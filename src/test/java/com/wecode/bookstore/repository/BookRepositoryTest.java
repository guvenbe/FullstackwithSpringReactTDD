package com.wecode.bookstore.repository;

import com.wecode.bookstore.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    public void shouldAbleToFetchAllBooksInDB() {
        Iterable<Book> all = bookRepository.findAll();
        long totalBooksCount = StreamSupport.stream(all.spliterator(), false).count();
        assertEquals(19, totalBooksCount);
    }

    @Test
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    public void shouldReturnOneBookWhenTitleIsTestTitle() {
        List<Book> test_title = bookRepository.findBooksByTitle("test title");
        assertEquals(test_title.size(), 1);

    }

    @Test
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    public void shouldReturnOneBookWhenTitleIsTestTitleIgnoreCase(){
        List<Book> test_title = bookRepository.findBooksByTitleIgnoreCase("Test Title");
        assertEquals(test_title.size(), 1);
    }

}
