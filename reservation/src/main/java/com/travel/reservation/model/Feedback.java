package com.travel.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedbackId;

    @Min(value=1, message="Rating must be in range of 1-5")
    @Max(value=5, message="Rating must be in range of 1-5")
    private Integer driverRating;

    @Min(value=1, message="Rating must be in range of 1-5")
    @Max(value=5, message="Rating must be in range of 1-5")
    private Integer serviceRating;

    @Min(value=1, message="Rating must be in range of 1-5")
    @Max(value=5, message="Rating must be in range of 1-5")
    private Integer overallRating;

    private String comments;

    private LocalDateTime feedbackDateTime;

    @OneToOne
    private User user;

    @OneToOne
    private Bus bus;

}
