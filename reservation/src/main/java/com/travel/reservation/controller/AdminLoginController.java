package com.travel.reservation.controller;


import com.travel.reservation.exception.AdminException;
import com.travel.reservation.exception.LoginException;
import com.travel.reservation.model.AdminLoginDTO;
import com.travel.reservation.model.CurrentAdminSession;
import com.travel.reservation.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/safari")
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @PostMapping("/admin/login")
    public ResponseEntity<CurrentAdminSession> loginAdmin(@RequestBody AdminLoginDTO loginDTO)throws AdminException, LoginException{

        CurrentAdminSession currentAdminSession = adminLoginService.adminLogin(loginDTO);
        return new ResponseEntity<>(currentAdminSession, HttpStatus.ACCEPTED);
    }

    @PostMapping("/admin/logout")
    public String logoutAdmin(@RequestParam(required = false) String key)throws LoginException{
        return adminLoginService.adminLogout(key);
    }

}
