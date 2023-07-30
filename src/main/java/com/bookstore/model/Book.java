package com.bookstore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int book_id;
	private String title;
	private int total_pages;
	private double rating;
	private String isbn;

	public Book(int book_id, String title, int total_pages, double rating, String isbn) {
		super();
		this.book_id = book_id;
		this.title = title;
		this.total_pages = total_pages;
		this.rating = rating;
		this.isbn = isbn;
	}

	public Book() {
		super();
	}

}
