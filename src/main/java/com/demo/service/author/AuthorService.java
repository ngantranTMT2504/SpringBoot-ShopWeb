package com.demo.service.author;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.demo.exception.AlreadyExistException;
import com.demo.exception.ResourceNotFoundException;
import com.demo.model.Author;
import com.demo.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService {
	private final AuthorRepository authorRepository;

	@Override
	public Author addAuthor(Author author) {
		return Optional.of(author).filter(c -> !authorRepository.existsByName(c.getName()))
				.map(authorRepository :: save)
				.orElseThrow(() -> new AlreadyExistException(author.getName() + " aready exists"));
	}

	@Override
	public Author getAuthorById(Long id) {
		return authorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Author not found"));
	}

	@Override
	public Author getAuthorByName(String name) {
		return authorRepository.getByName(name);
	}

	@Override
	public void deleteAuthor(Long id) {
		authorRepository.findById(id)
		.ifPresentOrElse(authorRepository::delete, () -> {
			throw new ResourceNotFoundException("Author not found");
		});
	}

	@Override
	public Author updateAuthor(Author author, Long authorId) {
		return Optional.ofNullable(getAuthorById(authorId)).map(oldAuthor -> {
			oldAuthor.setName(author.getName());
			return authorRepository.save(oldAuthor);
		}).orElseThrow( () -> new ResourceNotFoundException("Author not found")) ;
	}

	@Override
	public List<Author> getAllAuthor() {
		return authorRepository.findAll();
	}

}
