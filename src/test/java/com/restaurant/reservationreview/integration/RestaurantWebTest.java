package com.restaurant.reservationreview.integration;

import com.restaurant.reservationreview.TestUtils;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.frameworks.db.RestaurantRepository;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@EnableWebMvc
class RestaurantWebTest extends TestUtils {

    private static final String REQUEST_MAPPING_ROOT = "/restaurant";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = this.restaurantRepository.save(new Restaurant());
    }

    @Test
    @DisplayName("Teste para salvar informações de um restaurante")
    void insert() throws Exception {
        String body = getMock("RestaurantInsert.json", RestaurantDto.class);

        String url = REQUEST_MAPPING_ROOT + "/" + this.restaurant.getId();

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

}
