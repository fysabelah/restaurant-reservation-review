package com.restaurant.reservationreview.entities;

import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.enums.FoodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Document("restaurants")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant implements Serializable {

    @Id
    private String id;

    private String name;

    private Address address;

    private List<BusinessHours> businnessHours;

    private FoodType foodType;

    private Integer capacity;

    private LocalTime hourToConsiderReservation;

    private Integer quantityTablesAvailableReservation;

    private boolean active;

    public void setAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0200"));
        }

        this.address = address;
    }

    public void setBusinnessHours(List<BusinessHours> businnessHours) {
        if (businnessHours == null || businnessHours.isEmpty()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0203"));
        }

        this.businnessHours = businnessHours;
    }

    public void setHourToConsiderReservation(LocalTime hourToConsiderReservation) {
        if (hourToConsiderReservation == null) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0204"));
        }

        this.hourToConsiderReservation = hourToConsiderReservation;
    }

    public void setQuantityTablesAvailableReservation(Integer quantityTablesAvailableReservation) {
        if (quantityTablesAvailableReservation == null) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0205"));
        }

        this.quantityTablesAvailableReservation = quantityTablesAvailableReservation;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0005"));
        }

        this.name = name;
    }
}
