package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.Rating;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RatingDto;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class RatingPresenter implements Presenter<Rating, RatingDto> {

    @Resource
    private RestaurantPresenter restaurantConverter;

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
}