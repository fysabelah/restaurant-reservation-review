package com.restaurant.reservationreview.framework.web;

import com.restaurant.reservationreview.interfaceadapters.controllers.ReservationController;
import com.restaurant.reservationreview.interfaceadapters.gateways.ReservationControlGateway;
import com.restaurant.reservationreview.interfaceadapters.gateways.ReservationGateway;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/reservation")
@Tag(name = "Reserva", description = "Controle de reservas")
public class ReservationWeb {

    @Resource
    private ReservationController reservationController;

    @Resource
    private ReservationGateway reservationGateway;

    @Resource
    private ReservationControlGateway reservationControlGateway;

    @GetMapping(value="/available-dates/{restaurantId}")
    public ResponseEntity<List<LocalDate>> availableDates(@PathVariable String restaurantId,
                                                          @RequestParam("table") Integer table) throws ValidationsException {
        return ResponseEntity.ok(reservationController.checkAvailableDates(restaurantId, table));
    }

    @GetMapping(value="/available-hours/{restaurantId}")
    public ResponseEntity<List<LocalTime>> availableHours(@PathVariable String restaurantId,
                                                          @RequestParam("table") Integer table,
                                                          @RequestParam("data") LocalDate date) throws ValidationsException {
        return ResponseEntity.ok(reservationController.checkAvailableHours(restaurantId, table, date));
    }

    @PostMapping(value="/schedule/{restaurantId}")
    public ResponseEntity<ReservationDto> schedule(@PathVariable String restaurantId,
                                                   @RequestParam("table") Integer table,
                                                   @RequestParam("data") LocalDate date,
                                                   @RequestParam("hour") String hour,
                                                   @Valid @RequestBody ReservationDto dto) throws ValidationsException {
        LocalTime parsedHour = LocalTime.parse(hour, DateTimeFormatter.ofPattern("HH:mm"));
        return ResponseEntity.ok(reservationController.schedule(restaurantId, table, date, parsedHour, dto));
    }

    @GetMapping(value="/reservationSearch")
    public ResponseEntity<ReservationDto> findByEmail(@Valid @RequestBody ReservationDto dto) throws ValidationsException {
        return ResponseEntity.ok(reservationController.findByEmail(dto));
    }
}
