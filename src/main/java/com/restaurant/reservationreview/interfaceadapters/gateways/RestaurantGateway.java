package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.frameworks.db.RestaurantRepository;
import com.restaurant.reservationreview.usercase.RestaurantBusiness;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantGateway {

    @Resource
    private RestaurantRepository repository;

    @Resource
    private RestaurantBusiness business;

    public Restaurant insert(Restaurant restaurant) throws ValidationsException {
        restaurant = this.business.create(restaurant);

        return repository.insert(restaurant);
    }


    public Page<Restaurant> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }


    public Restaurant findById(String id) throws ValidationsException {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "restaurante", id));
    }


    public Restaurant findByName(String name) throws ValidationsException{
        return repository.findByNameEquals(name)
                .orElseThrow(() -> new ValidationsException("0001", "restaurante", name));
    }

    public void delete(Restaurant restaurant) {
        repository.delete(restaurant);
    }

    public Restaurant update(Restaurant restaurant) {
        return repository.save(restaurant);
    }
}