package com.travel.reservation.controller;

import com.travel.reservation.exception.AdminException;
import com.travel.reservation.exception.BusException;
import com.travel.reservation.model.Bus;
import com.travel.reservation.service.BusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/safari")
public class BusController {

    @Autowired
    private BusService busService;

    @PostMapping("/admin/bus/add")
    public ResponseEntity<Bus> addBusHandler(@Valid @RequestBody Bus bus , @RequestParam (required = false) String key) throws BusException , AdminException{

        Bus newBus = busService.addBus(bus , key);
        return new ResponseEntity<>(newBus , HttpStatus.CREATED);
    }
    @PutMapping("/admin/bus/update")
    public ResponseEntity<Bus> updateBusHandler(@Valid @RequestBody Bus bus,@RequestParam(required = false) String key) throws BusException, AdminException{

        Bus newBus = busService.updateBus(bus , key);
        return new ResponseEntity<>(newBus , HttpStatus.OK);
    }

    @DeleteMapping("/admin/bus/delete/{busId}")
    public ResponseEntity<Bus> deleteBusByBusIdHandler(@PathVariable ("busId") Integer busId, @RequestParam(required = false) String key) throws BusException,AdminException{

        Bus deletedBus = busService.deleteBus(busId , key);
        return new ResponseEntity<>(deletedBus , HttpStatus.OK);
    }

    //shared endpoints (user and admin both)
    @GetMapping("/bus/all")
    public ResponseEntity<List<Bus>> getAllBusesHandler()throws BusException{
        List<Bus> allBuses = busService.viewAllBuses();
        return new ResponseEntity<>(allBuses,HttpStatus.OK);
    }

    @GetMapping("/bus/{busId}")
    public ResponseEntity<Bus> getBusByIdHandler(@PathVariable("busId") Integer busId) throws BusException{
        Bus bus = busService.viewBus(busId);
        return new ResponseEntity<>(bus,HttpStatus.OK);
    }

    @GetMapping("/bus/type/{busType}")
    public ResponseEntity<List<Bus>> getBusesByBusTypeHandler(@PathVariable("busType") String busType) throws BusException{
        List<Bus> busList = busService.viewBusByBusType(busType);
        return new ResponseEntity<>(busList,HttpStatus.OK);
    }

}
