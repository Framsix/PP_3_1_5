package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Users;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public void add(Users user) {
        usersRepository.save(user);
    }

    @Transactional
    public void delete(Users user) {
        usersRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public List<Users> read() {
        return usersRepository.findAll();
    }

    @Transactional
    public void update(Users user) {
        usersRepository.save(user);
    }
    @Transactional
    public Users showUser(int id) {
        return usersRepository.getOne(id);
    }
    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}
