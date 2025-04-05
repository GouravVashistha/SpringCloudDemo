package com.school.library_service.DTO;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private int quantity;
    private Long userId;
}