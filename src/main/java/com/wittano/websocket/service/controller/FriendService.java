package com.wittano.websocket.service.controller;

import com.wittano.websocket.exception.FriendException;
import com.wittano.websocket.exception.UserNotFoundException;
import com.wittano.websocket.models.entity.User;
import com.wittano.websocket.models.request.FriendRequest;
import com.wittano.websocket.repository.UserRepository;
import com.wittano.websocket.utils.statics.ExceptionTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Service used for operation on friends list
 */
@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserRepository userRepository;

    /**
     * Get friends list for current user
     *
     * @throws UserNotFoundException throws, when user wasn't found
     */
    @Transactional
    public Set<User> getAll(String username) throws UserNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(ExceptionTemplate.USER_NOT_FOUND(username))
                .getFriends();
    }

    /**
     * Add new friend to friends list
     *
     * @throws FriendException throw, when friend exist on the friend list
     */
    @Transactional
    public void add(FriendRequest friend) throws FriendException {
        if (friend.getUsername().equals(friend.getFriendName())) {
            throw new FriendException("Username and friend who want to add, can't be same person");
        }

        final User user = userRepository.findUserByUsername(friend.getUsername())
                .orElseThrow(ExceptionTemplate.USER_NOT_FOUND(friend.getUsername()));

        if (user.getFriends().size() > 0 && user.getFriends()
                .stream()
                .anyMatch(it -> friend.getFriendName().equals(it.getUsername()))) {
            throw new FriendException(String.format("%s is exist!", friend.getFriendName()));
        } else {
            user.getFriends().add(userRepository.findUserByUsername(friend.getFriendName()).orElseThrow());
        }

        userRepository.save(user);
    }

    @Transactional
    public void delete(FriendRequest friend) {
        User user = userRepository.findUserByUsername(friend.getUsername()).orElseThrow();

        if (user.getFriends().stream().noneMatch(it -> it.getUsername().equals(friend.getFriendName()))) {
            throw new FriendException("Friend, which you was tried delete, doesn't exist in your friend list");
        } else {
            user.getFriends().remove(user.getFriends().stream()
                    .filter(it -> it.getUsername().equals(friend.getFriendName()))
                    .findFirst().orElseThrow());
        }

        userRepository.save(user);
    }
}
