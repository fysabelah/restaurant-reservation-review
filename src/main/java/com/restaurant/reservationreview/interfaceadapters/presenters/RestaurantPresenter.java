package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RestaurantPresenter implements Presenter<Restaurant, RestaurantDto> {

    @Resource
    private BusinessHoursPresenter businessHoursPresenter;

    @Resource
    private AddressPresenter addressPresenter;

    @Override
    public RestaurantDto convert(Restaurant document) {
        RestaurantDto restaurantDto = new RestaurantDto();

        restaurantDto.setId(document.getId());
        restaurantDto.setName(document.getName());
        restaurantDto.setAddressDto(addressPresenter.convert(document.getAddress()));
        restaurantDto.setBusinnessHoursDto(businessHoursPresenter.convertDocuments(document.getBusinnessHours()));
        restaurantDto.setCapacity(document.getCapacity());
        restaurantDto.setFoodType(document.getFoodType());
        restaurantDto.setHourToConsiderReservation(document.getHourToConsiderReservation().toString());
        restaurantDto.setQuantityTablesAvailableReservation(document.getQuantityTablesAvailableReservation());
        restaurantDto.setActive(document.isActive());

        return restaurantDto;
    }

    @Override
    public Restaurant convert(RestaurantDto dto) throws ValidationsException {
        Restaurant restaurant = new Restaurant();

        restaurant.setId(dto.getId());
        restaurant.setAddress(addressPresenter.convert(dto.getAddressDto()));
        restaurant.setHourToConsiderReservation(TimeUtils.getTime(dto.getHourToConsiderReservation()));
        restaurant.setQuantityTablesAvailableReservation(dto.getQuantityTablesAvailableReservation());
        restaurant.setBusinnessHours(businessHoursPresenter.convert(dto.getBusinnessHoursDto()));
        restaurant.setName(dto.getName());
        restaurant.setFoodType(dto.getFoodType());
        restaurant.setCapacity(dto.getCapacity());
        restaurant.setActive(dto.isActive());

        return restaurant;
    }

    public RestaurantDto convert(Restaurant restaurant, BigDecimal rating) {
        RestaurantDto dto = convert(restaurant);
        dto.setRating(rating.setScale(2, RoundingMode.HALF_EVEN));

        return dto;
    }

    public PagedResponse<RestaurantDto> convertDocuments(Page<Restaurant> page, Map<String, BigDecimal> ratings) {
        PagedResponse<RestaurantDto> paged = new PagedResponse<>();

        paged.setPage(new Pagination(page.getNumber(), page.getSize(), page.getTotalPages()));

        List<Restaurant> data = page.get().toList();

        List<RestaurantDto> dtos = new ArrayList<>();

        data.forEach(restaurant -> {
            RestaurantDto dto = convert(restaurant, ratings.get(restaurant.getId()));

            dtos.add(dto);
        });

        paged.setData(dtos);

        return paged;
    }
}
