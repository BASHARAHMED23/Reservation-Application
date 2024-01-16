package com.travel.reservation.service;

import com.travel.reservation.exception.AdminException;
import com.travel.reservation.exception.UserException;
import com.travel.reservation.model.CurrentAdminSession;
import com.travel.reservation.model.CurrentUserSession;
import com.travel.reservation.model.User;
import com.travel.reservation.repository.CurrentAdminSessionRepository;
import com.travel.reservation.repository.CurrentUserSessionRepository;
import com.travel.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;

    @Autowired
    private CurrentAdminSessionRepository currentAdminSessionRepository;

    @Override
    public User createUser(User user) throws UserException {

        User registeredUser = userRepository.findByEmail(user.getMobile());

        if(registeredUser != null) throw new UserException("User is already registered!");

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, String key) throws UserException {

        CurrentUserSession loggedInUser = currentUserSessionRepository.findByUuid(key);
        if(loggedInUser == null)  throw new UserException("Please enter a valid key or login first!");
        if(user.getUserId() != loggedInUser.getUserId()) throw new UserException("Invalid user details, please login for updating user!");
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(Integer userId, String key) throws UserException, AdminException {

        CurrentAdminSession loggedInAdmin = currentAdminSessionRepository.findByAid(key);
        if(loggedInAdmin==null) throw new AdminException("Please enter a valid key or login first!");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("Invalid user Id!"));
        userRepository.delete(user);
        return user;
    }

    @Override
    public User viewUserById(Integer userId, String key) throws UserException, AdminException {

        CurrentAdminSession loggedInAdmin = currentAdminSessionRepository.findByAid(key);
        if(loggedInAdmin==null) throw new AdminException("Please enter a valid key or login first!");

        User user = userRepository.findById(userId).orElseThrow(()-> new UserException("Invalid user id"));
        return user;
    }

    @Override
    public List<User> viewAllUsers(String key) throws UserException, AdminException {

        CurrentAdminSession loggedInAdmin = currentAdminSessionRepository.findByAid(key);
        if(loggedInAdmin == null)  throw new AdminException("Please enter a valid key or login first!");

        List<User> list = userRepository.findAll();

        if(list.isEmpty()) throw new UserException("No users found");

        return list;
    }


}
