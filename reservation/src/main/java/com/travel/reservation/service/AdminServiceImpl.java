package com.travel.reservation.service;

import com.travel.reservation.exception.AdminException;
import com.travel.reservation.model.Admin;
import com.travel.reservation.model.CurrentAdminSession;
import com.travel.reservation.repository.AdminRepository;
import com.travel.reservation.repository.CurrentAdminSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CurrentAdminSessionRepository currentAdminSessionRepository;

    @Override
    public Admin createAdmin(Admin admin) throws AdminException {

        List<Admin> admins = adminRepository.findByEmail(admin.getEmail());
        if(!admins.isEmpty()) throw new AdminException("Admin already present with the given email: " + admin.getEmail());

        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdmin(Admin admin , String key) throws AdminException {
        CurrentAdminSession adminSession = currentAdminSessionRepository.findByAid(key);
        if(adminSession == null) throw new AdminException("Please enter valid key or login first!");
        if(admin.getAdminId() != adminSession.getAdminId()) throw new AdminException("Invalid admin details, please login for updating admin!");
        return adminRepository.save(admin);
    }
}
