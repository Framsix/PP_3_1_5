package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;

@Controller
@RequestMapping
public class UsersController {

    private final UsersRepository usersRepository;
    private final RoleServiceImpl roleService;
    @Autowired
    public UsersController(UsersRepository usersRepository, RoleServiceImpl roleService) {
        this.usersRepository = usersRepository;
        this.roleService = roleService;
    }
    @GetMapping("/user")
    public String userInfo(Model model) {
        model.addAttribute("allRoles", roleService.getAllRoles());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        // получаем данные пользователя по имени пользователя (username)
        User user = usersRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "/user";
    }

}
