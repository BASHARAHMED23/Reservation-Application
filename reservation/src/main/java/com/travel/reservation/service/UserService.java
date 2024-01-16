package com.travel.reservation.service;

import com.travel.reservation.exception.AdminException;
import com.travel.reservation.exception.UserException;
import com.travel.reservation.model.User;

import java.util.List;

public interface UserService {

    public User createUser(User user) throws UserException;
    public User updateUser(User user , String key) throws UserException;

    public User deleteUser(Integer userId , String key) throws UserException, AdminException;

    public User viewUserById(Integer userId , String key) throws UserException , AdminException;

    public List<User>viewAllUsers(String key) throws UserException , AdminException;

}
