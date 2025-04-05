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
        book.setId(null);  // Ensure Hibernate auto-generates ID
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
        if (bookRepository.existsById(id)) {
            bookDTO.setId(id);
            Book book = modelMapper.map(bookDTO, Book.class);
            book = bookRepository.save(book);
            return modelMapper.map(book, BookDTO.class);
        }
        return null;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> getBooksByUserId(Long userId) {
        return bookRepository.findByUserId(userId).stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<Book> findByUserId(Long userId) {
        return bookRepository.findByUserId(userId); // Fetch books borrowed by the user
    }

}
