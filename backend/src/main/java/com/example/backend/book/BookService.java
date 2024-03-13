package com.example.backend.book;

import java.util.List;

public interface BookService {
    public abstract Book save(BookRequest request);
    public abstract List<Book> findAll();
}
