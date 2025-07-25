package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	Author getByName(String name);
	
	boolean existsByName(String name);

}
