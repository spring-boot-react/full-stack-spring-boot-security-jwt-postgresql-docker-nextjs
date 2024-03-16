package com.example.backend.book;

import com.example.backend.payload.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public Book saveBook(BookRequest request) {
        // TODO Use mapper to model and model to mapper
        var book = Book.builder()
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .build();
        return bookRepository.save(book);
    }

    @Override
    public ResponseEntity<Book> findBookById(int id) {
        return ResponseEntity.ok().body(this.findById(id));
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    private Book findById(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("book.si.not.found",  new String[]{String.valueOf(id)}));
    }
}