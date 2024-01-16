package com.travel.reservation.service;

import com.travel.reservation.exception.AdminException;
import com.travel.reservation.exception.LoginException;
import com.travel.reservation.model.Admin;
import com.travel.reservation.model.AdminLoginDTO;
import com.travel.reservation.model.CurrentAdminSession;
import com.travel.reservation.repository.AdminRepository;
import com.travel.reservation.repository.CurrentAdminSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private CurrentAdminSessionRepository currentAdminSessionRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public CurrentAdminSession adminLogin(AdminLoginDTO adminLoginDTO) throws LoginException, AdminException {
        List<Admin> admins = adminRepository.findByEmail(adminLoginDTO.getEmail());

        if(admins.isEmpty()) throw new AdminException("Please enter a valid email!");

        Admin registeredAdmin = admins.get(0);

        if(registeredAdmin == null) throw new AdminException("Please enter a valid email!");

        Optional<CurrentAdminSession> loggedInAdmin = currentAdminSessionRepository.findById(registeredAdmin.getAdminId());
        if(loggedInAdmin.isPresent()) throw new LoginException("Admin is already loggedIn!");

        if(registeredAdmin.getPassword().equals(adminLoginDTO.getPassword())){
            SecureRandom secureRandom = new SecureRandom();
            byte[] keyBytes = new byte[10];
            secureRandom.nextBytes(keyBytes);

            String key = Base64.getEncoder().encodeToString(keyBytes);

            CurrentAdminSession adminSession = new CurrentAdminSession();
            adminSession.setAdminId(registeredAdmin.getAdminId());
            adminSession.setAid(key);
            adminSession.setTime(LocalDateTime.now());
            return currentAdminSessionRepository.save(adminSession);
        }else
            throw new LoginException("Please enter valid password!");
    }

    @Override
    public String adminLogout(String key) throws LoginException {

        CurrentAdminSession currentAdminSession = currentAdminSessionRepository.findByAid(key);
        if(currentAdminSession == null) throw new LoginException("Invalid Admin login key!");
        currentAdminSessionRepository.delete(currentAdminSession);
        return "Admin logged out!";
    }
    }
