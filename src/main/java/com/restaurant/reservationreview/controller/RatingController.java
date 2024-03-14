package com.restaurant.reservationreview.controller;

import com.restaurant.reservationreview.controller.service.RatingService;
import com.restaurant.reservationreview.util.dto.restaurant.RatingDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rating")
@Tag(name = "Avaliação", description = "Metódos avaliação")
public class RatingController {

    @Resource
    private RatingService service;

    @PostMapping(value = "/{restaurant}")
    public ResponseEntity<RatingDto> insert(@Valid @RequestBody RatingDto dto, @PathVariable String restaurant) throws ValidationsException {
        return ResponseEntity.ok(service.insert(restaurant, dto));
    }

    @GetMapping(value = "/restaurant/{restaurant}")
    public ResponseEntity<PagedResponse<RatingDto>> findAll(@Parameter(description = "Default value 10. Max value 1000", example = "10") @RequestParam(required = false) Integer pageSize,
                                                            @Parameter(description = "Default value 0", example = "0") @RequestParam(required = false) Integer initialPage,
                                                            @PathVariable String restaurant) throws ValidationsException {
        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(service.findAll(page, restaurant));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RatingDto> findById(@PathVariable String id) throws ValidationsException {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<RatingDto> delete(@PathVariable String id) throws ValidationsException {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RatingDto> update(@PathVariable String id,
                                            @RequestParam(required = false) Integer score,
                                            @RequestBody String comment) throws ValidationsException {
        return ResponseEntity.ok(service.update(id, score, comment));
    }
}
