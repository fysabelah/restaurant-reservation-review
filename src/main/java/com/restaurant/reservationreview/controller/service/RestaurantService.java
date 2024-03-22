package com.restaurant.reservationreview.controller.service;

import com.restaurant.reservationreview.model.documents.Restaurant;
import com.restaurant.reservationreview.model.repository.RestaurantRepository;
import com.restaurant.reservationreview.util.converter.RestaurantConverter;
import com.restaurant.reservationreview.util.dto.RestaurantDto;
import com.restaurant.reservationreview.util.exception.NotFoundException;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {

    private static final String SORT = "id";

    @Resource
    private RestaurantRepository restaurantRepository;

    @Resource
    private RestaurantConverter converter;

    public RestaurantDto insert(RestaurantDto restaurantDto) throws ValidationsException {
        Restaurant restaurant = this.converter.convert(restaurantDto);

        Optional<Restaurant> saved = this.findByName(restaurant.getName());

        if (saved.isPresent()){
            throw new ValidationsException("Restaurant com nome " + saved.get().getName() +  " já cadastrado no código " + saved.get().getId());
        }

        restaurant = this.restaurantRepository.save(restaurant);

        return this.converter.convert(restaurant);
    }

    public RestaurantDto update(RestaurantDto restaurantDto) throws NotFoundException {
        Optional<Restaurant> saved = this.restaurantRepository.findById(restaurantDto.getId());

        if (saved.isEmpty()){
            throw new NotFoundException(restaurantDto.getId(), "Restaurant");
        }

        Restaurant restaurant = saved.get();

        restaurant.setName(restaurantDto.getName());
        restaurant.setLocation(restaurantDto.getLocation());
        restaurant.setCuisineType(restaurantDto.getCuisineType());
        restaurant.setActive(restaurantDto.getActive());
        restaurant.setQuantityTables(restaurantDto.getQuantitytables());

        restaurant = this.restaurantRepository.save(restaurant);

        return this.converter.convert(restaurant);
    }

    public RestaurantDto findById(String idRestaurant) throws NotFoundException {

        Optional<Restaurant> restaurant = this.restaurantRepository.findById(idRestaurant);

        if (restaurant.isEmpty()){
            throw  new NotFoundException(idRestaurant,"Restaurant");
        }

        return this.converter.convert(restaurant.get());
    }

    public Optional<Restaurant> findByName(String restaurantName) {

        return this.restaurantRepository.findByNameEquals(restaurantName);

    }

    public PagedResponse<RestaurantDto> findAll(Pagination pagination) {

        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize(), Sort.by(SORT));

        Page<Restaurant> restaurants = this.restaurantRepository.findAll(pageable);

        return this.converter.convertDocuments(restaurants);

    }

}
