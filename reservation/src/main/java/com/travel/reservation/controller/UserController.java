package com.travel.reservation.controller;

import com.travel.reservation.exception.AdminException;
import com.travel.reservation.exception.UserException;
import com.travel.reservation.model.User;
import com.travel.reservation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/safari")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<User> registeredUser(@Valid @RequestBody User user) throws UserException{

        User savedUser = userService.createUser(user);
        return new ResponseEntity<User>(savedUser , HttpStatus.CREATED);
    }
    @PutMapping("/user/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user , @RequestParam (required = false) String key) throws UserException{
        User updatedUser = userService.updateUser(user, key);
        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/admin/user/delete/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Integer userId , @RequestParam(required = false) String key) throws UserException , AdminException{
        User deleteUser = userService.deleteUser(userId , key);
        return new ResponseEntity<User>(deleteUser , HttpStatus.OK);
    }

    @GetMapping("/admin/user/{userId}")
    public ResponseEntity<User> viewUserById(@PathVariable("userId") Integer userId , @RequestParam(required = false) String key) throws UserException , AdminException{

        User user = userService.viewUserById(userId , key);
        return new ResponseEntity<User>(user , HttpStatus.FOUND);
    }

    @GetMapping("/admin/user/all")
    public  ResponseEntity<List<User>> viewAllUser(@RequestParam(required = false) String key) throws UserException, AdminException {
        List<User> list = userService.viewAllUsers(key);
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }
}
