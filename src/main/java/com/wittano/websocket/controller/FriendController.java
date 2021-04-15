package com.wittano.websocket.controller;

import com.wittano.websocket.exception.UserNotFoundException;
import com.wittano.websocket.models.entity.User;
import com.wittano.websocket.models.request.FriendRequest;
import com.wittano.websocket.service.controller.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @GetMapping("/friend/{user}")
    public Set<User> getFriends(@PathVariable("user") String name) throws UserNotFoundException {
        return friendService.getAll(name);
    }

    @PostMapping("/friend")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addFriend(@RequestBody FriendRequest friend) {
        friendService.add(friend);
    }

    @DeleteMapping("/friend")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFriend(@RequestBody FriendRequest friend) {
        friendService.delete(friend);
    }
}
