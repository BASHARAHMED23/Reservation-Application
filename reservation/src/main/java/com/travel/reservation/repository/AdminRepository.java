package com.travel.reservation.repository;

import com.travel.reservation.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin , Integer> {

    List<Admin> findByEmail(String email);
}
