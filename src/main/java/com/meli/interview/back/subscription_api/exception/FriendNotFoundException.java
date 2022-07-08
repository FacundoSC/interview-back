package com.meli.interview.back.subscription_api.exception;

public class FriendNotFoundException  extends RuntimeException{
    private static final long serialVersionUID = 5959479918185637340L;

    public FriendNotFoundException(String message) {
        super(message);
    }
}
