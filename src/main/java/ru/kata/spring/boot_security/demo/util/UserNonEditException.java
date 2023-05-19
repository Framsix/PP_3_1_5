package ru.kata.spring.boot_security.demo.util;

public class UserNonEditException extends RuntimeException {
    public UserNonEditException(String msg) {
        super(msg);
    }
}
