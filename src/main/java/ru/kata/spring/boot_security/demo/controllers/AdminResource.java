package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UsersServiceImpl;

@RestController
public class AdminResource {

    private final UsersServiceImpl usersService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminResource(UsersServiceImpl usersService, RoleServiceImpl roleService) {
        this.usersService = usersService;
        this.roleService = roleService;
    }

    @GetMapping("/api/v1/user/{userId}")
    public User findById(@PathVariable int userId) {
        System.out.println("-----------------------------xxxxxxxxxxxxxxxxxxx");
        System.out.println("-----------------------------xxxxxxxxxxxxxxxxxxx");
        System.out.println("-----------------------------xxxxxxxxxxxxxxxx");
        System.out.println("-----------------------------xxxxxxxxxxxxxxxxx");
        return usersService.showUser(userId);
    }

    @PostMapping("/api/v1/user")
    public User create(@RequestBody User user) {
        System.out.println("-----------------------------");
        System.out.println("-----------------------------");
        System.out.println("-----------------------------");
        System.out.println("-----------------------------");
        return usersService.save(user);
    }
}