package com.demo.service.category;

import java.util.List;

import com.demo.model.Category;

public interface ICategoryService {
	Category addCategory(Category category);
	Category getCategoryById(Long id);
	Category getCategoryByName(String name);
	void deleteCategory(Long id);
	Category updateCategory(Category category, Long categoryId);
	List<Category> getAllCategory();
}
