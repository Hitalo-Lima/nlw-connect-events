package br.com.nlw.events.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.nlw.events.model.User;
import br.com.nlw.events.service.UserService;

@RestController
public class UserController {
   
    @Autowired
    private UserService userService;

    @GetMapping("users")
    public List<User> listAllUsers() {
        return userService.listAllUsers();
    }

    @GetMapping("user/{userId}")
    public User getUserById(@PathVariable Integer userId) {
        return userService.findUserById(userId);
    }
}
