package com.wecode.controller;

import com.wecode.bookstore.controller.BookController;
import com.wecode.bookstore.dto.BookDto;
import com.wecode.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    @Mock
    BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setup(){
    }

    @Test
    public void shouldReturnListBookWhenGetBooksCalled(){
        List<BookDto>booksDto = new ArrayList<>();
        BookDto bookDto = getBookDto();
        booksDto.add(bookDto);
        when(bookService.getBooks()).thenReturn(booksDto);
        List<BookDto> resultsBooksDto = bookController.getBooks().getBody();
        verify(bookService, times(1)).getBooks();
        assertThat(resultsBooksDto).isNotNull();
        assertThat(resultsBooksDto.size()).isEqualTo(1);
        assertThat(resultsBooksDto.get(0))
                .hasFieldOrPropertyWithValue("title", "test title")
                .hasFieldOrPropertyWithValue("description", "test description")
                .hasFieldOrPropertyWithValue("releaseYear", 2020);

    }

    @Test
    public void shouldReturnBookWhenGetBookByTitleCalled(){
        List<BookDto>booksDto = new ArrayList<>();
        BookDto bookDto = getBookDto();
        booksDto.add(bookDto);
        when(bookService.getBooksByTitle(anyString())).thenReturn(booksDto);
        List<BookDto> resultsBooksDto = bookController.getBooksByTitle(bookDto.getTitle()).getBody();
        verify(bookService, times(1)).getBooksByTitle(anyString());
        assertThat(resultsBooksDto).isNotNull();
        assertThat(resultsBooksDto.size()).isEqualTo(1);
        assertThat(resultsBooksDto.get(0))
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
