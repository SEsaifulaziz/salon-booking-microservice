package com.devsaif.salon.booking.microservices.controller;


import com.devsaif.salon.booking.microservices.model.User;
import com.devsaif.salon.booking.microservices.repository.UserRepository;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/api")
public class UserController {

    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        return userRepo.save(user);
    }

    @GetMapping("/getusers")
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    @GetMapping("/byId/{id}")
    public User getUserById(@PathVariable Long id) throws Exception {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new Exception("user not found");
    }



   }
