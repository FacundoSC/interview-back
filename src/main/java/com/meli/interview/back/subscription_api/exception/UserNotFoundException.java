package com.meli.interview.back.subscription_api.exception;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 5959479918185637340L;

    public UserNotFoundException(String message) {
        super(message);
    }
}