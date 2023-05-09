package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository <Users, Integer> {
    Users findByUsername(String username);
}
