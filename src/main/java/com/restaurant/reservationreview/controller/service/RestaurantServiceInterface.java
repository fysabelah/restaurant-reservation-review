package com.restaurant.reservationreview.controller.service;

import com.restaurant.reservationreview.model.documents.Restaurant;
import com.restaurant.reservationreview.util.pagination.Pagination;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RestaurantServiceInterface {

    Restaurant insert(Restaurant restaurant);

    Restaurant update(Restaurant restaurant);

    public Optional<Restaurant> findById(String restaurantId);

    public Optional<Restaurant> findByName(String restaurantName);

    Page<Restaurant> findAll(Pagination pagination);
}
