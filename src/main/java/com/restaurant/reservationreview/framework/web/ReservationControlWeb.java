package com.restaurant.reservationreview.framework.web;

import com.restaurant.reservationreview.interfaceadapters.gateways.RatingGateway;
import com.restaurant.reservationreview.interfaceadapters.gateways.ReservationControlGateway;
import com.restaurant.reservationreview.interfaceadapters.gateways.ReservationGateway;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RatingDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = "/reservation")
@Tag(name = "Reserva", description = "Controle de reservas")
public class ReservationControlWeb {
    @Resource
    private ReservationGateway reservationGateway;

    @Resource
    private ReservationControlGateway reservationControlGateway;

    @GetMapping(value="/available-dates")
    public ResponseEntity<List<LocalDate>> availableDates(@RequestParam("table") Integer table, @Valid @RequestBody ReservationDto dto) throws ValidationsException {
        return ResponseEntity.ok(reservationControlGateway.checkAvailableDates(dto, table));
    }

    @GetMapping(value="/available-hours")
    public ResponseEntity<List<LocalTime>> availableHours(@RequestParam("table") Integer table, @RequestParam("data") LocalDate date, @Valid @RequestBody ReservationDto dto) throws ValidationsException {
        return ResponseEntity.ok(reservationControlGateway.checkAvailableHours(dto, table, date));
    }

    @PostMapping(value="/schedule")
    public ResponseEntity<ReservationDto> schedule(@RequestParam("table") Integer table, @RequestParam("data") LocalDate date, @RequestParam("hour") LocalTime hour, @Valid @RequestBody ReservationDto dto) throws ValidationsException {
        return ResponseEntity.ok(reservationControlGateway.schedule(table, date, hour, dto));
    }

    @GetMapping(value="/reservationSearch")
    public ResponseEntity<ReservationDto> findByEmail(@Valid @RequestBody ReservationDto dto) throws ValidationsException {
        return ResponseEntity.ok(reservationGateway.findByEmail(dto));
    }
}
