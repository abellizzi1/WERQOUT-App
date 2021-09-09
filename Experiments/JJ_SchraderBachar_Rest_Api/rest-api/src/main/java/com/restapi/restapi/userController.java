package com.restapi.restapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class userController {
    @RequestMapping("/users")
    public List<User> getAllusers() {
        return Arrays.asList(
            new User("user1", "user1@gmail.com", "Athlete"),
            new User("user2", "user2@gmail.com", "Coach"),
            new User("user3", "user3@gmail.com", "Gym Owner"),
            new User("user4", "user4@gmail.com", "Coach"),
            new User("user5", "user5@gmail.com", "Athlete")
        );
    }
}
