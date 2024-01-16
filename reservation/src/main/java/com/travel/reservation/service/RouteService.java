package com.travel.reservation.service;

import com.travel.reservation.exception.AdminException;
import com.travel.reservation.exception.RouteException;
import com.travel.reservation.model.Route;

import java.util.List;

public interface RouteService {

    public Route addRoute(Route route , String key) throws RouteException , AdminException;

    public List<Route> viewAllRoute() throws RouteException;

    public Route viewRoute(int routeId) throws RouteException;

    public Route updateRoute(Route route, String key) throws RouteException , AdminException;

    public Route deleteRoute(int routeId , String key) throws RouteException , AdminException;

}
