package com.restaurant.reservationreview.unit;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.entities.RestaurantBusinessHours;
import com.restaurant.reservationreview.util.enums.FoodType;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class RestauranteBusinessTest {
    @Test
    void testCreateRestaurant(){
        String IdNew = new Random().toString();
        LocalTime testStartTime = LocalTime.of(17,0,0);
        LocalTime testFinishTime = LocalTime.of(23,59,0);

        Restaurant restaurant = new Restaurant();
        List<RestaurantBusinessHours> listRestaurantBusinessHours = new ArrayList<>();

        RestaurantBusinessHours restaurantBusinessHours = new RestaurantBusinessHours();

        restaurant.setId(IdNew);
        restaurant.setName("Balde do lixo");
        restaurant.setFoodType(FoodType.OTHER);
        restaurant.setLocation("Fenda do Bikini");
        restaurant.setActive(true);
        restaurant.setQuantityTables(50);

        for (DayOfWeek day : DayOfWeek.values()){
            restaurantBusinessHours.setDayOfWeek(day);
            restaurantBusinessHours.setStartTime(testStartTime);
            restaurantBusinessHours.setFinishTime(testFinishTime);

            listRestaurantBusinessHours.add(restaurantBusinessHours);
        }

        restaurant.setBusinessHours(listRestaurantBusinessHours);

        System.out.println(restaurant);
    }

}
