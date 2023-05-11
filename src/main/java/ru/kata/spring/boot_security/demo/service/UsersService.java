package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Users;

import java.util.List;

public interface UsersService {
    void add(Users user);
    void delete(Users user);
    List<Users> read();
    void update(Users user);
    Users showUser(int id);
    Users findByUsername(String username);
}
