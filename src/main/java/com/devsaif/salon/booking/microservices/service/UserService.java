package com.devsaif.salon.booking.microservices.service;

import com.devsaif.salon.booking.microservices.exception.UserException;
import com.devsaif.salon.booking.microservices.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id) throws UserException;
    List<User> getAllUsers();
    void deleteUserById(Long id) throws UserException;
    void deleteAllUsers();
    User updateUser(Long id, User user) throws UserException;
}
