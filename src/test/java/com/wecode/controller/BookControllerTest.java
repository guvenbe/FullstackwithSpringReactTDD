package com.wecode.controller;

import com.wecode.bookstore.controller.BookController;
import com.wecode.bookstore.dto.BookDto;
import com.wecode.bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    @Mock
    BookService bookService;


    @InjectMocks
    private BookController bookController;

    @Test
    public void shouldReturnListBookWhenGetBooksCalled(){
        List<BookDto> booksDto = new ArrayList<>();
        BookDto bookDto = getBookDto();
        booksDto.add(bookDto);
        when(bookService.getBooks()).thenReturn(booksDto);
        List<BookDto> resultsBooksDto = bookController.getBooks().getBody();
        assertThat(resultsBooksDto.size()).isEqualTo(1);
        assertThat(resultsBooksDto.get(0))
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "test title")
                .hasFieldOrPropertyWithValue("description", "test description")
                .hasFieldOrPropertyWithValue("releaseYear", 2020);

    }

    private BookDto getBookDto(){
        return BookDto.builder()
                .title("test title")
                .description("test description")
                .id(UUID.randomUUID())
                .releaseYear(2020)
                .build();
    }
}
