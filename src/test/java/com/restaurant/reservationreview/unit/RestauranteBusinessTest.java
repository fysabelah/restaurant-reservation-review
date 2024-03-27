package com.restaurant.reservationreview.unit;

import com.restaurant.reservationreview.util.constants.Constants;
import com.restaurant.reservationreview.utils.TestUtils;
import com.restaurant.reservationreview.entities.Adress;
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
        restaurant.setAdress(adress());
        restaurant.setAverageRating(new BigDecimal("8.9"));
        restaurant.setActive(true);
        restaurant.setFoodType(FoodType.ITALIAN);
        restaurant.setCapacity(10);
        restaurant.setBusinessHours(new ArrayList<>());

        Restaurant converted = new Restaurant();
        converted.setId("id");
        converted.setName("Nome novo");
        converted.setLocation(adress());
        converted.setAverageRating(new BigDecimal("10"));
        converted.setActive(false);
        converted.setFoodType(FoodType.BRAZILIAN);
        converted.setCapacity(70);
        converted.setBusinessHours(new ArrayList<>());

        assertDoesNotThrow(() -> business.update(restaurant, converted, null));

        assertEquals(converted.getName(), restaurant.getName());
        assertEquals(new BigDecimal("8.9"), restaurant.getAverageRating());
        assertEquals(converted.isActive(), restaurant.isActive());
        assertEquals(FoodType.BRAZILIAN, restaurant.getFoodType());
        assertEquals(converted.getCapacity(), restaurant.getCapacity());
    }

    @Test
    void testUpdateWithSameNameAndSameId() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("id");
        restaurant.setName("Nome novo");
        restaurant.setLocation(adress());
        restaurant.setAverageRating(new BigDecimal("8.9"));
        restaurant.setActive(true);
        restaurant.setFoodType(FoodType.BRAZILIAN);
        restaurant.setCapacity(10);
        restaurant.setBusinessHours(new ArrayList<>());

        Restaurant converted = new Restaurant();
        converted.setId("id");
        converted.setName("Nome novo");
        converted.setLocation(adress());
        converted.setAverageRating(new BigDecimal("10"));
        converted.setActive(false);
        converted.setFoodType(FoodType.ITALIAN);
        converted.setCapacity(70);
        converted.setBusinessHours(new ArrayList<>());

        Restaurant restaurantWithTheSameName = new Restaurant();
        restaurantWithTheSameName.setId("id");
        restaurantWithTheSameName.setName("Nome novo");

        assertDoesNotThrow(() -> business.update(restaurant, converted, null));

        assertEquals(new BigDecimal("8.9"), restaurant.getAverageRating());
        assertEquals(converted.isActive(), restaurant.isActive());
        assertEquals(FoodType.ITALIAN, restaurant.getFoodType());
        assertEquals(converted.getCapacity(), restaurant.getCapacity());
    }


    private Adress adress(){

        Adress adress = new Adress();

        adress.setStreet(Constants.RESTAURANT_ADRESS_STREET);
        adress.setAdressNumber(Constants.RESTAURANT_ADRESS_NUMBER);
        adress.setCity(Constants.RESTAURANT_ADRESS_CITY);
        adress.setState(Constants.RESTAURANT_ADRESS_STATE);

        return adress;
    }

}
