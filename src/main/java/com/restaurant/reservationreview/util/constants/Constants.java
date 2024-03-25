package com.restaurant.reservationreview.util.constants;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Constants {

    public final static Integer PLUS_ONE_DAY = 1;

    public final static Integer PLUS_RESERVATION_DAYS = 30;

    public static final String RESTAURANT_ID = "NEW_RESTAURANT_ID";
    public static final String RESTAURANT_NAME = "NEW_RESTAURANT";
    public static final String RESTAURANT_ADRESS_STREET = "AV 5";
    public static final String RESTAURANT_ADRESS_NUMBER = "5";
    public static final String RESTAURANT_ADRESS_CITY = "SAO PAULO";
    public static final String RESTAURANT_ADRESS_STATE = "SP";
    public static final LocalTime RESTAURANT_BUSINESS_HOURS_START = LocalTime.parse("19:00");
    public static final LocalTime RESTAURANT_BUSINESS_HOURS_FINISH = LocalTime.parse("23:00");
    public static final DayOfWeek RESTAURANT_BUSINESS_HOURS_DAYOFWEEK = DayOfWeek.valueOf("FRIDAY");
    public static final Integer TABLE_AMOUNT = 10;
    public static final Integer TABLE_AMOUNT_AVAILABLE = 8;

}
