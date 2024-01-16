package com.travel.reservation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.apache.logging.log4j.message.Message;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @NotNull(message = "Name cannot be null!")
    @NotBlank(message = "Name cannot be Blank!")
    private String firstName;
    private String lastName;

    @NotNull(message="Mobile number cannot be null!")
    @NotBlank(message= "Mobile number cannot be blank!")
    @Pattern(regexp =  "[6789]{1}[0-9]{9}", message = "Enter valid 10 digit mobile number")
    @Size(min = 11 , max = 11)
    private String mobile;

    @Email
    private String email;

    @NotNull(message="Password cannot be null!")
    @NotBlank(message= "Password cannot be blank!")
    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters including alphanumerics and special characters")
    private String password;


    @JsonIgnore
    @OneToMany
    private List<Reservation> reservations = new ArrayList<>();

    @JsonIgnore
    @OneToMany
    private List<Feedback> feedbacks = new ArrayList<>();
}
