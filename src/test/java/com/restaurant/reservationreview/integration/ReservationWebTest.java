package com.restaurant.reservationreview.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.restaurant.reservationreview.utils.TestUtils;
import com.restaurant.reservationreview.entities.*;
import com.restaurant.reservationreview.frameworks.db.ReservationControlRepository;
import com.restaurant.reservationreview.frameworks.db.ReservationRepository;
import com.restaurant.reservationreview.frameworks.db.RestaurantRepository;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.PersonDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.util.enums.ReservationStatus;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@EnableWebMvc
class ReservationWebTest extends TestUtils {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationControlRepository reservationControlRepository;

    private static final String REQUEST_MAPPING_ROOT = "/reservation";

    private static final String MOCK_DIRECTORY = "reservation/";

    private Restaurant restaurant;


    @BeforeEach
    void setUp() {
        restaurant = restaurantRepository.save(newRestaurant());
    }

    @AfterEach
    void cleanUp() {
        reservationRepository.deleteAll();
        restaurantRepository.deleteAll();
        reservationControlRepository.deleteAll();
    }

    @Test
    @DisplayName("Teste recuperar horas disponíveis para reserva para uma determinada data e quantidade de mesas")
    void shouldReturnAvailableDatesList() throws Exception {

        LocalDateTime dateAndTime = LocalDateTime.of(2024,03,28, 17, 00);
        Integer table = 1;
        ReservationControl reservationControl = newReservationControl(restaurant, dateAndTime);

        reservationControl = reservationControlRepository.save(reservationControl);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/available-dates"+ "/" + restaurant.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("table", String.valueOf(table))
                ).andExpect(status().isOk())
                .andReturn().getResponse();
        List<LocalDate> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<LocalDate>>() {});

        assertNotNull(result);
        assertEquals(result.size(), result.size());
        assertTrue(result.containsAll(result));

    }

    @Test
    @DisplayName("Teste recuperar datas disponíveis para reserva para uma determinada quantidade de mesas")
    void shouldReturnAvailableHoursList() throws Exception {

        LocalDateTime dateAndTime = LocalDateTime.of(2024,03,28, 17, 00);
        Integer table = 1;
        ReservationControl reservationControl = newReservationControl(restaurant, dateAndTime);

        reservationControl = reservationControlRepository.save(reservationControl);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/available-hours"+ "/" + restaurant.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("table", String.valueOf(table))
                                .param("date", "2024-03-28")
                ).andExpect(status().isOk())
                .andReturn().getResponse();
        List<LocalTime> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<LocalTime>>() {});

        assertNotNull(result);
        assertEquals(result.size(), result.size());
        assertTrue(result.containsAll(result));

    }

    @Test
    @DisplayName("Teste criação de reserva")
    void testSchedule() throws Exception {

        LocalDateTime dateAndTime = LocalDateTime.of(2024,03,28, 17, 00);
        Integer table = 1;
        ReservationControl reservationControl = newReservationControl(restaurant, dateAndTime);
        PersonDto body = newPersonDto();

        String requestBody = objectMapper.writeValueAsString(body);

        reservationControl = reservationControlRepository.save(reservationControl);

        MockHttpServletResponse response = mockMvc.perform(
                        post(REQUEST_MAPPING_ROOT + "/schedule"+ "/" + restaurant.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("table", String.valueOf(table))
                                .param("date", "2024-03-28")
                                .param("hour", "17:00")
                                .content(requestBody)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        ReservationDto result = (ReservationDto) getResponseBody(response, ReservationDto.class);

        assertNotNull(result);

    }

    @Test
    @DisplayName("Teste recuperar reservas por email em formato paginado")
    void findAllReservationsByEmail() throws Exception {

        String email = "jose@mail.com.br";
        File mock = getFile(MOCK_DIRECTORY + "reservationsByPeriod.json");
        List<Reservation> reservations = objectMapper.readValue(mock, new TypeReference<List<Reservation>>() {
        });
        reservations.forEach(reservation -> reservation.setRestaurant(restaurant));

        reservations = reservationRepository.saveAll(reservations);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/findReservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("email", email)
                                .param("pageSize", "4")
                                .param("initialPage", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        PagedResponse<ReservationDto> result = (PagedResponse<ReservationDto>) getResponseBody(response, PagedResponse.class);

        assertEquals(0, result.getPage().getPage());
        assertEquals(4, result.getPage().getPageSize());
        assertEquals(1, result.getPage().getTotalPages());
        assertEquals(1, result.getData().size());

    }

    @Test
    @DisplayName("Teste cancelamento de reserva")
    void testReservationCancelation() throws Exception {

        LocalDateTime dateAndTime = LocalDateTime.now();

        Reservation reservation = newReservation(restaurant, dateAndTime);

        reservation = reservationRepository.save(reservation);

        ReservationControl reservationControl = newReservationControl(restaurant, dateAndTime);

        reservationControl = reservationControlRepository.save(reservationControl);

        mockMvc.perform(
                        put(REQUEST_MAPPING_ROOT + "/reservationCancelation")
                                .param("reservationId", reservation.getId())
                ).andExpect(status().isNoContent());

    }

    private Reservation newReservation(Restaurant restaurant, LocalDateTime dateAndTime){

        Reservation reservation = new Reservation();
        Person person = newPerson();

        reservation.setId("6600f7c5155fcb542b4f7430");
        reservation.setRestaurant(restaurant);
        reservation.setPerson(person);
        reservation.setDateAndTime(dateAndTime);
        reservation.setReservationAmount(3);
        reservation.setDayOfWeek(dateAndTime.getDayOfWeek());
        reservation.setReservationStatus(ReservationStatus.SCHEDULED);

        return reservation;
    }

    private Restaurant newRestaurant(){

        Restaurant newRestaurant = new Restaurant();
        List<BusinessHours> businessHours = businessHours();

        newRestaurant.setId("6600f7c5155fcb542b4f5430");
        newRestaurant.setBusinessHours(businessHours);
        newRestaurant.setCapacity(10);

        return newRestaurant;
    }

    private List<BusinessHours> businessHours(){

        BusinessHours businessHours = new BusinessHours();
        List<BusinessHours> businessHoursList = new ArrayList<>();

        businessHours.setStart(LocalTime.of(17,00));
        businessHours.setFinish(LocalTime.of(23,00));
        businessHours.setDayOfWeek(DayOfWeek.THURSDAY);
        businessHours.setAvailable(true);
        businessHours.setReservationHours(reservationHours());

        businessHoursList.add(businessHours);

        return businessHoursList;

    }
    private List<ReservationHours> reservationHours(){

        ReservationHours reservationHours = new ReservationHours();
        List<ReservationHours> reservationHoursListList = new ArrayList<>();

        reservationHours.setHour(LocalTime.of(17,00));
        reservationHours.setTableAmountAvailable(10);

        reservationHoursListList.add(reservationHours);

        return reservationHoursListList;

    }

    private ReservationControl newReservationControl(Restaurant restaurant, LocalDateTime dateAndTime){

        ReservationControl reservationControl = new ReservationControl();

        reservationControl.setId("6600f7c5155fcb542b4f7431");
        reservationControl.setRestaurant(restaurant);
        reservationControl.setDayOfWeek(dateAndTime.getDayOfWeek());
        reservationControl.setTotalReservations(3);
        reservationControl.setCapacity(10);
        reservationControl.setAvailable(true);
        reservationControl.setDateAndTime(dateAndTime);

        return reservationControl;
    }

    private Person newPerson(){

        Person person = new Person();

        person.setName("Jose");
        person.setEmail("jose@mail.com.br");
        person.setPhone("11980809090");

        return person;

    }

    private PersonDto newPersonDto(){

        PersonDto personDto = new PersonDto();

        personDto.setName("Jose");
        personDto.setEmail("jose@mail.com.br");
        personDto.setPhone("11980809090");

        return personDto;

    }

    private List<LocalTime> availableHoursList(){

        List<LocalTime> availableHoursList = new ArrayList<>();

        for(int i = 0 ; i < 3 ; i++){

            LocalTime hour = LocalTime.now();

            hour.plusHours(i);

            availableHoursList.add(hour);

        }

        return availableHoursList;

    }

}
