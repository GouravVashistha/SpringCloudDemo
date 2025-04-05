package com.school.user_service.ServiceImpl;

import com.school.user_service.DTO.Book;
import com.school.user_service.Entity.User;
import com.school.user_service.Repository.UserRepository;
import com.school.user_service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String LIBRARY_SERVICE_URL = "http://localhost:2000/books";

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Corrected URL with '/' before userId
            String url = LIBRARY_SERVICE_URL + "/" + userId;

            try {
                ResponseEntity<Book[]> response = restTemplate.getForEntity(url, Book[].class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    user.setBorrowedBooks(Arrays.asList(response.getBody()));
                }
            } catch (HttpClientErrorException e) {
                System.out.println("No books found for user " + userId);
            } catch (Exception ex) {
                System.out.println("Error while calling Library Service: " + ex.getMessage());
            }

            return Optional.of(user);
        }
        return Optional.empty();
    }


    @Override
    public User updateUser(Long userId, User updatedUser) {
        return userRepository.findById(userId).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            return userRepository.save(user);
        }).orElse(null);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

//    @Override
//    public List<Book> getBooksBorrowedByUser(Long userId) {
//        String url = LIBRARY_SERVICE_URL + "/user/" + userId;
//        ResponseEntity<Book[]> response = restTemplate.getForEntity(url, Book[].class);
//        return Arrays.asList(response.getBody());
//    }
//    @Override
//    public List<Book> getBooksBorrowedByUser(Long userId) {
////        ResponseEntity<Book[]> response = restTemplate.getForEntity("http://localhost:2001/books/user/" + userId, Book[].class);
//        ResponseEntity<Book[]> response = restTemplate.getForEntity("http://LIBRARY-SERVICE/" + userId, Book[].class);
//        return Arrays.asList(response.getBody());
//    }


@Override
public List<Book> getBooksBorrowedByUser(Long userId) {
    String url = "http://localhost:2001/books/user/" + userId;
    System.out.println("Fetching books from: " + url);

    try {
        ResponseEntity<Book[]> response = restTemplate.getForEntity(url, Book[].class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            System.out.println("Books fetched: " + Arrays.toString(response.getBody())); // Debugging
            return Arrays.asList(response.getBody());
        }
    } catch (Exception ex) {
        System.out.println("Error while calling Library Service: " + ex.getMessage());
    }

    return List.of(); // Empty list if no books found
}


}
