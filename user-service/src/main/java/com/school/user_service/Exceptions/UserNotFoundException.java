package com.school.user_service.Exceptions;

public class UserNotFoundException  extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }

}
