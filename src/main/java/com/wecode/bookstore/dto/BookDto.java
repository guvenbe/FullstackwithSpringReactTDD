package com.wecode.bookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema( name= "BookDto", description = "All Details about book")
public class BookDto {

    @Schema(
            name = "UUID",
            type = "UUID",
            example = "d3655555-8a24-11ec-a8a3-0242ac120002",
            description = "The database generated uuid for book id",
            required = true
    )
    private UUID id;

    @Schema(
            name = "title",
            type = "String",
            example = "book title",
            description = "Book title",
            required = true
    )
    private String title;

    @Schema(
            name = "description",
            type = "String",
            example = "book description",
            description = "Book description",
            required = true
    )
    private String description;

    @Schema(
            name = "releaseYear",
            type = "int",
            example = "2022",
            description = "Book releaseYear",
            required = true
    )
    private int releaseYear;
}
