package com.school.library_service.Controller;

import com.school.library_service.DTO.BookDTO;
import com.school.library_service.Entity.Book;
import com.school.library_service.Service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.saveBook(bookDTO), HttpStatus.CREATED);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully.");
    }

    @PostMapping("/issue/{bookId}/user/{userId}")
    public ResponseEntity<String> issueBook(@PathVariable Long bookId, @PathVariable Long userId) {
        boolean result = bookService.issueBookToUser(bookId, userId);
        if (result) {
            return ResponseEntity.ok("Book issued successfully.");
        } else {
            return ResponseEntity.badRequest().body("Book not available.");
        }
    }

    @PostMapping("/return/{bookId}/user/{userId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId, @PathVariable Long userId) {
        bookService.returnBookFromUser(bookId, userId);
        return ResponseEntity.ok("Book returned successfully.");
    }
}