package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UsersServiceImpl;
import ru.kata.spring.boot_security.demo.util.UserErrorResponse;
import ru.kata.spring.boot_security.demo.util.UserNonCreatedException;
import ru.kata.spring.boot_security.demo.util.UserNonEditException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AdminRestController {

    private final UsersServiceImpl usersService;


    public AdminRestController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(usersService.read(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public User findById(@PathVariable int userId) {
        return usersService.showUser(userId);
    }

    @PostMapping("/user")
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserNonCreatedException("Error Create User");
        }
        usersService.add(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PatchMapping("/user/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("id") int id, @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserNonEditException("Error Edit User");
        }
        usersService.update(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        usersService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNonCreatedException e) {
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNonEditException e) {
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}