package com.restaurant.reservationreview.controller;

import com.restaurant.reservationreview.controller.service.ReservationControlService;
import com.restaurant.reservationreview.controller.service.ReservationService;
import com.restaurant.reservationreview.util.dto.reservation.ReservationDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/reservation")
@Tag(name = "Reserva", description = "Controle de reservas")
public class ReservationController {

    @Resource
    private ReservationService reservationService;

    @Resource
    private ReservationControlService reservationControlService;

    @GetMapping(value="/available-dates")
    public ResponseEntity<List<LocalDate>> availableDates(@RequestParam("table") Integer table, @Valid @RequestBody ReservationDto dto) throws ValidationsException {
        return ResponseEntity.ok(reservationControlService.checkAvailableDates(dto, table));
    }

    @GetMapping(value="/available-hours")
    public ResponseEntity<List<LocalTime>> availableHours(@RequestParam("table") Integer table, @RequestParam("data") Date date, @Valid @RequestBody ReservationDto dto) throws ValidationsException {
        return ResponseEntity.ok(reservationControlService.availableHours(dto, table, date));
    }

    @PostMapping(value="/schedule")
    public ResponseEntity<ReservationDto> schedule(@RequestParam("table") Integer table, @RequestParam("data") Date date, @RequestParam("hour") LocalTime hour, @Valid @RequestBody ReservationDto dto) throws ValidationsException {
        return ResponseEntity.ok(reservationControlService.schedule(table, date, hour, dto));
    }

    @GetMapping(value="/reservationSearch")
    public ResponseEntity<ReservationDto> findByEmail(@Valid @RequestBody ReservationDto dto) throws ValidationsException {
        return ResponseEntity.ok(reservationService.findByEmail(dto));
    }
}
