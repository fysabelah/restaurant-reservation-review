package com.restaurant.reservationreview.controller.service;

import com.restaurant.reservationreview.model.documents.restaurant.Restaurant;
import com.restaurant.reservationreview.model.repository.RestaurantRepository;
import com.restaurant.reservationreview.util.converter.RestaurantConverter;
import com.restaurant.reservationreview.util.dto.restaurant.RestaurantDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Resource
    private RestaurantRepository repository;

    @Resource
    private RestaurantConverter converter;

    public RestaurantDto findById(String id) throws ValidationsException {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "restaurante", id));

        return converter.convert(restaurant);
    }
}
