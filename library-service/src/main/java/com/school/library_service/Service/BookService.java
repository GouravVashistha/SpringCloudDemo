package com.school.library_service.Service;

import com.school.library_service.DTO.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO saveBook(BookDTO bookDTO);
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO updateBook(Long id, BookDTO bookDTO);
    String deleteBook(Long id);
    List<BookDTO> getBooksByUserId(Long userId);
    boolean issueBookToUser(Long bookId, Long userId);
    void returnBookFromUser(Long bookId, Long userId);
}
