package com.school.user_service.Controller;

import com.school.user_service.DTO.Book;
import com.school.user_service.DTO.UserDTO;
import com.school.user_service.Entity.User;
import com.school.user_service.Exceptions.UserNotFoundException;
import com.school.user_service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        User saveUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId).orElseThrow(()-> new UserNotFoundException("User not found with ID: " + userId));
        return ResponseEntity.ok(user);
    }


    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/getBooksByUser/{userId}/books")
    public ResponseEntity<List<Book>> getBooksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getBooksBorrowedByUser(userId));
    }
}
