package com.devsaif.service;

import com.devsaif.exception.UserException;
import com.devsaif.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id) throws UserException;
    List<User> getAllUsers();
    void deleteUserById(Long id) throws UserException;
    void deleteAllUsers();
    User updateUser(Long id, User user) throws UserException;
    User getUserFromJwt(String jwt) throws Exception;
}
