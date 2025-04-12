package com.school.user_service.Exceptions;

public class UserAllReadyExists extends RuntimeException{
    public UserAllReadyExists(String message){
        super(message);
    }
}
