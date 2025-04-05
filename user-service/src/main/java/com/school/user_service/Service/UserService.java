package com.school.user_service.Service;

import com.school.user_service.DTO.Book;
import com.school.user_service.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> getUserById(Long id);
    User updateUser(Long userId,User user);
    void deleteUser(Long userId);
    List<Book> getBooksBorrowedByUser(Long userId);
}
