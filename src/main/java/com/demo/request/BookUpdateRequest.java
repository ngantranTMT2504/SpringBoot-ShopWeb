package com.demo.request;

import java.math.BigDecimal;
import java.util.Date;

import com.demo.model.Author;
import com.demo.model.Category;

import lombok.Data;

@Data
public class BookUpdateRequest {
	private Long id;
	private String name;
	private BigDecimal price;
	private int inventory;
	private int pages;
	private Date publishDate;
	private String description;
	private Category category;
	private Author author;

}
