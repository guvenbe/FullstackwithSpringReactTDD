package com.wecode.bookstore.controller;

import com.wecode.bookstore.dto.BookDto;
import com.wecode.bookstore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ResponseEntity<List<BookDto>> getBooks(){
//        BookDto book = BookDto.builder()
//                .title("My First Book Title")
//                .build();
//        List<BookDto> books= new ArrayList<>();
//        books.add(book);
        List<BookDto> books = bookService.getBooks();
        return ResponseEntity.ok(books);
    }
}
