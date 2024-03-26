package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepositories;

import javax.validation.Valid;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepositories userDao;

    @Autowired
    public UserServiceImpl(UserRepositories userDAO) {
        this.userDao = userDAO;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public void addUser(@Valid User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public void removeUser(int id) {
        userDao.deleteById(id);
    }

    @Override
    public void updateUser(@Valid User user) {
        User user1 = getUserById(user.getUserId());
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        if (!user.getPassword().isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user1.setPassword(encoder.encode(user.getPassword()));
        }
        user1.setRole((List<Role>) user.getAuthorities());
        userDao.save(user1);
    }
}