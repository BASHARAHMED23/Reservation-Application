package com.travel.reservation.controller;

import com.travel.reservation.exception.AdminException;
import com.travel.reservation.model.Admin;
import com.travel.reservation.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/safari")
public class AdminController {

    private AdminService adminService;

    @PostMapping("/admin/register")
    public ResponseEntity<Admin> registerAdmin(@Valid @RequestBody Admin admin) throws AdminException{

        System.out.println("Admin Controller: " + admin);
        Admin saveedAdmin = adminService.createAdmin(admin);
        return  new ResponseEntity<>(saveedAdmin , HttpStatus.CREATED);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<Admin> updateAdmin(@Valid @RequestBody Admin admin , @RequestParam(required = false) String key) throws AdminException{

        Admin updateAdmin = adminService.updateAdmin(admin , key);
        return new  ResponseEntity<>(updateAdmin,HttpStatus.OK);
    }


}
