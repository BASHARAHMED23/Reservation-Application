package com.travel.reservation.repository;

import com.travel.reservation.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route , Integer> {

    public Route findByRouteFromAndRouteTo(String from ,String to);
}
