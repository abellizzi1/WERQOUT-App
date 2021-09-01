package com.requests.requestapp;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Simple application to return a string on different types of requests. 
 * @author JJ SchraderBachar
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public String getUsers(){
        return "HTTP GET request";
    }
    @DeleteMapping
    public String deleteUser(){
        return "HTTP DELETE request";
    }

    @PostMapping
    public String createUser(){
        return "HTTP Post request";
    }
    //tested with postman and it worked
}

