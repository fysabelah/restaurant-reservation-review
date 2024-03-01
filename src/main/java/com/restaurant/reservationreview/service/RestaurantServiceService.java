package com.restaurant.reservationreview.service;

import com.restaurant.reservationreview.model.Restaurant;
import com.restaurant.reservationreview.repository.RestaurantRepository;
import com.restaurant.reservationreview.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantServiceService implements RestaurantServiceInterface {

    private static final String SORT = "id";

    @Resource
    private RestaurantRepository restaurantRepository;

    @Override
    public Restaurant insert(Restaurant restaurant) {
        return this.restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        return this.restaurantRepository.save(restaurant);
    }

    @Override
    public Optional<Restaurant> findById(String idRestaurant) {
        return this.restaurantRepository.findById(idRestaurant);
    }

    @Override
    public Optional<Restaurant> findByName(String restaurantName) {

        return this.restaurantRepository.findByNameEquals( restaurantName);


    }

    @Override
    public Page<Restaurant> findAll(Pagination pagination) {

        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize(), Sort.by(SORT));

        return restaurantRepository.findAll(pageable);

    }

}
