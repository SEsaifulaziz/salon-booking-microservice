package com.devsaif.salon.booking.microservices.controller;


import com.devsaif.salon.booking.microservices.model.User;
import com.devsaif.salon.booking.microservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@RequiredArgsConstructor
public class UserController {


    private UserRepository userRepo;

    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        return userRepo.save(user);
    }
}
