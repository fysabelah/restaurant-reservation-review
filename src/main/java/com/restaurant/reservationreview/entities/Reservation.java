package com.restaurant.reservationreview.entities;

import com.restaurant.reservationreview.util.enums.StatusReservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Document("reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation implements Serializable {

    @Id
    private String id;

    @DBRef
    private Restaurant restaurant;

    private Person person;

    private LocalDateTime date;

    private DayOfWeek dayOfWeek;

    private Integer tables;

    private StatusReservation status;

}
