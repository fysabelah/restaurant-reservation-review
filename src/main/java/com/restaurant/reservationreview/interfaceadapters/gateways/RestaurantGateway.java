package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.framework.db.RestaurantRepository;
import com.restaurant.reservationreview.interfaceadapters.presenters.RestaurantPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RestaurantGateway {

    @Resource
    private RestaurantRepository repository;

    @Resource
    private RestaurantPresenter restaurantPresenter;

    public RestaurantDto findById(String id) throws ValidationsException {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "restaurante", id));

        return restaurantPresenter.convert(restaurant);
    }
}
