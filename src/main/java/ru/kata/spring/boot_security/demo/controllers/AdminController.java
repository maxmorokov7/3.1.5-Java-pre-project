package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exception.UserExistException;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@CrossOrigin
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }
    @ResponseBody
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser (@PathVariable("id") Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserExistException("There is no user with this ID");
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<UserNotFoundException> handleException(UserExistException exception) {
        UserNotFoundException data = new UserNotFoundException();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<UserNotFoundException> handleException(Exception exception) {
        UserNotFoundException data = new UserNotFoundException();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/")
    public String table(Model model,@CurrentSecurityContext(expression = "authentication.principal") User principal) {
        model.addAttribute("table", userService.getAllUsers());
        model.addAttribute("user", principal);
        return "table";
    }
    @ResponseBody
    @PostMapping("/new")
    public ResponseEntity<User> add(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok().body(user);
    }
    @ResponseBody
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        userService.removeUser(id);
        return ResponseEntity.ok().body("User removed");
    }
    @ResponseBody
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Integer id, @RequestBody User user) {
        user.setUserId(id);
        userService.updateUser(user);
        return ResponseEntity.ok().body("User updated");
    }
}