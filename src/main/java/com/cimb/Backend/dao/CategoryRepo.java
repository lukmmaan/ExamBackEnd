package com.cimb.Backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.Backend.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
