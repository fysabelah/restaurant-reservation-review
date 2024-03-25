package com.restaurant.reservationreview.integration;

import com.restaurant.reservationreview.utils.TestUtils;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.frameworks.db.RestaurantRepository;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.exception.StandardError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@EnableWebMvc
class RestaurantWebTest extends TestUtils {

    private static final String REQUEST_MAPPING_ROOT = "/restaurant";

    @Autowired
    private MockMvc mockMvc;

    private static final String MOCK_DIRECTORY = "restaurant/";

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Restaurant getRestaurant() throws IOException {
        File file = getFile(MOCK_DIRECTORY + "RestaurantInsert.json");

        Restaurant restaurant = objectMapper.readValue(file, Restaurant.class);

        return this.restaurantRepository.save(restaurant);
    }

    @AfterEach
    void cleanUp() {
        restaurantRepository.deleteAll();
    }

    @Test
    @DisplayName("Teste para salvar informações de um restaurante")
    void insert() throws Exception {
        String body = getMock(MOCK_DIRECTORY + "RestaurantInsert.json", RestaurantDto.class);

        mockMvc.perform(
                post(REQUEST_MAPPING_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste não permitir nome em duplicidade de um restaurante")
    void insertDuplicate() throws Exception {
        String body = getMock(MOCK_DIRECTORY + "RestaurantInsert.json", RestaurantDto.class);

        getRestaurant();

        mockMvc.perform(
                post(REQUEST_MAPPING_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Teste para atualizar informações de um restaurante")
    void update() throws Exception {
        Restaurant restaurant = getRestaurant();

        String pathUpdateJsonFile = "RestaurantUpdate.json";
        File file = getFile(MOCK_DIRECTORY + pathUpdateJsonFile);

        Restaurant restaurantUpdate = objectMapper.readValue(file, Restaurant.class);
        String updateBody = getMock(MOCK_DIRECTORY + pathUpdateJsonFile, RestaurantDto.class);

        MockHttpServletResponse response = mockMvc.perform(
                        put(REQUEST_MAPPING_ROOT + "/" + restaurant.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateBody)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        RestaurantDto result = (RestaurantDto) getResponseBody(response, RestaurantDto.class);

        assertEquals(restaurantUpdate.getName(), result.getName());
    }

    @Test
    @DisplayName("Teste restaurante não encontrada por id")
    void findByIdThrow() throws Exception {
        String id = "idqualquer";


        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/" + id)
                ).andExpect(status().isBadRequest())
                .andReturn().getResponse();

        StandardError error = (StandardError) getResponseBody(response, StandardError.class);

        assertEquals(MessageUtil.getMessage("0001", "Restaurant", id), error.getMessage());
    }

}
