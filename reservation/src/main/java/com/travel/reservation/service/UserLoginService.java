package com.travel.reservation.service;

import com.travel.reservation.exception.LoginException;
import com.travel.reservation.model.CurrentUserSession;
import com.travel.reservation.model.UserLoginDTO;

public interface UserLoginService {
    public CurrentUserSession userLogin(UserLoginDTO userLoginDTO) throws LoginException;

    public String userLogout(String key) throws LoginException;
}
