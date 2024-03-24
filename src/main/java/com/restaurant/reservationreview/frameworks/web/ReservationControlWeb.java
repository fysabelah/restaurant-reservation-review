package com.restaurant.reservationreview.frameworks.web;

import com.restaurant.reservationreview.interfaceadapters.controllers.ReservationControlController;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationControlDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/reservationControl")
@Tag(name = "Controle de Reservas", description = "Controle de reservas do Restaurante")
public class ReservationControlWeb {

    @Resource
    private ReservationControlController reservationControlController;

    @GetMapping(value = "/reservationsByPeriod/{restaurant}")
    public ResponseEntity<PagedResponse<ReservationDto>> findAllReservationsByRestaurantAndDateBetween
            (@PathVariable String restaurant,
             @RequestParam("data-inicio") LocalDate start,
             @RequestParam("data-fim") LocalDate finish,
             @Parameter(description = "Default value 10. Max value 1000", example = "10") @RequestParam(required = false) Integer pageSize,
             @Parameter(description = "Default value 0", example = "0") @RequestParam(required = false) Integer initialPage) throws ValidationsException {
        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(reservationControlController.findAllReservationsByRestaurantAndDateBetween(page, restaurant, start, finish));
    }

    @GetMapping(value = "/reservationControlByPeriod/{restaurant}")
    public ResponseEntity<PagedResponse<ReservationControlDto>> findAllReservationControlByRestaurantIdAndDateBetween
            (@PathVariable String restaurant,
             @RequestParam("data-inicio") LocalDate start,
             @RequestParam("data-fim") LocalDate finish,
             @Parameter(description = "Default value 10. Max value 1000", example = "10") @RequestParam(required = false) Integer pageSize,
             @Parameter(description = "Default value 0", example = "0") @RequestParam(required = false) Integer initialPage) throws ValidationsException {
        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(reservationControlController.findAllReservationControlByRestaurantAndDateBetween(page, restaurant, start, finish));
    }

}
