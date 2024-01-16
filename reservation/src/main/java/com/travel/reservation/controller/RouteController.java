package com.travel.reservation.controller;

import com.travel.reservation.exception.AdminException;
import com.travel.reservation.exception.RouteException;
import com.travel.reservation.model.Route;
import com.travel.reservation.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/safari")
public class RouteController {

    @Autowired
    RouteService routeService;

    @PostMapping("/Admin/route/add")
    public ResponseEntity<Route> addRoute(@Valid @RequestBody Route route , @RequestParam(required = false) String key) throws RouteException, AdminException{

        Route newRoute = routeService.addRoute(route,key);
        return new ResponseEntity<Route>(newRoute , HttpStatus.ACCEPTED);
    }

    @GetMapping("/route/all")
    public ResponseEntity<List<Route>> getAllRouteHandler() throws RouteException{
        List<Route> routes = routeService.viewAllRoute();
        return new ResponseEntity<>(routes , HttpStatus.OK);
    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<Route>getAllRouteByRouteIdHandler(@PathVariable Integer routeId) throws RouteException{

        Route route= routeService.viewRoute(routeId);

        return new ResponseEntity<>(route,HttpStatus.OK);
    }
    @PutMapping("/admin/route/update")
    public ResponseEntity<Route> updateRoute(@Valid @RequestBody Route route , @RequestParam(required = false) String key) throws RouteException , AdminException{

        Route newRoute = routeService.updateRoute(route , key);
        return new ResponseEntity<Route>(newRoute , HttpStatus.OK);
    }

    @DeleteMapping("/admin/route/delete/{routeID}")
    public ResponseEntity<Route> DeleteRoute(@PathVariable Integer routeID,@RequestParam(required = false) String key) throws RouteException, AdminException{

        Route route = routeService.deleteRoute(routeID,key);

        return new ResponseEntity<Route>(route,HttpStatus.OK);
    }
}
