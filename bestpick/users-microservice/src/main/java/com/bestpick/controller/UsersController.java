package com.bestpick.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @GetMapping()
    public String getUsers() {
        return "Hello World";
    }

}
