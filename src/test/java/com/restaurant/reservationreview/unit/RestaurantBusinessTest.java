package com.restaurant.reservationreview.unit;

import com.restaurant.reservationreview.TestUtils;
import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.usercase.RestaurantBusiness;
import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantBusinessTest extends TestUtils {

    @Resource
    private RestaurantBusiness business;

    @Test
    void testCreateWithPresentOptional() {
        Optional<Restaurant> optional = Optional.of(new Restaurant());

        ValidationsException exception = assertThrows(ValidationsException.class, () -> business.create(optional));

        assertEquals(MessageUtil.getMessage("0201"), exception.getMessage());
    }

    @Test
    void testCreateWithEmptyOptional() {
        Optional<Restaurant> optional = Optional.empty();

        assertDoesNotThrow(() -> business.create(optional));
    }

    @Test
    void testDisableWithNullList() {
        assertDoesNotThrow(() -> business.disable(null));
    }

    @Test
    void testDisableWithNonNullAndNonEmptyList() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        reservations.add(new Reservation());

        ValidationsException exception = assertThrows(ValidationsException.class, () -> business.disable(reservations));

        assertEquals(MessageUtil.getMessage("0207"), exception.getMessage());
    }

    @Test
    void testUpdateWithMismatchedIds() {
        Restaurant restaurantName = new Restaurant();
        restaurantName.setId("existing_id");

        Restaurant restaurantToUpdate = new Restaurant();
        restaurantToUpdate.setId("id");

        Restaurant restaurantConvert = new Restaurant();
        restaurantToUpdate.setId("id");

        Optional<Restaurant> optional = Optional.of(restaurantName);

        ValidationsException exception = assertThrows(ValidationsException.class, () -> business.update(optional, restaurantToUpdate, restaurantConvert));

        assertEquals(MessageUtil.getMessage("0206"), exception.getMessage());
    }
}