package com.restaurant.reservationreview.usercase;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.frameworks.db.RestaurantRepository;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RestaurantBusiness {

    @Resource
    RestaurantRepository repository;

    public Restaurant create( Restaurant restaurant) throws ValidationsException{
        Optional<Restaurant> restaurantSaved = this.repository.findByNameEquals(restaurant.getName());

        if ( restaurantSaved.isPresent() ){
            throw new ValidationsException("0105");
        }

        return  restaurant;
    }



}
