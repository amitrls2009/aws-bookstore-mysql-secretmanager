package com.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
