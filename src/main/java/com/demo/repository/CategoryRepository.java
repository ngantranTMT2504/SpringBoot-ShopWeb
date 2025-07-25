package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category getByName(String name);
	
	boolean existsByName(String name);

}
