package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;
import ru.kata.spring.boot_security.demo.service.UsersDetailService;

import java.security.Principal;

@Controller
@RequestMapping
public class UsersController {

    private final UsersRepository usersRepository;
    @Autowired
    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @GetMapping("/user")
    public String findAll(Model model, Principal principal){
        model.addAttribute("user", usersRepository.findByUsername(principal.getName()));
        return "user";
    }

}
