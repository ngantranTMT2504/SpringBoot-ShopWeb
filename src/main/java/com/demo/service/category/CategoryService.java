package com.demo.service.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.exception.ResourceNotFoundException;
import com.demo.model.Category;
import com.demo.repository.AuthorRepository;
import com.demo.repository.BookRepository;
import com.demo.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class CategoryService implements ICategoryService{
	private final CategoryRepository categoryRepository;

	@Override
	public Category addCategory(Category category) {
		return null;
	}

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
	}

	@Override
	public Category getCategoryByName(String name) {
		return null;
	}

	@Override
	public void deleteCategory(Long id) {
		
	}

	@Override
	public Category updateCategory(Category category, Long categoryId) {
		return null;
	}

	@Override
	public List<Category> getAllCategory() {
		return null;
	}

}
