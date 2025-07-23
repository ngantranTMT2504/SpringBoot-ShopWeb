package com.demo.service.product;

import java.util.List;

import com.demo.model.Product;
import com.demo.request.AddProductRequest;

public interface IProductService {
	Product addProduct(AddProductRequest product);
	Product getProduct(Long id);
	void deleteProduct(Long id);
	void updateProduct(Product product, Long productId);
	List<Product> getAllProduct();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductByBrand(String brand);
	List<Product> getProductByCategoryAndBrand(String category, String brand);
	List<Product> getProductByName(String name);
	List<Product> getProductByNameAndBrand(String name, String brand);
	Long countProductByBrandAndName(String brand, String name);
}
