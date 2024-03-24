package com.example.backend.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookDTO {

    private Integer id;
    private String author;
    private String isbn;
}
