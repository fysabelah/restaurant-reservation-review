package com.restaurant.reservationreview.frameworks.web;

import com.restaurant.reservationreview.interfaceadapters.controllers.ReservationController;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.PersonDto;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/reservation")
@Tag(name = "Reserva", description = "Cria, cancela e procura por reservas")
public class ReservationWeb {

    @Resource
    private ReservationController reservationController;

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
                                                   @Valid @RequestBody PersonDto dto) throws ValidationsException {
        LocalTime parsedHour = LocalTime.parse(hour, DateTimeFormatter.ofPattern("HH:mm"));
        return ResponseEntity.ok(reservationController.schedule(restaurantId, table, date, parsedHour, dto));
    }

    @GetMapping(value="/findReservations")
    public ResponseEntity<PagedResponse<ReservationDto>> findByEmail(
            @RequestParam("email") String email,
            @Parameter(description = "Default value 10. Max value 1000", example = "10") @RequestParam(required = false) Integer pageSize,
            @Parameter(description = "Default value 0", example = "0") @RequestParam(required = false) Integer initialPage) throws ValidationsException {
        Pagination page = new Pagination(initialPage, pageSize);
        return ResponseEntity.ok(reservationController.findByEmail(email, page));
    }

    @DeleteMapping(value="/reservationCancelation")
    public ResponseEntity<ReservationDto> reservationCancelation(@RequestParam("reservationId") String id) throws ValidationsException {
        reservationController.reservationCancelation(id);
        return ResponseEntity.noContent().build();
    }

}