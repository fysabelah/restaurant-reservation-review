package com.restaurant.reservationreview.frameworks.web;

import com.restaurant.reservationreview.interfaceadapters.controllers.ReservationController;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.util.enums.StatusReservation;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/reservation")
@Tag(name = "Reservas", description = "Metódos reserva")
public class ReservationWeb {

    @Resource
    private ReservationController controller;

    @PostMapping
    public ResponseEntity<ReservationDto> insert(@Valid @RequestBody ReservationDto dto) throws ValidationsException {
        return ResponseEntity.ok(controller.insert(dto));
    }

    @GetMapping(value = "/restaurant/{restaurant}/person/{person}")
    @Operation(summary = "Buscar todas reservas e uma pessoa (nome) e restaurante (id)")
    public ResponseEntity<PagedResponse<ReservationDto>> findAll(@Parameter(description = "Default value 10. Max value 1000", example = "10") @RequestParam(required = false) Integer pageSize,
                                                                 @Parameter(description = "Default value 0", example = "0") @RequestParam(required = false) Integer initialPage,
                                                                 @PathVariable String restaurant,
                                                                 @PathVariable String person) {
        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(controller.findAll(page, restaurant, person));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDto> findById(@PathVariable String id) throws ValidationsException {
        return ResponseEntity.ok(controller.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ReservationDto> updateStatus(@PathVariable String id, @RequestParam StatusReservation status) throws ValidationsException {
        return ResponseEntity.ok(controller.updateStatus(id, status));
    }
}
