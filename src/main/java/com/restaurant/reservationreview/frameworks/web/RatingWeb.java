package com.restaurant.reservationreview.frameworks.web;

import com.restaurant.reservationreview.interfaceadapters.controllers.RatingController;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RatingDto;
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
public class RatingWeb {

    @Resource
    private RatingController controller;

    @PostMapping(value = "/{restaurant}")
    public ResponseEntity<RatingDto> insert(@Valid @RequestBody RatingDto dto, @PathVariable String restaurant) throws ValidationsException {
        return ResponseEntity.ok(controller.insert(restaurant, dto));
    }

    @GetMapping(value = "/restaurant/{restaurant}")
    public ResponseEntity<PagedResponse<RatingDto>> findAll(@Parameter(description = "Default value 10. Max value 1000", example = "10") @RequestParam(required = false) Integer pageSize,
                                                            @Parameter(description = "Default value 0", example = "0") @RequestParam(required = false) Integer initialPage,
                                                            @PathVariable String restaurant) throws ValidationsException {
        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(controller.findAll(page, restaurant));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RatingDto> findById(@PathVariable String id) throws ValidationsException {
        return ResponseEntity.ok(controller.findById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<RatingDto> delete(@PathVariable String id) throws ValidationsException {
        controller.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RatingDto> update(@PathVariable String id,
                                            @RequestParam(required = false) Integer score,
                                            @RequestBody String comment) throws ValidationsException {
        return ResponseEntity.ok(controller.update(id, score, comment));
    }
}