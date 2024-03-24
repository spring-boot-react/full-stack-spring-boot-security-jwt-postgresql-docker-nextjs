package com.example.backend.book;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    public abstract Book saveBook(Book book);
    public abstract ResponseEntity<Book> findBookById(int id);
    public abstract List<Book> findAllBooks();
}
