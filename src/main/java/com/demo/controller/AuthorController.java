package com.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.exception.AlreadyExistException;
import com.demo.model.Author;
import com.demo.response.ApiResponse;
import com.demo.service.author.IAuthorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/authors")
public class AuthorController {
	private final IAuthorService authorService;

	@GetMapping("/all")
	public ResponseEntity<?> getAllAuthors() {
		try {
			List<Author> authors = authorService.getAllAuthor();
			return ResponseEntity.ok(authors);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addAuthor(@RequestBody Author author) {
		try {
			Author theAuthor = authorService.addAuthor(author);
			return ResponseEntity.ok(theAuthor);
		} catch (AlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/author/{id}")
	public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
		try {
			Author theAuthor = authorService.getAuthorById(id);
			return ResponseEntity.ok(theAuthor);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/author/{name}")
	public ResponseEntity<?> getAuthorByName(@PathVariable String name) {
		try {
			Author theAuthor = authorService.getAuthorByName(name);
			return ResponseEntity.ok(theAuthor);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/author/{id}")
	public ResponseEntity<?> deleteAuthorById(@PathVariable Long id) {
		try {
			authorService.deleteAuthor(id);
			return ResponseEntity.ok("Deleted");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/author/{id}")
	public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
		try {
			Author theAuthor = authorService.updateAuthor(author, id);
			return ResponseEntity.ok(theAuthor);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	
}
