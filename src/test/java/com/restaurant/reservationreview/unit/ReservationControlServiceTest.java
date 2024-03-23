package com.restaurant.reservationreview.unit;

import com.restaurant.reservationreview.TestUtils;
import com.restaurant.reservationreview.interfaceadapters.gateways.ReservationControlGateway;
import com.restaurant.reservationreview.entities.ReservationControl;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.AdressDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.BusinnessHoursDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationHoursDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
import com.restaurant.reservationreview.util.enums.FoodType;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ReservationControlServiceTest extends TestUtils {

    @Resource
    private ReservationControlGateway reservationControlGateway;
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
//    public void shouldReturnNoReservationsDatesAvailable() throws ValidationsException {
//
//        ReservationDto dto = new ReservationDto();
//        dto.setRestaurantDto(newRestaurantDto());
//        Integer table = 1;
//
//        // Mock the dependencies
//        Restaurant restaurant = new Restaurant();
//        restaurant.setBusinnessHours(new ArrayList<>());
//        List<ReservationControl> reservations = new ArrayList<>();
//        ReservationHelper reservationHelper = Mockito.mock(ReservationHelper.class);
//        Mockito.when(reservationHelper.checkDateAvailability(Mockito.any(Restaurant.class), Mockito.anyList(), Mockito.anyInt())).thenReturn(new ArrayList<>());
//
//         Act
//        List<LocalDate> availableDates = reservationControlGateway.checkAvailableDates(dto, table);
//
//         Assert
//        assertEquals(0, availableDates.size());
//    }
    public RestaurantDto newRestaurantDto(){

        RestaurantDto restaurantDto = new RestaurantDto();

        restaurantDto.setId(RESTAURANT_ID);
        restaurantDto.setName(RESTAURANT_NAME);
        restaurantDto.setAdressDto(adressDto());
        restaurantDto.setBusinnessHoursDto(businessHoursDto());
        restaurantDto.setFoodType(FoodType.BRAZILIAN);
        restaurantDto.setCapacity(TABLE_AMOUNT);

        return restaurantDto;
    }

    public List<BusinnessHoursDto> businessHoursDto(){

        List<BusinnessHoursDto> businessHoursDto = new ArrayList<>();

        BusinnessHoursDto businnessHours = new BusinnessHoursDto();

        businnessHours.setStart(RESTAURANT_BUSINESS_HOURS_START);
        businnessHours.setFinish(RESTAURANT_BUSINESS_HOURS_FINISH);
        businnessHours.setDayOfWeek(RESTAURANT_BUSINESS_HOURS_DAYOFWEEK);
        businnessHours.setReservationHoursDto(reservationHoursDto());
        businnessHours.setAvailable(true);

        businessHoursDto.add(businnessHours);

        return businessHoursDto;
    }

    public List<ReservationHoursDto> reservationHoursDto(){

        List<ReservationHoursDto> reservationHoursDto = new ArrayList<>();

        ReservationHoursDto reservationHours= new ReservationHoursDto();

        reservationHours.setHour(RESTAURANT_BUSINESS_HOURS_START);
        reservationHours.setTableAmountAvailable(TABLE_AMOUNT_AVAILABLE);

        reservationHoursDto.add(reservationHours);

        return reservationHoursDto;
    }

    public AdressDto adressDto(){

        AdressDto adress = new AdressDto();

        adress.setStreet(RESTAURANT_ADRESS_STREET);
        adress.setAdressNumber(RESTAURANT_ADRESS_NUMBER);
        adress.setCity(RESTAURANT_ADRESS_CITY);
        adress.setState(RESTAURANT_ADRESS_STATE);

        return adress;
    }
}