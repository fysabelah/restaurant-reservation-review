package com.restaurant.reservationreview.usercase;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RestaurantBusiness {

    public Restaurant create( Restaurant restaurant, Restaurant restaurantNomeDuplicado) throws ValidationsException{

        if (restaurantNomeDuplicado != null || ! restaurantNomeDuplicado.getId().isEmpty() ) {
            throw new ValidationsException("0200");
        }

        return restaurant;
    }

    public Restaurant update( Optional<Restaurant> restaurant ) throws  ValidationsException{
        if (! restaurant.isPresent()){
            throw new ValidationsException("0001");
        }

        return restaurant.get();
    }


}
