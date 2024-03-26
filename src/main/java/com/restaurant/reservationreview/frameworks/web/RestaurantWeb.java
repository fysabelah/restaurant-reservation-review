package com.restaurant.reservationreview.frameworks.web;

import com.restaurant.reservationreview.interfaceadapters.controllers.RestaurantController;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
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
@RequestMapping(value = "/restaurant")
@Tag(name = "Restaurant", description = "Exibe os métodos para consultar e incluir informações de um restaurante")
public class RestaurantWeb {

    @Resource
    private RestaurantController restaurantController;

    @Operation(summary = "Consultar todos os restaurantes")
    @GetMapping
    public ResponseEntity<PagedResponse<RestaurantDto>> findAll(@Parameter(description = "Default value 10. Max value 1000", example = "10") @RequestParam(required = false) Integer pageSize,
                                                                @Parameter(description = "Default value 0", example = "0") @RequestParam(required = false) Integer initialPage) {

        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(this.restaurantController.findAll(page));
    }

    @Operation(summary = "Consultar um restaurante por código")
    @GetMapping(value = "/{restaurant}")
    public ResponseEntity<RestaurantDto> findById(@Parameter(description = "Informe o ID do restaurante", example = "65fda06d0f8a6b46cd1b2eba")
                                                  @RequestParam(required = true) String idRestaurant) throws ValidationsException {

        return ResponseEntity.ok(this.restaurantController.findById(idRestaurant));

    }

    @Operation(summary = "Incluir informações de um restaurante")
    @PostMapping
    public ResponseEntity<RestaurantDto> insert(@RequestBody RestaurantDto restaurantDto) throws ValidationsException {

        RestaurantDto restaurantDtoSaved = this.restaurantController.insert(restaurantDto);
        return ResponseEntity.ok(restaurantDtoSaved);

    }

    @Operation(summary = "Atualizar informações de um restaurante")
    @PutMapping
    public ResponseEntity<RestaurantDto> update(@Valid @RequestBody RestaurantDto restaurantDto) throws ValidationsException {

        RestaurantDto restaurantDtoUpdated = this.restaurantController.update(restaurantDto);

        return ResponseEntity.ok(restaurantDtoUpdated);

    }

}
