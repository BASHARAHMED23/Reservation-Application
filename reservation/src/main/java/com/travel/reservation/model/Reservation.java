package com.travel.reservation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {

    private Integer reservationId;
    private String status;
    private LocalDate date;
    private LocalTime time;

    private String source;
    private String destination;
    private LocalDate JourneyDate;
    private Integer bookedSeat;
    private Integer fare;

    @ManyToOne
    private User user;

    @ManyToMany
    private Bus bus;
}
