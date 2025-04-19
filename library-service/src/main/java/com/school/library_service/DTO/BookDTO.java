package com.school.library_service.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;
    @NotBlank(message = "Publisher is required")
    private String publisher;
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

}