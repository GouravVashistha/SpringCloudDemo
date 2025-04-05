package com.school.user_service.DTO;

import lombok.Data;

@Data
public class Book {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private int quantity;
}