package com.travel.reservation.repository;

import com.travel.reservation.model.CurrentAdminSession;
import com.travel.reservation.model.CurrentUserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CurrentAdminSessionRepository extends JpaRepository<CurrentAdminSession , Integer> {

    @Query("select c from CurrentAdminSession c where c.aid=?1")
    public CurrentAdminSession findByAid(String aid);

}
