package com.restaurant.reservationreview.command.restaurant;

import com.restaurant.reservationreview.dto.RestaurantDto;
import com.restaurant.reservationreview.model.Restaurant;
import com.restaurant.reservationreview.service.RestaurantServiceInterface;
import com.restaurant.reservationreview.util.converter.RestaurantConverter;
import com.restaurant.reservationreview.util.exception.NotFoundException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindRestaurantByIdCommand {

    @Resource
    private RestaurantServiceInterface restaurantService;

    @Resource
    private RestaurantConverter restaurantConverter;

    public RestaurantDto execute(String idRestaurant) throws NotFoundException{

        Optional<Restaurant> restaurant = this.restaurantService.findById(idRestaurant);

        if ( restaurant.isEmpty()){
            throw new NotFoundException(idRestaurant, "Restaurant");
        }

        return this.restaurantConverter.convert(restaurant.get());


    }

}
