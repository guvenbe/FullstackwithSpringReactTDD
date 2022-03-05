package com.wecode.integrationtest;

import com.wecode.bookstore.BookstoreApplication;
import com.wecode.bookstore.config.JwtUtil;
import com.wecode.bookstore.dto.BookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// DirtiesContext after each test clean up the db...to avoid primary key violation
@SpringBootTest(classes = BookstoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerTestIT {

    @LocalServerPort
    private int port;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        setUpHeader();
    }

    //Generate token
    void setUpHeader() {
        String token = jwtUtil.generateToken(
                new User("peter@gmail.com",
                        passwordEncoder.encode("password"),
                        new ArrayList<>()));
        testRestTemplate.getRestTemplate()
                .setInterceptors(Collections.singletonList(((request, body, execution) -> {
                            request.getHeaders()
                                    .add("Authorization", "Bearer " + token);
                            return execution.execute(request, body);
                        }))
                );
    }

    @Test
    @Disabled
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    public void shouldReturnBookWhenApiCalled() {
        BookDto[] listOfBooks = testRestTemplate
                .getForObject("http://localhost:" + port + "/api/v1/books", BookDto[].class);
        assertThat(listOfBooks).isNotNull();
        assertThat(listOfBooks.length).isEqualTo(19);

    }

    @Test
    @Disabled
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    public void shouldReturnBookWhenApiCalled1() {
        BookDto[] listOfBooks = testRestTemplate
                .getForObject("http://localhost:" + port + "/api/v1/books", BookDto[].class);
        assertThat(listOfBooks).isNotNull();
        assertThat(listOfBooks.length).isEqualTo(19);

    }

    @Test
//    @Disabled
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    public void shouldReturnOneBookWhenCalledWithTitle() {
        BookDto[] listOfBookDto = testRestTemplate
                .getForObject(
                        "http://localhost:" + port + "/api/v1/books/test Title", BookDto[].class);
        assertThat(listOfBookDto).isNotNull();
        assertThat(listOfBookDto.length).isEqualTo(1);
    }
}
