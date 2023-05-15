package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UsersService {
    void add(User user);
    void delete(User user);
    List<User> read();
    void update(User user);
    User showUser(int id);
    User findByUsername(String username);
    User save(User user);
}
