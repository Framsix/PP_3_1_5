package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Users;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UsersService;

@RestController
public class AdminResource {

    private final UsersService usersService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminResource(UsersService usersService, RoleServiceImpl roleService) {
        this.usersService = usersService;
        this.roleService = roleService;
    }

    @GetMapping("/api/v1/user/{userId}")
    public Users findById(@PathVariable int userId) {
        return usersService.showUser(userId);
    }

    @PostMapping("/api/v1/user")
    public Users create(@RequestBody Users user) {
        return usersService.save(user);
    }
}