package com.restaurant.reservationreview.usercase;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import org.springframework.stereotype.Component;

@Component
public class RestaurantBusiness {

    public void create(Restaurant restaurantNomeDuplicado) throws ValidationsException {
        if (restaurantNomeDuplicado != null) {
            throw new ValidationsException("0200");
        }
    }

    public void update(Restaurant restaurant, Restaurant converted, Restaurant restaurantWithTheSameName) throws ValidationsException {
        if (restaurantWithTheSameName != null && restaurant.getId().compareTo(restaurantWithTheSameName.getId()) != 0) {
            throw new ValidationsException("0200");
        }

        // atribuímos no restaurante atual as novas informações
        restaurant.setName(converted.getName());
        restaurant.setAdress(converted.getAdress());
        restaurant.setFoodType(converted.getFoodType());
        restaurant.setActive(converted.isActive());
        restaurant.setCapacity(converted.getCapacity());
        restaurant.setBusinessHours(converted.getBusinessHours());
    }
}