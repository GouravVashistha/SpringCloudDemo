package com.school.library_service.ServiceImpl;

import com.school.library_service.DTO.BookDTO;
import com.school.library_service.Entity.Book;
import com.school.library_service.Repository.BookRepository;
import com.school.library_service.Service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BookDTO saveBook(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        book.setId(null);
        book = bookRepository.save(book);
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElse(null);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book.setPublisher(bookDTO.getPublisher());
            book.setQuantity(bookDTO.getQuantity());
            return modelMapper.map(bookRepository.save(book), BookDTO.class);
        }).orElse(null);
    }

    @Override
    public String deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            return "Cannot delete book because it does not exist in the library.";
        }
        bookRepository.deleteById(id);
        return "Book deleted successfully.";
    }

    @Override
    public List<BookDTO> getBooksByUserId(Long userId) {
        return bookRepository.findByUserId(userId).stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean issueBookToUser(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) return false;

        if (book.getQuantity() > 0) {
            book.setUserId(userId);
            book.setQuantity(book.getQuantity() - 1);
            bookRepository.save(book);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void returnBookFromUser(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null && userId.equals(book.getUserId())) {
            book.setUserId(null);
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
        }
    }

}
