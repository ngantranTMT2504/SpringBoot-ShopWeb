package com.demo.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.demo.exception.AlreadyExistException;
import com.demo.exception.ResourceNotFoundException;
import com.demo.model.Category;
import com.demo.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class CategoryService implements ICategoryService{
	private final CategoryRepository categoryRepository;

	@Override
	public Category addCategory(Category category) {
		return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
				.map(categoryRepository :: save)
				.orElseThrow(() -> new AlreadyExistException(category.getName() + " aready exists"));
	}

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
	}

	@Override
	public Category getCategoryByName(String name) {
		return categoryRepository.getByName(name);
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.findById(id)
		.ifPresentOrElse(categoryRepository::delete, () -> {
			throw new ResourceNotFoundException("Category not found");
		});
		
	}

	@Override
	public Category updateCategory(Category category, Long categoryId) {
		return Optional.ofNullable(getCategoryById(categoryId)).map(oldCategory -> {
			oldCategory.setName(category.getName());
			return categoryRepository.save(oldCategory);
		}).orElseThrow( () -> new ResourceNotFoundException("Category not found")) ;
	}

	@Override
	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

}
