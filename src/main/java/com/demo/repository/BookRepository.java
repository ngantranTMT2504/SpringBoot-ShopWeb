package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Book;


public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByCategoryName(String category);

	List<Book> findByAuthorName(String author);

	List<Book> findByName(String name);

	Long countByAuthorNameAndName(String author, String name);

	List<Book> findByCategoryNameAndAuthorName(String category, String author);

	List<Book> findByNameAndAuthorName(String name, String author);

}
