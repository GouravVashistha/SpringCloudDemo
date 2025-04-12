package com.school.library_service.Service;

import com.school.library_service.DTO.BookDTO;
import com.school.library_service.Entity.Book;

import java.util.List;

public interface BookService {
    BookDTO saveBook(BookDTO bookDTO);
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO updateBook(Long id, BookDTO bookDTO);
    void deleteBook(Long id);
    List<BookDTO> getBooksByUserId(Long userId);

    List<Book> findByUserId(Long userId);

    BookDTO assignBookToUser(Long bookId, Long userId);
}
