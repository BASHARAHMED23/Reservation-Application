package com.travel.reservation.service;

import com.travel.reservation.exception.AdminException;
import com.travel.reservation.model.Admin;

public interface AdminService {
    public Admin createAdmin(Admin admin) throws AdminException;
    public Admin updateAdmin(Admin admin , String key) throws AdminException;


}
