package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.frameworks.db.RestaurantRepository;
import com.restaurant.reservationreview.util.enums.FoodType;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantGateway {

    @Resource
    private RestaurantRepository repository;

    public Restaurant insert(Restaurant restaurant) {
        return this.repository.insert(restaurant);
    }

    public Page<Restaurant> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    public Page<Restaurant> findAll(FoodType foodType, String location, Pageable pageable) {
        return this.repository.findAllByFoodTypeAndAdressCityLike(foodType, location, pageable);
    }

    public Page<Restaurant> findAll(FoodType foodType, Pageable pageable) {
        return this.repository.findAllByFoodType(foodType, pageable);
    }

    public Page<Restaurant> findAll(String location, Pageable pageable) {
        return this.repository.findAllByAdressCityLike(location, pageable);
    }

    public Restaurant findById(String id) throws ValidationsException {
        return this.repository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "Restaurant", id));
    }

    public Restaurant findByName(String nameRestaurant) {
        return this.repository.findByNameEquals(nameRestaurant);
    }

    public Restaurant update(Restaurant restaurant) {
        return this.repository.save(restaurant);
    }
}