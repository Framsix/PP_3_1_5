package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UsersService;

@Controller
public class AdminController {
    private final UsersService usersService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminController(UsersService usersService, RoleServiceImpl roleService) {
        this.usersService = usersService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users", usersService.read());
        model.addAttribute("allRoles", roleService.getAllRoles());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        // получаем данные пользователя по имени пользователя (username)
        User user = usersService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("registrationUser", new User());
        model.addAttribute("editUser", new User());
        return "users/GetUsers";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", usersService.showUser(id));
        return "users/showUser";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        System.out.println(model);
        return "users/newUser";
    }

    @PostMapping("/save")
    public String createUser(@ModelAttribute("user") User user) {
        if (!("".equals(user.getName()))) {
            usersService.add(user);
        }
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", usersService.showUser(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "users/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        usersService.update(user);
        System.out.println(user);;
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        usersService.delete(user);
        return "redirect:/admin";
    }


}
