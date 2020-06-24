package com.cimb.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.Backend.dao.CategoryRepo;
import com.cimb.Backend.entity.Category;
import com.cimb.Backend.entity.Movie;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryRepo categoryRepo;
	
	//get categories
	@GetMapping
	public Iterable<Category> getCategories(){
		return categoryRepo.findAll();
	}
	
	//post categories
	@PostMapping
	public Category postCategory(@RequestBody Category category) {
		return categoryRepo.save(category);
	}
	
	//edit category
	@PutMapping("/{categoryId}")
	public Category editCategory(@RequestBody Category category, @PathVariable int categoryId) {
		Category findCategory = categoryRepo.findById(categoryId).get();
		category.setId(categoryId);
		category.setMovies(findCategory.getMovies());
		return categoryRepo.save(category);
	}
}
