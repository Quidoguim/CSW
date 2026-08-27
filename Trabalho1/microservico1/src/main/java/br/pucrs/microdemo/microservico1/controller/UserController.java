package br.pucrs.microdemo.microservico1.controller;

import br.pucrs.microdemo.microservico1.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user")
    public User getUser(@RequestParam("fn") String firstName, @RequestParam("ln") String lastName) {
        System.out.println("microservico1: " + firstName + " " + lastName);
        return new User(firstName + firstName, lastName + lastName);
    }
}
