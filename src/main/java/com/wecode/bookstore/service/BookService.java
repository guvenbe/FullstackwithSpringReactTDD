package com.wecode.bookstore.service;

import com.wecode.bookstore.dto.BookDto;
import com.wecode.bookstore.model.Book;
import com.wecode.bookstore.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public List<BookDto> getBooks(){
        Iterable<Book> allBooks = bookRepository.findAll();
        return StreamSupport.stream(allBooks.spliterator(), false)
                .map(convertBookModelBookDto())
                .collect(Collectors.toList());

    }

    private Function<Book, BookDto> convertBookModelBookDto() {
        return book -> modelMapper.map(book, BookDto.class);
    }
}
