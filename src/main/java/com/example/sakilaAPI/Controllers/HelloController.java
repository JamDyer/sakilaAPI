package com.example.sakilaAPI.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloController {
    @GetMapping ("/greeting/{id}{name}{insult}")
    public String greet(@PathVariable Integer id, String name, String insult) {
        final String actualName = name == null ? "Jamie" : name;
        final String actualInsult = insult == null ? "loser" : insult;
        return "Hello User " + id + "\n Your name is: " + actualName + "\n You're probably a " + actualInsult;
    }
}
