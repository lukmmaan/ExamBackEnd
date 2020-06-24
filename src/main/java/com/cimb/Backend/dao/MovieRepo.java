package com.cimb.Backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.Backend.entity.Movie;

public interface MovieRepo extends JpaRepository<Movie, Integer>{

}
