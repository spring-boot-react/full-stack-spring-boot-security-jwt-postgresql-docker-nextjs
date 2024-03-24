package com.example.backend.book;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    @PostMapping
    public BookDTO save(
            @RequestBody BookDTO bookDTO
    ) {
        Book book = modelMapper.map(bookDTO, Book.class);
        return modelMapper.map(bookService.saveBook(book), BookDTO.class);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable int id) {
        return bookService.findBookById(id);
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }
}
