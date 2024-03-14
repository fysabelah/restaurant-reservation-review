package com.restaurant.reservationreview.controller.service;

import com.restaurant.reservationreview.model.documents.restaurant.Rating;
import com.restaurant.reservationreview.model.repository.RatingRepository;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.converter.RatingConverter;
import com.restaurant.reservationreview.util.dto.restaurant.RatingDto;
import com.restaurant.reservationreview.util.dto.restaurant.RestaurantDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Resource
    private RatingRepository repository;

    @Resource
    private RestaurantService restaurantService;

    @Resource
    private RatingConverter converter;

    public RatingDto insert(String restaurant, RatingDto dto) throws ValidationsException {
        RestaurantDto restaurantDto = restaurantService.findById(restaurant);

        Rating rating = converter.convert(dto, restaurantDto);
        rating.setDate(TimeUtils.now());

        rating = repository.save(rating);

        return converter.convert(rating);
    }

    public PagedResponse<RatingDto> findAll(Pagination page, String restaurant) throws ValidationsException {
        restaurantService.findById(restaurant);

        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize());

        Page<Rating> ratings = repository.findAllByRestaurantId(restaurant, pageable);

        return converter.convertDocuments(ratings);
    }

    public RatingDto findById(String id) throws ValidationsException {
        Rating rating = getRating(id);

        return converter.convert(rating);
    }

    private Rating getRating(String id) throws ValidationsException {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "avalia\u00E7\u00E3o", id));
    }

    public void delete(String id) throws ValidationsException {
        Rating rating = getRating(id);

        repository.delete(rating);
    }

    public RatingDto update(String id, Integer score, String comment) throws ValidationsException {
        Rating rating = getRating(id);

        rating.setComment(comment);

        if (score != null) {
            if (score < 0 || score > 10) {
                throw new ValidationsException("0100");
            }

            rating.setScore(score);
        }

        rating = repository.save(rating);

        return converter.convert(rating);
    }
}
