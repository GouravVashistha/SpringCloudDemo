package com.school.library_service.Controller;

import com.school.library_service.DTO.BookDTO;
import com.school.library_service.Entity.Book;
import com.school.library_service.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")  // Removed trailing slash
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        BookDTO savedBook = bookService.saveBook(bookDTO);
        return ResponseEntity.status(201).body(savedBook);
    }
    @PutMapping("/assignBookToUser/{bookId}/{userId}")
    public ResponseEntity<BookDTO> assignBookToUser(
            @PathVariable Long bookId,
            @PathVariable Long userId) {

        BookDTO updatedBook = bookService.assignBookToUser(bookId, userId);
        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping("/getAllBooks/")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
    @PutMapping("/updateBook/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }
    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Book>> getBooksByUserId(@PathVariable Long userId) {
       List<Book> books = bookService.findByUserId(userId);
        return ResponseEntity.ok(books);
    }
}
