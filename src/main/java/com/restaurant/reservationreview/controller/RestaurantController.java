package com.restaurant.reservationreview.controller;

import com.restaurant.reservationreview.command.restaurant.FindAllRestaurantCommand;
import com.restaurant.reservationreview.command.restaurant.FindRestaurantByIdCommand;
import com.restaurant.reservationreview.command.restaurant.InsertRestaurantCommand;
import com.restaurant.reservationreview.command.restaurant.UpdateRestaurantCommand;
import com.restaurant.reservationreview.dto.RestaurantDto;
import com.restaurant.reservationreview.util.exception.NotFoundException;
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
@Tag(name="Restaurant", description = "Exibe os métodos para consultar e incluir informações de um restaurante")

public class RestaurantController {

    @Resource
    private InsertRestaurantCommand insertCommand;

    @Resource
    private UpdateRestaurantCommand updateCommand;

    @Resource
    private FindAllRestaurantCommand findAllCommand;

    @Resource
    private FindRestaurantByIdCommand findRestaurantById;

    @Operation(summary = "Consultar todos os restaurantes")
    @GetMapping
    public ResponseEntity<PagedResponse<RestaurantDto>> findAll(@Parameter(description = "Default value 10. Max value 1000", example = "10") @RequestParam(required = false) Integer pageSize,
                                                                @Parameter(description = "Default value 0", example = "0") @RequestParam(required = false) Integer initialPage) {
        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(this.findAllCommand.execute(page));
    }

    @Operation(summary = "Consultar um restaurante por código")
    @GetMapping("/find")
    public ResponseEntity<RestaurantDto> findById(@RequestParam String idRestaurant) throws NotFoundException {
        return ResponseEntity.ok(this.findRestaurantById.execute(idRestaurant));
    }

    @Operation(summary="Incluir um restaurante")
    @PostMapping
    public ResponseEntity<RestaurantDto>  insert(@RequestBody RestaurantDto restaurantDto) throws ValidationsException {
        RestaurantDto restaurantDtoSaved = this.insertCommand.execute(restaurantDto);
        return ResponseEntity.ok(restaurantDtoSaved);
    }

    @Operation(summary = "Atualizar informações de um restaurante")
    @PutMapping
    public ResponseEntity<RestaurantDto> update(@Valid @RequestBody RestaurantDto restaurantDto) throws NotFoundException {
        RestaurantDto restaurantDtoUpdated = this.updateCommand.execute(restaurantDto);

        return ResponseEntity.ok(restaurantDtoUpdated);
    }

}
