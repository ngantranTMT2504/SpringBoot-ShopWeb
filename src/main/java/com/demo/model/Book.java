package com.demo.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private BigDecimal price;
	private int inventory;
	private int pages;
	private Date publishDate;
	private String description;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "author_id")
	private Author author;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Image> images;

	public Book(String name, BigDecimal price, int inventory, int pages, Date publishDate, String description,
			Category category, Author author) {
		super();
		this.name = name;
		this.price = price;
		this.inventory = inventory;
		this.pages = pages;
		this.publishDate = publishDate;
		this.description = description;
		this.category = category;
		this.author = author;
	}
	
	
}
