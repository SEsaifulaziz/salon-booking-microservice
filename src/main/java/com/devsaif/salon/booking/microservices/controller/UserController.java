package com.devsaif.salon.booking.microservices.controller;


import com.devsaif.salon.booking.microservices.model.User;
import com.devsaif.salon.booking.microservices.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user){
        User createUser = userService.createUser(user);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws Exception {
        User getById= userService.getUserById(id);
        return new ResponseEntity<>(getById, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user,
                           @PathVariable Long id) throws Exception{
        User updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        userService.deleteAllUsers();
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws Exception{
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.ACCEPTED);
    }

   }
