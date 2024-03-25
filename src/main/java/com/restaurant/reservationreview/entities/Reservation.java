package com.restaurant.reservationreview.entities;

import com.restaurant.reservationreview.util.MessageUtil;
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

    public void setRestaurant(Restaurant restaurant) {
        if (restaurant == null) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0300"));
        }

        this.restaurant = restaurant;
    }

    public void setPerson(Person person) {
        if (person == null) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0301"));
        }

        this.person = person;
    }

    public void setDate(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0302"));
        }

        this.date = date;
    }

    public void setTables(Integer tables) {
        if (tables == null || tables <= 0) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0303"));
        }

        this.tables = tables;
    }
}
