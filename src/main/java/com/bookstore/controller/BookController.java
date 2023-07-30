package com.bookstore.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.APIResponse;
import com.bookstore.model.Book;
import com.bookstore.repo.BookRepository;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookRepository bookRepository;

	private static final Logger log = LogManager.getLogger(BookController.class);

	@PostMapping
	public APIResponse postBook(@RequestBody Book book) {
		log.info("Request for creating book with method name " + HttpMethod.POST.name());
		return buildResponse(bookRepository.save(book));
	}

	@PutMapping
	public APIResponse updateBook(@RequestBody Book book) {
		log.info("Request for updating book with method name " + HttpMethod.PUT.name());
		return buildResponse(bookRepository.save(book));
	}

	@GetMapping("/all")
	public APIResponse getAllBooks() {
		log.info("Request for reading all books with method name " + HttpMethod.GET.name());
		return buildResponse(bookRepository.findAll());
	}

	@GetMapping
	public APIResponse getBook(@RequestParam int book_id) {
		log.info("Request for reading book by id :" + book_id + "  with method name " + HttpMethod.GET.name());
		return buildResponse(bookRepository.findById(book_id).get());
	}

	@DeleteMapping("/{book_id}")
	public APIResponse deleteBook(@PathVariable("book_id") int bookId) {
		log.info(
				"Method Name is : " + HttpMethod.DELETE.name() + "with URL: /book and pasing path paramter :" + bookId);
		bookRepository.deleteById(bookId);
		String deleteMsg = "";
		if (bookRepository.findById(bookId) != null) {
			deleteMsg = "Book is deleted";
		}
		return buildResponse(deleteMsg);
	}

	private APIResponse buildResponse(Object result) {
		Map<String, String> headers = new HashMap<>();
		headers.put("X-amazon-author", "Amit");
		headers.put("X-amazon-apiVersion", "v1");
		APIResponse response = new APIResponse(result, headers);
		log.info("APIResponse:::" + response);
		return response;
	}

}
