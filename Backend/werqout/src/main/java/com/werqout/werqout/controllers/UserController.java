package com.werqout.werqout.controllers;

import java.util.Optional;
import java.util.List;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/")
// change to abstract class??
public interface UserController {

    @GetMapping("/")
    List<UserController> getAll(); 
    
    @GetMapping("/{id}")
    Optional<UserController> getById(@PathVariable Long id);
    
    @PostMapping("/save/{id}")
    public void save(@RequestBody UserController user, @PathVariable Long id);

    @DeleteMapping("/delete/{id}")
    public String delete(@RequestBody UserController user, @PathVariable Long id);
}
