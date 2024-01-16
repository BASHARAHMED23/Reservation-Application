package com.travel.reservation.service;

import com.travel.reservation.exception.AdminException;
import com.travel.reservation.exception.RouteException;
import com.travel.reservation.model.Bus;
import com.travel.reservation.model.CurrentAdminSession;
import com.travel.reservation.model.Route;
import com.travel.reservation.repository.AdminRepository;
import com.travel.reservation.repository.CurrentAdminSessionRepository;
import com.travel.reservation.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Service
public class RouteServiceImpl implements RouteService{

    @Autowired
    public RouteRepository routeRepository;

    @Autowired
    public CurrentAdminSessionRepository currentAdminSessionRepository;

    @Autowired
    public AdminRepository adminRepository;


    @Override
    public Route addRoute(Route route, String key) throws RouteException, AdminException {

        CurrentAdminSession currentAdminSession = currentAdminSessionRepository.findByAid(key);
        if(currentAdminSession == null) {
             throw new AdminException("Please provide a valid admin key!");
        }

        Route newRoute = routeRepository.findByRouteFromAndRouteTo(route.getRouteFrom(), route.getRouteTo());
        if(newRoute != null) throw new RouteException("Route :"+ newRoute.getRouteFrom() +" to "+ newRoute.getRouteTo()+ " is already present !");

        List<Bus> buses = new ArrayList<>();
        if(route != null) {
            route.setBusList(buses);
            return routeRepository.save(route);
        }
        else {
            throw new RouteException("This root is not available");
        }
    }

    @Override
    public List<Route> viewAllRoute() throws RouteException {

        List<Route> routes = routeRepository.findAll();

        if(routes==null){
            throw new  RouteException ("No routes available");
        }else
             return routes;
    }

    @Override
    public Route viewRoute(int routeId) throws RouteException {

        Optional<Route> opt = routeRepository.findById(routeId);

        return opt.orElseThrow(()->new RouteException("There is no route present of this  routeId :" + routeId));
    }

    @Override
    public Route updateRoute(Route route, String key) throws RouteException, AdminException {

        CurrentAdminSession loggedInAdmin = currentAdminSessionRepository.findByAid(key);

        if(loggedInAdmin == null) {
            throw new AdminException("Please provide a valid admin key!");
        }

        Optional<Route> existedRoute = routeRepository.findById(route.getRouteId());
        if(existedRoute.isPresent()) {

            Route presentRoute = existedRoute.get();
            List<Bus> busList = presentRoute.getBusList();

            if(!busList.isEmpty()) throw new RouteException("Cannot update running route! Buses are already scheduled in the route.");

            return routeRepository.save(route);
        }
        else
            throw new RouteException("Route doesn't exist of  this routeId : "+ route.getRouteId());
    }

    @Override
    public Route deleteRoute(int routeId, String key) throws RouteException, AdminException {


        CurrentAdminSession loggedInAdmin = currentAdminSessionRepository.findByAid(key);
        if(loggedInAdmin == null) {
            throw new AdminException("Please provide a valid admin key!");
        }

        Optional<Route> route=routeRepository.findById(routeId);

        if(route.isPresent()) {
            Route existingRoute=route.get();
            routeRepository.delete(existingRoute);
            return existingRoute;
        }
        else
            throw new RouteException("There is no route of this routeId : "+ routeId);

    }

    }
