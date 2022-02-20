package com.wecode.integrationtest;

import com.wecode.bookstore.BookstoreApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = BookstoreApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// DirtiesContext after each test clean up the db...to avoid primary key violation
public class BookControllerTestIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

//    @Test
//    @Disabled
//    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
//    public void shouldReturnBookWhenApiCalled(){
//        BookDto[] listOfBooks = testRestTemplate
//                .getForObject("http://localhost:" + port + "/api/v1/books", BookDto[].class);
//        assertThat(listOfBooks).isNotNull();
//        assertThat(listOfBooks.length).isEqualTo(2);
//
//    }
//
//    @Test
//    @Disabled
//    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
//    public void shouldReturnBookWhenApiCalled1(){
//        BookDto[] listOfBooks = testRestTemplate
//                .getForObject("http://localhost:" + port + "/api/v1/books", BookDto[].class);
//        assertThat(listOfBooks).isNotNull();
//        assertThat(listOfBooks.length).isEqualTo(2);
//
//    }
}
