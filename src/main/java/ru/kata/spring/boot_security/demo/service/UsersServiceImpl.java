package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public void add(User user) {
        usersRepository.save(user);
    }


    public void delete(User user) {
        usersRepository.delete(user);
    }


    public List<User> read() {
        return usersRepository.findAll();
    }


    public void update(User user) {
        usersRepository.save(user);
    }

    public User showUser(int id) {
        return usersRepository.getOne(id);
    }


    public User findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }


    public void deleteById(int id) {
        usersRepository.deleteById(id);
    }
}
