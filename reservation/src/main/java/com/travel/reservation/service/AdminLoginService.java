package com.travel.reservation.service;

import com.travel.reservation.exception.AdminException;
import com.travel.reservation.exception.LoginException;
import com.travel.reservation.model.AdminLoginDTO;
import com.travel.reservation.model.CurrentAdminSession;

public interface AdminLoginService {

    public CurrentAdminSession adminLogin(AdminLoginDTO adminLoginDTO) throws LoginException , AdminException;
    public String adminLogout (String key) throws LoginException;

}
