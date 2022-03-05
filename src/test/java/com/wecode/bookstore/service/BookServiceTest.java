package com.wecode.bookstore.service;

import com.wecode.bookstore.dto.BookDto;
import com.wecode.bookstore.model.Book;
import com.wecode.bookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    public void shouldReturnBookWhenGetBookByTitleIgnoreCaseCalled(){
        List<Book> books = new ArrayList<>();
        Book book = getBook();
        books.add(book);
        BookDto bookDTO = getBookDto();

        when(bookRepository.findBooksByTitleIgnoreCase(anyString())).thenReturn(books);
        when(mapper.map(book,BookDto.class)).thenReturn(bookDTO);
        List<BookDto> bookDtoList =bookService.getBooksByTitle(anyString());

        verify(bookRepository, times(1)).findBooksByTitleIgnoreCase(anyString());
        verify(mapper , times(1)).map(book,BookDto.class);
        assertThat(bookDtoList.size()).isEqualTo(1);
        assertThat(bookDtoList.get(0))
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "test title")
                .hasFieldOrPropertyWithValue("description", "test description")
                .hasFieldOrPropertyWithValue("releaseYear", 2020);
    }

    @Test
    public void shouldReturnListBookDTOWhenGetBooksCalled(){

        List<Book> books = new ArrayList<>();
        Book book = getBook();
        books.add(book);
        BookDto bookDto = getBookDto();
        when(bookRepository.findAll()).thenReturn(books);
        when(mapper.map(book, BookDto.class)).thenReturn(bookDto);
        List<BookDto> bookDtos = bookService.getBooks();
        assertThat(bookDtos.size()).isEqualTo(1);
        assertThat(bookDtos.get(0))
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "test title")
                .hasFieldOrPropertyWithValue("description", "test description")
                .hasFieldOrPropertyWithValue("releaseYear", 2020);
    }

    private Book getBook(){
        return Book.builder()
                .title("test title")
                .description("test description")
                .id(UUID.randomUUID())
                .releaseYear(2020)
                .build();
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
