package com.demo.service.book;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demo.exception.BookNotFoundException;
import com.demo.model.Category;
import com.demo.model.Author;
import com.demo.model.Book;
import com.demo.repository.AuthorRepository;
import com.demo.repository.BookRepository;
import com.demo.repository.CategoryRepository;
import com.demo.request.AddBookRequest;
import com.demo.request.BookUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // auto-generate constructors for final variables
public class BookService implements IBookService {
	private final BookRepository bookRepository; // inject ProductRepository via constructor
	private final CategoryRepository categoryRepository;
	private final AuthorRepository authorRepository;

	@Override
	public Book addBook(AddBookRequest book) {
		// Check category and author in DB
		// If found, create new product
		// If not found, create a new category and new author then create product

		Category category = Optional.ofNullable(categoryRepository.getByName(book.getCategory().getName()))
				.orElseGet(() -> {
					Category newCategory = new Category(book.getCategory().getName());
					return categoryRepository.save(newCategory);
				});

		Author author = Optional.ofNullable(authorRepository.getByName(book.getAuthor().getName())).orElseGet(() -> {
			Author newAuthor = new Author(book.getAuthor().getName());
			return authorRepository.save(newAuthor);
		});
		book.setCategory(category);

		return bookRepository.save(createBook(book, category, author));
	}

	private Book createBook(AddBookRequest book, Category category, Author author) {
		return new Book(book.getName(), book.getPrice(), book.getInventory(), book.getPublishDate(),
				book.getDescription(), category, author);
	}

	@Override
	public Book getBook(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
	}

	@Override
	public void deleteBook(Long id) {
		bookRepository.findById(id).ifPresentOrElse(bookRepository::delete, () -> {
			throw new BookNotFoundException("Product not found");
		});
	}

	@Override
	public Book updateBook(BookUpdateRequest book, Long bookId) {
		return bookRepository.findById(bookId).map(existingBook -> updateExistingBook(existingBook, book))
				.map(bookRepository::save).orElseThrow(() -> new BookNotFoundException("Book not found"));
	}

	private Book updateExistingBook(Book existingBook, BookUpdateRequest request) {
		existingBook.setName(request.getName());
		existingBook.setDescription(request.getDescription());
		existingBook.setInventory(request.getInventory());
		existingBook.setPrice(request.getPrice());
		existingBook.setImportDate(request.getImportDate());

		Category category = categoryRepository.getByName(request.getCategory().getName());
		existingBook.setCategory(category);
		Author author = authorRepository.getByName(request.getAuthor().getName());
		existingBook.setAuthor(author);

		return existingBook;
	}

	@Override
	public Page<Book> getAllBook(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return bookRepository.findAll(pageable);
	}

	@Override
	public List<Book> getBooksByCategory(String category) {
		return bookRepository.findByCategoryName(category);
	}

	@Override
	public List<Book> getBooksByAuthor(String author) {
		return bookRepository.findByAuthorName(author);
	}

	@Override
	public List<Book> getBooksByCategoryAndAuthor(String category, String author) {
		return bookRepository.findByCategoryNameAndAuthorName(category, author);
	}

	@Override
	public List<Book> getBooksByName(String name) {
		return bookRepository.findByName(name);
	}

	@Override
	public List<Book> getBooksByNameAndAuthor(String name, String author) {
		return bookRepository.findByNameAndAuthorName(name, author);
	}

	@Override
	public Long countBooksByAuthorAndName(String author, String name) {
		return bookRepository.countByAuthorNameAndName(author, name);
	}

}
