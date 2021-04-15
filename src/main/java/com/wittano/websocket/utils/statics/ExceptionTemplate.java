package com.wittano.websocket.utils.statics;

import com.wittano.websocket.exception.UserNotFoundException;

import java.util.function.Supplier;

public class ExceptionTemplate {
    /**
     * Template for {@link UserNotFoundException}
     */
    public static Supplier<? extends UserNotFoundException> USER_NOT_FOUND(String username) {
        return () -> {
            throw new UserNotFoundException(String.format("User %s isn't exist!", username));
        };
    }
}
