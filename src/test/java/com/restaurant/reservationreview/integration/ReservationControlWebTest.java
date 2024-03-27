package com.restaurant.reservationreview.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.restaurant.reservationreview.utils.TestUtils;
import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.entities.ReservationControl;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.frameworks.db.ReservationControlRepository;
import com.restaurant.reservationreview.frameworks.db.ReservationRepository;
import com.restaurant.reservationreview.frameworks.db.RestaurantRepository;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationControlDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
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
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@EnableWebMvc
class ReservationControlWebTest extends TestUtils {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationControlRepository reservationControlRepository;

    private static final String REQUEST_MAPPING_ROOT = "/reservationControl";

    private static final String MOCK_DIRECTORY = "reservationControl/";

    private Restaurant restaurant;


    @BeforeEach
    void setUp() {
        restaurant = restaurantRepository.save(new Restaurant());
    }

    @AfterEach
    void cleanUp() {
        reservationRepository.deleteAll();
        restaurantRepository.deleteAll();
        reservationControlRepository.deleteAll();
    }

    @Test
    @DisplayName("Teste recuperar reservas por restaurante e período de datas em formato paginado")
    void findAllReservationsByRestaurantAndDateBetween() throws Exception {

        File mock = getFile(MOCK_DIRECTORY + "reservationsByPeriod.json");
        List<Reservation> reservations = objectMapper.readValue(mock, new TypeReference<List<Reservation>>() {
        });
        String start = LocalDate.now().toString();
        String finish = LocalDate.now().plusDays(1).toString();
        reservations.forEach(reservation -> reservation.setRestaurant(restaurant));

        reservations = reservationRepository.saveAll(reservations);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/reservationsByPeriod/" + restaurant.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("data-inicio", start)
                                .param("data-fim", finish)
                                .param("pageSize", "4")
                                .param("initialPage", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        PagedResponse<ReservationDto> result = (PagedResponse<ReservationDto>) getResponseBody(response, PagedResponse.class);

        assertEquals(0, result.getPage().getPage());
        assertEquals(4, result.getPage().getPageSize());
        assertEquals(2, result.getPage().getTotalPages());
        assertEquals(4, result.getData().size());

    }

    @Test
    @DisplayName("Teste recuperar reservas por restaurante e período de datas em formato paginado")
    void findAllReservationControlByRestaurantIdAndDateBetween() throws Exception {

        File mock = getFile(MOCK_DIRECTORY + "reservationControlByPeriod.json");
        List<ReservationControl> reservationsControl = objectMapper.readValue(mock, new TypeReference<List<ReservationControl>>() {
        });
        String start = "2024-03-28";
        String finish = "2024-03-29";
        reservationsControl.forEach(reservationControl -> reservationControl.setRestaurant(restaurant));

        reservationsControl = reservationControlRepository.saveAll(reservationsControl);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/reservationControlByPeriod/" + restaurant.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("data-inicio", start)
                                .param("data-fim", finish)
                                .param("pageSize", "4")
                                .param("initialPage", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        PagedResponse<ReservationControlDto> result = (PagedResponse<ReservationControlDto>) getResponseBody(response, PagedResponse.class);

        assertEquals(0, result.getPage().getPage());
        assertEquals(4, result.getPage().getPageSize());
        assertEquals(2, result.getPage().getTotalPages());
        assertEquals(4, result.getData().size());

    }

}
