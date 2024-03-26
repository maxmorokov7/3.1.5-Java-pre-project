package ru.kata.spring.boot_security.demo.exception;

public class UserNotFoundException extends RuntimeException {
    private String info;

    public UserNotFoundException() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
