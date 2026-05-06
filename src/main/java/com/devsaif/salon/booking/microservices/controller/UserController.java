package com.devsaif.salon.booking.microservices.controller;


import com.devsaif.salon.booking.microservices.exception.UserException;
import com.devsaif.salon.booking.microservices.model.User;
import com.devsaif.salon.booking.microservices.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody @Valid User user){
        return userRepo.save(user);
    }

    @GetMapping("/getusers")
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) throws Exception {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("user not found");
    }

    @PutMapping("/users/{id}")
    public User updateUser(@RequestBody User user,
                           @PathVariable Long id) throws Exception{

        Optional<User> findUser = userRepo.findById(id);
        if(findUser.isEmpty()){
            throw new UserException("user not found!");
        }
        User existingUser = findUser.get();

        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setUpdatedAt(LocalDateTime.now());
        existingUser.setRole(user.getRole());

        return userRepo.save(existingUser);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        userRepo.deleteAll();
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable Long id) throws Exception{
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()){
            throw new UserException("user not found!!");

        }
        userRepo.deleteById(user.get().getId());
        return "user deleted";

    }

   }
