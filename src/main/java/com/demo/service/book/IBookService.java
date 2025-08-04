package com.demo.service.book;

import java.util.List;

import org.springframework.data.domain.Page;
import com.demo.model.Book;
import com.demo.request.AddBookRequest;
import com.demo.request.BookUpdateRequest;

public interface IBookService {
	Book addBook(AddBookRequest book);
	Book getBook(Long id);
	void deleteBook(Long id);
	Book updateBook(BookUpdateRequest book, Long bookId);
	Page<Book> getAllBook(int page, int size);
	List<Book> getBooksByCategory(String category);
	List<Book> getBooksByAuthor(String author);
	List<Book> getBooksByCategoryAndAuthor(String category, String author);
	List<Book> getBooksByName(String name);
	List<Book> getBooksByNameAndAuthor(String name, String author);
	Long countBooksByAuthorAndName(String author, String name);
}
