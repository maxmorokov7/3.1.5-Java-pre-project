package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.User;
import javax.validation.Valid;
import java.util.List;

public interface UserService {
    List<User> getAllUsers ();
    User getUserById(int id);
    void addUser(User user);
    void removeUser(int id);
    void updateUser(@Valid User user);
}