package com.restaurant.reservationreview.model.documents.reservation;

import com.restaurant.reservationreview.model.documents.restaurant.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Document("reservationControl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationControl implements Serializable {

    @DBRef
    private Restaurant restaurant;

    private LocalDateTime dateAndTime;

    private DayOfWeek dayOfWeek;

    private Integer totalReservations;

    private Integer capacity;

    private boolean available;

}
