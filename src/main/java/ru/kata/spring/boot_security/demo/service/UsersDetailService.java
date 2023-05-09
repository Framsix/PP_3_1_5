package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.Users;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersDetailService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersDetailService(UsersRepository usersRepository, RoleRepository roleRepository) {
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
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User ot found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
    }
}
