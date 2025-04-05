package com.school.user_service.Entity;

import com.school.user_service.DTO.Book;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    private String password;

    @Transient  // Not stored in the database
    private List<Book> borrowedBooks;
}
