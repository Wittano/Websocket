package com.wittano.websocket.exception;

/**
 * Runtime exception related with operation on Friend table and models.
 * Raise, when operation is unauthorised or friend wasn't found in database for current user
 */
public class FriendException extends RuntimeException {
    public FriendException(String message) {
        super(message);
    }
}
