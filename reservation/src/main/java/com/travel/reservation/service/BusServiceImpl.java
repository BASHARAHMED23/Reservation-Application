package com.travel.reservation.service;

import com.travel.reservation.exception.AdminException;
import com.travel.reservation.exception.BusException;
import com.travel.reservation.model.Bus;
import com.travel.reservation.model.CurrentAdminSession;
import com.travel.reservation.model.Route;
import com.travel.reservation.repository.BusRepository;
import com.travel.reservation.repository.CurrentAdminSessionRepository;
import com.travel.reservation.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BusServiceImpl implements BusService{

    @Autowired
    public BusRepository busRepository;

    @Autowired
    public RouteRepository routeRepository;

    @Autowired
    public CurrentAdminSessionRepository currentAdminSessionRepository;

    @Override
    public Bus addBus(Bus bus, String key) throws BusException, AdminException {

        CurrentAdminSession adminSession = currentAdminSessionRepository.findByAid(key);
        if(adminSession == null) throw new AdminException("Key is not valid! Please provide a valid key.");

        Route route = new Route(bus.getRouteFrom() , bus.getRouteTo(),bus.getRoute().getDistance());
        if(route == null) throw new BusException("Route is not valid");

        bus.setRoute(route);

        route.getBusList().add(bus);

        return busRepository.save(bus);
    }

    @Override
    public Bus updateBus(Bus bus, String key) throws BusException, AdminException {

        CurrentAdminSession admin = currentAdminSessionRepository.findByAid(key);
        if(admin==null){
            throw new AdminException("Key is not valid! Please provide a valid key.");
        }
        Optional<Bus> bus1 = busRepository.findById(bus.getBusId());
        if(!bus1.isPresent()){
            throw new BusException("Bus doesn't exist with busId: "+ bus.getBusId());
        }
        Bus existBus = bus1.get();
        //checking if bus scheduled or not , can be updated only if not scheduled
        //if(existBus.getAvailableSeats() != existBus.getSeats()) throw new BusException("Scheduled bus can't be updated");

        Route route = routeRepository.findByRouteFromAndRouteTo(bus.getRouteFrom(),bus.getRouteTo()); //finding and checking route => pending

//           if(route==null) throw new BusException("Route is not valid");
        if(route == null){
            Route route1 = new Route(bus.getRouteFrom(),bus.getRouteTo(),bus.getRoute().getDistance());
            routeRepository.save(route1);
            bus.setRoute(route1);
            return busRepository.save(bus);
        }
        routeRepository.save(route);
        bus.setRoute(route);
        return busRepository.save(bus);
    }

    @Override
    public Bus deleteBus(Integer busId, String key) throws BusException, AdminException {

        CurrentAdminSession admin = currentAdminSessionRepository.findByAid(key);
        if(admin==null){
            throw new AdminException("Key is not valid! Please provide a valid key.");
        }

        Optional<Bus> bus = busRepository.findById(busId);

        if(bus.isPresent()){
            Bus existingBus = bus.get();
            //checking if current date is before journey date it means bus scheduled so can't delete / or seats are reserved or not
            if(LocalDate.now().isBefore(existingBus.getBusJourneyDate()) && existingBus.getAvailableSeats()!=existingBus.getSeats()){
                throw new BusException("Can't delete scheduled and reserved bus.");
            }
            busRepository.delete(existingBus);
            return existingBus;
        }else throw  new BusException("Bus not found with busId: "+busId);
    }

    @Override
    public Bus viewBus(Integer busId) throws BusException {

        Optional<Bus> bus = busRepository.findById(busId);

        if (!bus.isPresent()) throw new BusException("No bus exist with this busId: "+ busId);

        return bus.get();
    }

    @Override
    public List<Bus> viewBusByBusType(String busType) throws BusException {

        List<Bus> busListType = busRepository.findByBusType(busType);
        if(busListType.isEmpty()){
            throw new BusException("There are no buses with bus type: "+ busType);
        }
        return busListType;
    }

    @Override
    public List<Bus> viewAllBuses() throws BusException {

        List<Bus> busList = busRepository.findAll();
        if(busList.isEmpty()){
            throw new BusException("No bus found at this moment. Try again later!");
        }
        return busList;
    }
}
