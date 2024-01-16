package com.travel.reservation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class CurrentAdminSession {

    @Id
    @Column(unique = true)
    private Integer adminId;

    private String aid;

    private LocalDateTime time;

}
