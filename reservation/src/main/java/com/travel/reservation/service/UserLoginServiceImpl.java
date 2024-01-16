package com.travel.reservation.service;

import com.travel.reservation.exception.LoginException;
import com.travel.reservation.model.CurrentUserSession;
import com.travel.reservation.model.User;
import com.travel.reservation.model.UserLoginDTO;
import com.travel.reservation.repository.CurrentUserSessionRepository;
import com.travel.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserLoginServiceImpl implements UserLoginService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;

    @Override
    public CurrentUserSession userLogin(UserLoginDTO userLoginDTO) throws LoginException {

        User registeredUser = userRepository.findByEmail(userLoginDTO.getEmail());
        if(registeredUser == null) throw new LoginException("Please enter valid email!");

        Optional<CurrentUserSession> loggedInUser =  currentUserSessionRepository.findById(registeredUser.getUserId());
        if(loggedInUser.isPresent()) throw new LoginException("User already Logged!");

        if(registeredUser.getPassword().equals(userLoginDTO.getPassword())) {
            SecureRandom secureRandom = new SecureRandom();
            byte[] keyBytes = new byte[10];
            secureRandom.nextBytes(keyBytes);

            String key = Base64.getEncoder().encodeToString(keyBytes);

            CurrentUserSession currentUserSession = new CurrentUserSession();
            currentUserSession.setUserId(registeredUser.getUserId());
            currentUserSession.setUuid(key);
            currentUserSession.setTime(LocalDateTime.now());
            return currentUserSessionRepository.save(currentUserSession);
        }else
            throw new LoginException("Please enter a valid password!");
    }

    @Override
    public String userLogout(String key) throws LoginException {
        CurrentUserSession loggedInUser = currentUserSessionRepository.findByUuid(key);
        if(loggedInUser == null)  throw new LoginException("Please enter a valid key or login first!");
        currentUserSessionRepository.delete(loggedInUser);
        return "User logged out!";
    }
}
