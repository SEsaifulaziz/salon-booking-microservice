package com.devsaif.service.impl;

import com.devsaif.exception.UserException;
import com.devsaif.model.User;
import com.devsaif.repository.UserRepository;
import com.devsaif.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Override
    public User createUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserException {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("user not found");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUserById(Long id) throws UserException {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()){
            throw new UserException("user not found!!");

        }
        userRepo.deleteById(user.get().getId());
    }

    @Override
    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    @Override
    public User updateUser(Long id, User user) throws UserException {
        Optional<User> findUser = userRepo.findById(id);
        if(findUser.isEmpty()){
            throw new UserException("user not found!");
        }
        User existingUser = findUser.get();

        existingUser.setFullName(user.getFullName());
        existingUser.setUserName(user.getUserName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setUpdatedAt(LocalDateTime.now());
        existingUser.setRole(user.getRole());

        return userRepo.save(existingUser);
    }
}
