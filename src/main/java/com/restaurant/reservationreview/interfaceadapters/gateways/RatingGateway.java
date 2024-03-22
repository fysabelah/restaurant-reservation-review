package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.Rating;
import com.restaurant.reservationreview.framework.db.RatingRepository;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.interfaceadapters.presenters.RatingPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RatingDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RatingGateway {

    @Resource
    private RatingRepository repository;

    @Resource
    private RestaurantGateway restaurantService;

    @Resource
    private RatingPresenter ratingPresenter;

    public RatingDto insert(String restaurant, RatingDto dto) throws ValidationsException {
        RestaurantDto restaurantDto = restaurantService.findById(restaurant);

        Rating rating = ratingPresenter.convert(dto, restaurantDto);
        rating.setDate(TimeUtils.now());

        rating = repository.save(rating);

        return ratingPresenter.convert(rating);
    }

    public PagedResponse<RatingDto> findAll(Pagination page, String restaurant) throws ValidationsException {
        restaurantService.findById(restaurant);

        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize());

        Page<Rating> ratings = repository.findAllByRestaurantId(restaurant, pageable);

        return ratingPresenter.convertDocuments(ratings);
    }

    public RatingDto findById(String id) throws ValidationsException {
        Rating rating = getRating(id);

        return ratingPresenter.convert(rating);
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

        return ratingPresenter.convert(rating);
    }
}
