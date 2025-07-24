package com.demo.service.author;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.model.Author;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService {

	@Override
	public Author addAuthor(Author author) {
		return null;
	}

	@Override
	public Author getAuthorById(Long id) {
		return null;
	}

	@Override
	public Author getAuthorByName(String name) {
		return null;
	}

	@Override
	public void deleteAuthor(Long id) {
		
	}

	@Override
	public Author updateAuthor(Author author, Long authorId) {

		return null;
	}

	@Override
	public List<Author> getAllAuthor() {
		return null;
	}

}
