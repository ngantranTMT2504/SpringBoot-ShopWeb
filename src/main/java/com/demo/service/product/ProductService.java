package com.demo.service.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.exception.ProductNotFoundException;
import com.demo.model.Category;
import com.demo.model.Product;
import com.demo.repository.ProductRepository;
import com.demo.request.AddProductRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // auto-generate constructors for final variables
public class ProductService implements IProductService {
	private final ProductRepository productRepository; //inject ProductRepository via constructor

	@Override
	public Product addProduct(AddProductRequest product) {
		//Check category in DB
		//If found, create new product 
		//If not found, create a new category and then create product with this category
		return null;
	}
	
	private Product createProduct(AddProductRequest product, Category category) {
		return new Product(
				product.getName(),
				product.getBrand(),
				product.getPrice(),
				product.getInventory(),
				product.getDescription(),
				category
				);
	}

	@Override
	public Product getProduct(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
			throw new ProductNotFoundException("Product not found");
		});

	}

	@Override
	public void updateProduct(Product product, Long productId) {

	}

	@Override
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductByCategoryAndBrand(String category, String brand) {
		return productRepository.findByCategoryNameAndBrand(category, brand);
	}

	@Override
	public List<Product> getProductByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductByNameAndBrand(String name, String brand) {
		return productRepository.findByNameAndBrand(name, brand);
	}

	@Override
	public Long countProductByBrandAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand, name);
	}

}
