package com.cimb.Backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.Backend.dao.CategoryRepo;
import com.cimb.Backend.dao.MovieRepo;
import com.cimb.Backend.entity.Category;
import com.cimb.Backend.entity.Movie;

@RestController
@RequestMapping("/movie")
public class MovieController {
	@Autowired
	private MovieRepo movieRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	
	//add movies
	@PostMapping
	public Movie addMovie(@RequestBody Movie movie) {
		return movieRepo.save(movie);
	}
	
	//get movies
	@GetMapping
	public Iterable<Movie> getMovie(){
		return movieRepo.findAll();
	}
	
	//add categories to movie
	@PostMapping("/{movieId}/category/{categoryId}")
	public Movie addMovieToCategory(@PathVariable int movieId, @PathVariable int categoryId) {
		Movie findMovie = movieRepo.findById(movieId).get();
		Category findCategory = categoryRepo.findById(categoryId).get();
		findMovie.getCategories().add(findCategory);
		return movieRepo.save(findMovie);
	}
	
	//delete categories dalam suatu movie
	@DeleteMapping("/{movieId}/category/{categoryId}")
	public Movie deleteCategory(@PathVariable int categoryId, @PathVariable int movieId) {
		Movie findMovie = movieRepo.findById(movieId).get();
		Category findCategory = categoryRepo.findById(categoryId).get();
		
		findMovie.getCategories().remove(findCategory);
		return movieRepo.save(findMovie);
	}
	
	//delete kategori dan putusin semua hubungan dengan id itu
	@DeleteMapping("/category/{categoryId}")
	public void deleteCategory(@PathVariable int categoryId) {
		Category findCategory = categoryRepo.findById(categoryId).get();
		
		findCategory.getMovies().forEach(movie ->{
			List<Category> movieCategory = movie.getCategories();
			movieCategory.remove(findCategory);
			movieRepo.save(movie);
		});
		findCategory.setMovies(null);
		categoryRepo.deleteById(categoryId);
	}
	
	//edit movie tanpa hilang categories
	@PutMapping("/{movieId}")
	public Movie editMovie(@RequestBody Movie movie, @PathVariable int movieId) {
		Movie findmovie = movieRepo.findById(movieId).get();
//		System.out.println(findmovie.getCategories());
		movie.setId(movieId);
		movie.setCategories(findmovie.getCategories());
		return movieRepo.save(movie);
	}
	//delete movie
	@DeleteMapping("/{id}")
	public void deleteMovie(@PathVariable int id) {
//		System.out.println(id);
		Movie findMovie = movieRepo.findById(id).get();
		findMovie.getCategories().forEach(categories -> {
			List<Movie> categoriesMovie = categories.getMovies();
			categoriesMovie.remove(findMovie);
			categoryRepo.save(categories);
		});
		findMovie.setCategories(null);
		movieRepo.deleteById(id);
	}
}
