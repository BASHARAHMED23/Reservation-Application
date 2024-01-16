package com.travel.reservation.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class CurrentUserSession {

    @Id
    @Column(unique = true)
    private Integer userId;
    private String uuid;
    private LocalDateTime time;

}
