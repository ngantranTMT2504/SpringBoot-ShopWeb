package com.demo.controller;

import java.util.HashMap;
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

import com.demo.exception.AlreadyExistException;
import com.demo.model.Category;
import com.demo.response.ApiResponse;
import com.demo.service.category.CategoryService;
import com.demo.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
	private final ICategoryService categoryService;
	private final CategoryService categoryServiceImpl;

	@GetMapping("/all")
	public ResponseEntity<Map<String, Object>> getAllCategories(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		
			Page<Category> data = categoryServiceImpl.getAllCategories(page, size);
			
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("data", data.getContent());
			response.put("curentPage", data.getNumber());
			response.put("totalItems", data.getTotalElements());
			response.put("totalPages", data.getTotalPages());
			
			return ResponseEntity.ok(response);
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
		try {
			Category theCategory = categoryService.addCategory(category);
			return ResponseEntity.ok(new ApiResponse("Success!", theCategory));
		} catch (AlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
		try {
			Category theCategory = categoryService.getCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("Found", theCategory));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

//	@GetMapping("/category/{name}")
//	public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
//		try {
//			Category theCategory = categoryService.getCategoryByName(name);
//			return ResponseEntity.ok(new ApiResponse("Found", theCategory));
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
//		}
//	}

	@DeleteMapping("/category/{id}")
	public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id) {
		try {
			categoryService.deleteCategory(id);
			return ResponseEntity.ok(new ApiResponse("Deleted", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PutMapping("/category/{id}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
		try {
			Category theCategory = categoryService.updateCategory(category, id);
			return ResponseEntity.ok(new ApiResponse("Updated category", theCategory));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
