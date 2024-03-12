package com.restaurant.reservationreview.command.restaurant;

import com.restaurant.reservationreview.util.dto.RestaurantDto;
import com.restaurant.reservationreview.model.documents.Restaurant;
import com.restaurant.reservationreview.controller.service.RestaurantServiceInterface;
import com.restaurant.reservationreview.util.converter.RestaurantConverter;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class FindAllRestaurantCommand {
    @Resource
    private RestaurantServiceInterface restaurantService;

    @Resource
    private RestaurantConverter restaurantConverter;

    public PagedResponse<RestaurantDto> execute(Pagination page) {
        Page<Restaurant> result = this.restaurantService.findAll(page);

        return this.restaurantConverter.convertEntities(result);
    }
}
