package com.restaurant.reservationreview.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.reservationreview.TestUtils;
import com.restaurant.reservationreview.interfaceadapters.gateways.RatingGateway;
import com.restaurant.reservationreview.entities.*;
import com.restaurant.reservationreview.framework.db.RatingRepository;
import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.enums.FoodType;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


class RatingGatewayTest extends TestUtils {

    @Resource
    private ObjectMapper objectMapper;

    @MockBean
    private RatingRepository ratingRepository;

    @Resource
    private RatingGateway service;

    private static final String RESTAURANT_ID = "NEW_RESTAURANT_ID";
    private static final String RESTAURANT_NAME = "NEW_RESTAURANT";
    private static final String RESTAURANT_ADRESS_STREET = "AV 5";
    private static final String RESTAURANT_ADRESS_NUMBER = "5";
    private static final String RESTAURANT_ADRESS_CITY = "SAO PAULO";
    private static final String RESTAURANT_ADRESS_STATE = "SP";
    private static final LocalTime RESTAURANT_BUSINESS_HOURS_START = LocalTime.parse("19:00");
    private static final LocalTime RESTAURANT_BUSINESS_HOURS_FINISH = LocalTime.parse("23:00");
    private static final DayOfWeek RESTAURANT_BUSINESS_HOURS_DAYOFWEEK = DayOfWeek.valueOf("MONDAY");
    private static final Integer TABLE_AMOUNT = 10;
    private static final Integer TABLE_AMOUNT_AVAILABLE = 8;

    @Test
    void shouldThrowOnFindById() {
        Mockito.when(ratingRepository.findById(any(String.class)))
                .thenReturn(Optional.empty());

        assertThrows(ValidationsException.class, () -> service.findById(new Random().toString()));
    }

    @Test
    void shouldThrowOnUpdateScore() {
        Mockito.when(ratingRepository.findById(any(String.class)))
                .thenReturn(Optional.of(new Rating()));

        try {
            service.update(new Random().toString(), 39, null);
        } catch (ValidationsException e) {
            assertEquals(e.getMessage(), MessageUtil.getMessage("0100"));
        }
    }

    @Test
    void shouldUpdateOnNullScore() {
        Rating rating = new Rating();
        rating.setId("id");
        rating.setScore(5);
        rating.setComment("novo comentário");
        rating.setName("José");
        rating.setDate(LocalDateTime.now());
        rating.setRestaurant(newRestaurant());
        rating.setZoneId(TimeUtils.getZoneId());

        Mockito.when(ratingRepository.findById(any(String.class)))
                .thenReturn(Optional.of(new Rating()));

        Mockito.when(ratingRepository.save(any(Rating.class)))
                .thenReturn(rating);

        assertDoesNotThrow(() -> service.update("id", null, "novo comentário"));
    }

    public Restaurant newRestaurant(){

        Restaurant restaurant = new Restaurant();

        restaurant.setId(RESTAURANT_ID);
        restaurant.setName(RESTAURANT_NAME);
        restaurant.setAdress(adress());
        restaurant.setBusinnessHours(businessHours());
        restaurant.setFoodType(FoodType.BRAZILIAN);
        restaurant.setCapacity(TABLE_AMOUNT);

        return restaurant;
    }

    public List<BusinnessHours> businessHours(){

        List<BusinnessHours> BusinessHoursList = new ArrayList<>();

        BusinnessHours businnessHours = new BusinnessHours();

        businnessHours.setStart(RESTAURANT_BUSINESS_HOURS_START);
        businnessHours.setFinish(RESTAURANT_BUSINESS_HOURS_FINISH);
        businnessHours.setDayOfWeek(RESTAURANT_BUSINESS_HOURS_DAYOFWEEK);
        businnessHours.setReservationHours(reservationHours());
        businnessHours.setAvailable(true);

        BusinessHoursList.add(businnessHours);

        return BusinessHoursList;
    }

    public List<ReservationHours> reservationHours(){

        List<ReservationHours> reservationHoursList = new ArrayList<>();

        ReservationHours reservationHours= new ReservationHours();

        reservationHours.setHour(RESTAURANT_BUSINESS_HOURS_START);
        reservationHours.setTableAmountAvailable(TABLE_AMOUNT_AVAILABLE);

        reservationHoursList.add(reservationHours);

        return reservationHoursList;
    }

    public Adress adress(){

        Adress adress = new Adress();

        adress.setStreet(RESTAURANT_ADRESS_STREET);
        adress.setAdressNumber(RESTAURANT_ADRESS_NUMBER);
        adress.setCity(RESTAURANT_ADRESS_CITY);
        adress.setState(RESTAURANT_ADRESS_STATE);

        return adress;
    }
}