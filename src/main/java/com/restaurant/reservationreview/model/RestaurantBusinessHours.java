package com.restaurant.reservationreview.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class RestaurantBusinessHours {
    private String restaurante_Id;

    private Integer diaSemana;

    private LocalDateTime HoraInicio;

    private LocalDateTime HoraFinal;

}
