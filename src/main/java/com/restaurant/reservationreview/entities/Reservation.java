package com.restaurant.reservationreview.model.documents;

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
public class Reservation implements Serializable {

    @Id
    private String id;

    @DBRef
    private Restaurant restaurant;

    private Person person;

    private LocalDateTime dateAndTime;

    private DayOfWeek dayOfWeek;

    private Integer reservationAmount;

}
