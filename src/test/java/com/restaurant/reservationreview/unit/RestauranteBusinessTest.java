package com.restaurant.reservationreview.unit;

import com.restaurant.reservationreview.TestUtils;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.usercase.RestaurantBusiness;
import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.enums.FoodType;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteBusinessTest extends TestUtils {

    @Resource
    private RestaurantBusiness business;

    @Test
    void testValidateRestaurantExists() {
        ValidationsException exception = assertThrows(ValidationsException.class,
                () -> business.create(new Restaurant()));

        assertEquals(MessageUtil.getMessage("0200"), exception.getMessage());
    }

    @Test
    void testValidateRestaurantNotExists() {
        assertDoesNotThrow(() -> business.create(null));
    }

    @Test
    void testUpdateWithSameNameDiferentsId() {
        // Create instances of Restaurant
        Restaurant restaurant = new Restaurant();
        restaurant.setId("id");
        restaurant.setName("Nome antigo");

        Restaurant converted = new Restaurant();
        converted.setId("id");
        converted.setName("Novo nome");

        Restaurant restaurantWithTheSameName = new Restaurant();
        restaurantWithTheSameName.setId("id_1");
        restaurantWithTheSameName.setName("Novo nome");

        ValidationsException exception = assertThrows(ValidationsException.class,
                () -> business.update(restaurant, converted, restaurantWithTheSameName));

        assertEquals(MessageUtil.getMessage("0200"), exception.getMessage());
    }

    @Test
    void testUpdateWithNameNotSaveOnDatabase() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("id");
        restaurant.setName("Nome antigo");
        restaurant.setLocation("Aqui");
        restaurant.setRating(new BigDecimal("8.9"));
        restaurant.setActive(true);
        restaurant.setFoodType(FoodType.BRAZILIAN);
        restaurant.setQuantityTables(10);
        restaurant.setBusinessHours(new ArrayList<>());

        Restaurant converted = new Restaurant();
        converted.setId("id");
        converted.setName("Nome novo");
        converted.setLocation("Aqui");
        converted.setRating(new BigDecimal("10"));
        converted.setActive(false);
        converted.setFoodType(FoodType.FRENCH);
        converted.setQuantityTables(70);
        converted.setBusinessHours(new ArrayList<>());

        assertDoesNotThrow(() -> business.update(restaurant, converted, null));

        assertEquals(converted.getName(), restaurant.getName());
        assertEquals(new BigDecimal("8.9"), restaurant.getRating());
        assertEquals(converted.isActive(), restaurant.isActive());
        assertEquals(FoodType.FRENCH, restaurant.getFoodType());
        assertEquals(converted.getQuantityTables(), restaurant.getQuantityTables());
    }

    @Test
    void testUpdateWithSameNameAndSameId() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("id");
        restaurant.setName("Nome novo");
        restaurant.setLocation("Aqui");
        restaurant.setRating(new BigDecimal("8.9"));
        restaurant.setActive(true);
        restaurant.setFoodType(FoodType.BRAZILIAN);
        restaurant.setQuantityTables(10);
        restaurant.setBusinessHours(new ArrayList<>());

        Restaurant converted = new Restaurant();
        converted.setId("id");
        converted.setName("Nome novo");
        converted.setLocation("Aqui");
        converted.setRating(new BigDecimal("10"));
        converted.setActive(false);
        converted.setFoodType(FoodType.FRENCH);
        converted.setQuantityTables(70);
        converted.setBusinessHours(new ArrayList<>());

        Restaurant restaurantWithTheSameName = new Restaurant();
        restaurantWithTheSameName.setId("id");
        restaurantWithTheSameName.setName("Nome novo");

        assertDoesNotThrow(() -> business.update(restaurant, converted, null));

        assertEquals(new BigDecimal("8.9"), restaurant.getRating());
        assertEquals(converted.isActive(), restaurant.isActive());
        assertEquals(FoodType.FRENCH, restaurant.getFoodType());
        assertEquals(converted.getQuantityTables(), restaurant.getQuantityTables());
    }

}
