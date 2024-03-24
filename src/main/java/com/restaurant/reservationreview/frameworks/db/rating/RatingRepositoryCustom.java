package com.restaurant.reservationreview.frameworks.db.rating;

import java.math.BigDecimal;
import java.util.Map;

public interface RatingRepositoryCustom {

    Map<String, BigDecimal> calculateAvgScoreByRestaurant();
}
