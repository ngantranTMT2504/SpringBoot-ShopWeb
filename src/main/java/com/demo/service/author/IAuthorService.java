package com.demo.service.author;

import java.util.List;

import com.demo.model.Author;

public interface IAuthorService {
	Author addAuthor(Author author);
	Author getAuthorById(Long id);
	Author getAuthorByName(String name);
	void deleteAuthor(Long id);
	Author updateAuthor(Author author, Long authorId);
	List<Author> getAllAuthor();

}
