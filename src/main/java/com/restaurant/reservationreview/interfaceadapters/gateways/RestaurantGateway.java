package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.frameworks.db.RestaurantRepository;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RestaurantGateway {

    @Resource
    private RestaurantRepository repository;

    public Restaurant insert(Restaurant restaurant) {
        return repository.insert(restaurant);
    }

    public Restaurant findById(String id) throws ValidationsException {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "restaurante", id));
    }

    public void delete(Restaurant restaurant) {
        repository.delete(restaurant);
    }

    public Restaurant update(Restaurant restaurant) {
        return repository.save(restaurant);
    }
}
