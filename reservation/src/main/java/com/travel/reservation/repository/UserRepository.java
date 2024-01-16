package com.travel.reservation.repository;

import com.travel.reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User , Integer> {
    public User findByEmail(String email);

}
