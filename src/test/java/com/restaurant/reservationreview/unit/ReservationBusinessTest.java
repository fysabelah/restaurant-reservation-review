package com.restaurant.reservationreview.unit;

import com.restaurant.reservationreview.TestUtils;
import com.restaurant.reservationreview.entities.*;
import com.restaurant.reservationreview.interfaceadapters.presenters.PersonPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.PersonDto;
import com.restaurant.reservationreview.usercase.ReservationBusiness;
import com.restaurant.reservationreview.util.constants.Constants;
import com.restaurant.reservationreview.util.enums.FoodType;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationBusinessTest extends TestUtils {

    @Resource
    private ReservationBusiness reservationBusiness;

    @Resource
    private PersonPresenter personPresenter;

    private static final String RESTAURANT_ID = "NEW_RESTAURANT_ID";
    private static final String RESTAURANT_NAME = "NEW_RESTAURANT";
    private static final String RESTAURANT_ADRESS_STREET = "AV 5";
    private static final String RESTAURANT_ADRESS_NUMBER = "5";
    private static final String RESTAURANT_ADRESS_CITY = "SAO PAULO";
    private static final String RESTAURANT_ADRESS_STATE = "SP";
    private static final LocalTime RESTAURANT_BUSINESS_HOURS_START = LocalTime.parse("19:00");
    private static final LocalTime RESTAURANT_BUSINESS_HOURS_FINISH = LocalTime.parse("23:00");
    private static final DayOfWeek RESTAURANT_BUSINESS_HOURS_DAYOFWEEK = DayOfWeek.valueOf("FRIDAY");
    private static final Integer TABLE_AMOUNT = 10;
    private static final Integer TABLE_AMOUNT_AVAILABLE = 8;
    private Constants constants;

    @Test
    public void shouldReturnAvailableDates() {

        ReservationBusiness reservationBusiness = new ReservationBusiness();
        Restaurant restaurant = newRestaurant();
        List<ReservationControl> reservations;
        reservations = reservationControlList(restaurant);
        Integer table = 2;

        List<LocalDate> result = reservationBusiness.checkDateAvailability(restaurant, reservations, table);

        assertNotNull(result);

    }

    @Test
    public void shouldReturnNoAvailableDates() {

        ReservationBusiness reservationBusiness = new ReservationBusiness();
        Restaurant restaurant = newRestaurant();
        List<ReservationControl> reservations;
        reservations = reservationControlUnavailable(restaurant);
        Integer table = 50;

        List<LocalDate> result = reservationBusiness.checkDateAvailability(restaurant, reservations, table);

        Assertions.assertTrue(result.isEmpty());

    }

    @Test
    public void shouldReturnNextDays() {

        ReservationBusiness reservationBusiness = new ReservationBusiness();
        Restaurant restaurant = newRestaurant();

        List<LocalDate> result = reservationBusiness.nextDaysList(restaurant);

        assertNotNull(result);

    }

    @Test
    public void shouldReturnAvailableHours() {

        ReservationBusiness reservationBusiness = new ReservationBusiness();
        Restaurant restaurant = newRestaurant();
        List<ReservationControl> reservations;
        reservations = reservationControlList(restaurant);
        Integer table = 1;

        List<LocalTime> availableHours = reservationBusiness.checkAvailableHours(restaurant, reservations, RESTAURANT_BUSINESS_HOURS_DAYOFWEEK, table);

        assertNotNull(availableHours);

    }

    @Test
    public void shouldReturnNoAvailableHours() {

        ReservationBusiness reservationBusiness = new ReservationBusiness();
        Restaurant restaurant = newRestaurant();
        List<ReservationControl> reservations;
        reservations = reservationControlUnavailable(restaurant);
        Integer table = 9;

        List<LocalTime> availableHours = reservationBusiness.checkAvailableHours(restaurant, reservations, RESTAURANT_BUSINESS_HOURS_DAYOFWEEK, table);

        Assertions.assertTrue(availableHours.isEmpty());

    }

    @Test
    public void shouldReturnAvailableHoursByDayOfWeek() {

        ReservationBusiness reservationBusiness = new ReservationBusiness();
        Restaurant restaurant = newRestaurant();

        List<LocalTime> availableHours = reservationBusiness.checkAvailableHoursByDayOfWeek(restaurant, RESTAURANT_BUSINESS_HOURS_DAYOFWEEK);

        assertNotNull(availableHours);

    }

    @Test
    public void testNewReservationControl() {

        ReservationBusiness reservationBusiness = new ReservationBusiness();
        Restaurant restaurant = newRestaurant();
        int hours = RESTAURANT_BUSINESS_HOURS_START.getHour();
        int minutes = RESTAURANT_BUSINESS_HOURS_START.getMinute();
        LocalDateTime dateAndHour = LocalDate.now().atStartOfDay().plusHours(hours).plusMinutes(minutes);
        Integer table = 1;

        ReservationControl result = reservationBusiness.newReservationControl(restaurant, dateAndHour, RESTAURANT_BUSINESS_HOURS_START, RESTAURANT_BUSINESS_HOURS_DAYOFWEEK, table);

        assertEquals(restaurant, result.getRestaurant());
        assertEquals(dateAndHour, result.getDateAndTime());
        assertEquals(RESTAURANT_BUSINESS_HOURS_DAYOFWEEK, result.getDayOfWeek());
        assertEquals(table, result.getTotalReservations());
        assertNotNull(result.getCapacity());
        assertTrue(result.isAvailable());

    }

    @Test
    public void testNewReservationControlNullRestaurant() {

        ReservationBusiness reservationBusiness = new ReservationBusiness();
        Restaurant restaurant = null;
        int hours = RESTAURANT_BUSINESS_HOURS_START.getHour();
        int minutes = RESTAURANT_BUSINESS_HOURS_START.getMinute();
        LocalDateTime dateAndHour = LocalDate.now().atStartOfDay().plusHours(hours).plusMinutes(minutes);
        Integer table = 1;

        assertThrows(NullPointerException.class, () -> {
            reservationBusiness.newReservationControl(restaurant, dateAndHour, RESTAURANT_BUSINESS_HOURS_START, RESTAURANT_BUSINESS_HOURS_DAYOFWEEK, table);
        });

    }

    @Test
    public void testReservationAvailability() {

        ReservationControl reservationControl = new ReservationControl();
        reservationControl.setAvailable(true);

        try {
            ReservationBusiness.checkReservationAvailability(reservationControl);
        } catch (ValidationsException e) {
            fail("Expected no exception to be thrown");
        }

    }

    @Test
    public void testReservationControlByNewReservation() {

        Restaurant restaurant = newRestaurant();
        ReservationControl reservation;
        reservation = reservationControl(restaurant);

        Integer table = 1;

        ReservationBusiness reservationBusiness = new ReservationBusiness();
        ReservationControl updatedReservationControl = reservationBusiness.updateReservationControlByNewReservation(reservation, table);

        assertEquals(9, updatedReservationControl.getTotalReservations());

    }

    @Test
    public void testReservationControlByCancelation() {

        ReservationBusiness reservationBusiness = new ReservationBusiness();
        Restaurant restaurant = newRestaurant();
        ReservationControl reservation;
        reservation = reservationControl(restaurant);

        Integer table = 1;

        ReservationControl updatedReservationControl = reservationBusiness.updateReservationControlByReservationCancelation(reservation, table);

        assertEquals(7, updatedReservationControl.getTotalReservations());

    }

    @Test
    public void testnewReservation() {

        PersonPresenter personPresenter = new PersonPresenter();
        ReservationBusiness reservationBusiness = new ReservationBusiness(personPresenter);
        Restaurant restaurant = newRestaurant();
        Integer table = 2;
        int hours = RESTAURANT_BUSINESS_HOURS_START.getHour();
        int minutes = RESTAURANT_BUSINESS_HOURS_START.getMinute();
        LocalDateTime dateAndHour = LocalDate.now().atStartOfDay().plusHours(hours).plusMinutes(minutes);
        DayOfWeek weekDayEnum = RESTAURANT_BUSINESS_HOURS_DAYOFWEEK;
        PersonDto dto = new PersonDto();

        dto.setName("George");
        dto.setEmail("george@mail.com.br");
        dto.setPhone("11980809090");

        Reservation result = reservationBusiness.newReservation(restaurant, table, dateAndHour, weekDayEnum, dto);

        assertNotNull(result);
        assertEquals(restaurant, result.getRestaurant());
        assertEquals(dto.getName(), personPresenter.convert(result.getPerson()).getName());
        assertEquals(dto.getEmail(), personPresenter.convert(result.getPerson()).getEmail());
        assertEquals(dto.getPhone(), personPresenter.convert(result.getPerson()).getPhone());
        assertEquals(dateAndHour, result.getDateAndTime());
        assertEquals(weekDayEnum, result.getDayOfWeek());
        assertEquals(table, result.getReservationAmount());

    }

    private Restaurant newRestaurant(){

        Restaurant restaurant = new Restaurant();

        restaurant.setId(RESTAURANT_ID);
        restaurant.setName(RESTAURANT_NAME);
        restaurant.setAdress(adress());
        restaurant.setBusinessHours(businessHours());
        restaurant.setFoodType(FoodType.BRAZILIAN);
        restaurant.setCapacity(TABLE_AMOUNT);

        return restaurant;
    }

    private List<BusinessHours> businessHours(){

        List<BusinessHours> businessHoursList = new ArrayList<>();

        BusinessHours businessHours = new BusinessHours();

        businessHours.setStart(RESTAURANT_BUSINESS_HOURS_START);
        businessHours.setFinish(RESTAURANT_BUSINESS_HOURS_FINISH);
        businessHours.setDayOfWeek(RESTAURANT_BUSINESS_HOURS_DAYOFWEEK);
        businessHours.setReservationHours(reservationHours());
        businessHours.setAvailable(true);

        businessHoursList.add(businessHours);

        return businessHoursList;
    }

    private List<ReservationHours> reservationHours(){

        List<ReservationHours> reservationHoursList = new ArrayList<>();

        ReservationHours reservationHours= new ReservationHours();

        reservationHours.setHour(RESTAURANT_BUSINESS_HOURS_START);
        reservationHours.setTableAmountAvailable(TABLE_AMOUNT_AVAILABLE);

        reservationHoursList.add(reservationHours);

        return reservationHoursList;
    }

    private Adress adress(){

        Adress adress = new Adress();

        adress.setStreet(RESTAURANT_ADRESS_STREET);
        adress.setAdressNumber(RESTAURANT_ADRESS_NUMBER);
        adress.setCity(RESTAURANT_ADRESS_CITY);
        adress.setState(RESTAURANT_ADRESS_STATE);

        return adress;
    }

    private List<ReservationControl> reservationControlList(Restaurant restaurant){
        List<ReservationControl> reservationControlList = new ArrayList<>();
        ReservationControl reservationControl = new ReservationControl();

        int hours = RESTAURANT_BUSINESS_HOURS_START.getHour();
        int minutes = RESTAURANT_BUSINESS_HOURS_START.getMinute();

        for(int i = 1; i <= constants.PLUS_RESERVATION_DAYS; i++){

            LocalDateTime dateAndHour = LocalDate.now().plusDays(i).atStartOfDay().plusHours(hours).plusMinutes(minutes);

            reservationControl.setId(RESTAURANT_ID);
            reservationControl.setDateAndTime(dateAndHour);
            reservationControl.setDayOfWeek(RESTAURANT_BUSINESS_HOURS_DAYOFWEEK);
            reservationControl.setTotalReservations(TABLE_AMOUNT_AVAILABLE);
            reservationControl.setCapacity(TABLE_AMOUNT);
            reservationControl.isAvailable();

            reservationControlList.add(reservationControl);

        }

        return reservationControlList;

    }


    private ReservationControl reservationControl(Restaurant restaurant){
        ReservationControl reservationControl = new ReservationControl();

        int hours = RESTAURANT_BUSINESS_HOURS_START.getHour();
        int minutes = RESTAURANT_BUSINESS_HOURS_START.getMinute();

        LocalDateTime dateAndHour = LocalDate.now().atStartOfDay().plusHours(hours).plusMinutes(minutes);

        reservationControl.setId(RESTAURANT_ID);
        reservationControl.setDateAndTime(dateAndHour);
        reservationControl.setDayOfWeek(RESTAURANT_BUSINESS_HOURS_DAYOFWEEK);
        reservationControl.setTotalReservations(TABLE_AMOUNT_AVAILABLE);
        reservationControl.setCapacity(TABLE_AMOUNT);
        reservationControl.isAvailable();

        return reservationControl;

    }

    private List<ReservationControl> reservationControlUnavailable(Restaurant restaurant){
        List<ReservationControl> reservationControlList = new ArrayList<>();

        int hours = RESTAURANT_BUSINESS_HOURS_START.getHour();
        int minutes = RESTAURANT_BUSINESS_HOURS_START.getMinute();

        for(int i = 1; i <= constants.PLUS_RESERVATION_DAYS; i++){

            ReservationControl reservationControl = new ReservationControl();

            LocalDateTime dateAndHour = LocalDate.now().plusDays(i).atStartOfDay().plusHours(hours).plusMinutes(minutes);

            reservationControl.setId(RESTAURANT_ID);
            reservationControl.setDateAndTime(dateAndHour);
            reservationControl.setDayOfWeek(RESTAURANT_BUSINESS_HOURS_DAYOFWEEK);
            reservationControl.setTotalReservations(TABLE_AMOUNT);
            reservationControl.setCapacity(TABLE_AMOUNT);
            reservationControl.setAvailable(false);

            reservationControlList.add(reservationControl);

        }

        return reservationControlList;

    }
}

