package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.model.documents.restaurant.Rating;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.dto.restaurant.RatingDto;
import com.restaurant.reservationreview.util.dto.restaurant.RestaurantDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class RatingConverter implements Converter<Rating, RatingDto> {

    @Resource
    private RestaurantConverter restaurantConverter;

    @Override
    public RatingDto convert(Rating document) {
        return new RatingDto(document.getId(),
                document.getName(),
                document.getScore(),
                document.getComment(),
                document.getDate().toString());
    }

    @Override
    public Rating convert(RatingDto dto) {
        Rating rating = new Rating();

        rating.setId(dto.getId());
        rating.setName(dto.getName());
        rating.setScore(dto.getScore());
        rating.setComment(dto.getComment());
        rating.setZoneId(TimeUtils.getZoneId());

        if (dto.getDate() != null) {
            rating.setDate(TimeUtils.getDate(dto.getDate()));
        }

        return rating;
    }

    public Rating convert(RatingDto dto, RestaurantDto restaurantDto) {
        Rating rating = convert(dto);

        rating.setRestaurant(restaurantConverter.convert(restaurantDto));

        return rating;
    }
}
