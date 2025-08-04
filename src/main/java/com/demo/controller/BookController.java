package com.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.demo.model.Book;
import com.demo.request.AddBookRequest;
import com.demo.request.BookUpdateRequest;
import com.demo.response.ApiResponse;
import com.demo.service.book.IBookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/books")
public class BookController {
	private final IBookService bookService;
	
	@GetMapping("/all")
	public ResponseEntity<Map<String, Object>> getAllBooks(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size) {

	    Page<Book> bookPage = bookService.getAllBook(page, size);

	    Map<String, Object> response = new HashMap<String, Object>();
	    response.put("data", bookPage.getContent());
	    response.put("currentPage", bookPage.getNumber());
	    response.put("totalItems", bookPage.getTotalElements());
	    response.put("totalPages", bookPage.getTotalPages());

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/book/{id}")
	public ResponseEntity<?> getBookById(@PathVariable Long id) {
		try {
			Book book = bookService.getBook(id);
			return ResponseEntity.ok(book);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/book/{name}")
	public ResponseEntity<?> getBookByName(@RequestBody String name){
		try {
			List<Book> books = bookService.getBooksByName(name);
			if(books.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not Found", null));
			}
			return ResponseEntity.ok(books);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
	@GetMapping("/book/{autho}")
	public ResponseEntity<?> getBookByAuthor(@RequestBody String author){
		try {
			List<Book> books = bookService.getBooksByAuthor(author);
			if(books.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not Found", null));
			}
			return ResponseEntity.ok(books);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addBook(@RequestBody AddBookRequest book){
		try {
			Book theBook = bookService.addBook(book);
			return ResponseEntity.ok(theBook);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) {
		try {
			bookService.deleteBook(id);
			return ResponseEntity.ok("deleted");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/book/{id}")
	public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookUpdateRequest book) {
		try {
			Book theBook = bookService.updateBook(book, id);
			return ResponseEntity.ok(theBook);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
