package com.restaurant.reservationreview.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.restaurant.reservationreview.TestUtils;
import com.restaurant.reservationreview.model.documents.restaurant.Rating;
import com.restaurant.reservationreview.model.documents.restaurant.Restaurant;
import com.restaurant.reservationreview.model.repository.RatingRepository;
import com.restaurant.reservationreview.model.repository.RestaurantRepository;
import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.dto.restaurant.RatingDto;
import com.restaurant.reservationreview.util.exception.StandardError;
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
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@EnableWebMvc
class RatingControllerTest extends TestUtils {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RatingRepository ratingRepository;

    private static final String REQUEST_MAPPING_ROOT = "/rating";

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = restaurantRepository.save(new Restaurant());
    }

    @AfterEach
    void cleanUp() {
        ratingRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @Test
    @DisplayName("Teste salvar avaliação")
    void insert() throws Exception {
        String body = getMock("insert.json", RatingDto.class);

        mockMvc.perform(post(REQUEST_MAPPING_ROOT + "/" + restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste recuperar avaliações em formato paginado")
    void findAll() throws Exception {
        File mock = getFile("findAll.json");
        List<Rating> ratings = objectMapper.readValue(mock, new TypeReference<List<Rating>>() {
        });

        ratings.forEach(rating -> rating.setRestaurant(restaurant));

        ratings = ratingRepository.saveAll(ratings);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/restaurant/" + restaurant.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("pageSize", "3")
                                .param("initialPage", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        PagedResponse<RatingDto> result = (PagedResponse<RatingDto>) getResponseBody(response, PagedResponse.class);

        assertEquals(0, result.getPage().getPage());
        assertEquals(3, result.getPage().getPageSize());
        assertEquals(2, result.getPage().getTotalPages());
        assertEquals(3, result.getData().size());
    }

    @Test
    @DisplayName("Teste avaliação não encontrada")
    void findByIdThrow() throws Exception {
        String id = "idqualquer";

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/" + id)
                ).andExpect(status().isBadRequest())
                .andReturn().getResponse();

        StandardError error = (StandardError) getResponseBody(response, StandardError.class);

        assertEquals(MessageUtil.getMessage("0001", "avalia\u00E7\u00E3o", id), error.getMessage());
    }

    @Test
    @DisplayName("Teste deletar avaliação")
    void remove() throws Exception {
        Rating rating = getRating();

        mockMvc.perform(
                delete(REQUEST_MAPPING_ROOT + "/" + rating.getId())
        ).andExpect(status().isNoContent());
    }

    @Test
    void updateWithScore() throws Exception {
        Rating rating = getRating();

        String body = "muito boa a comida";

        MockHttpServletResponse response = mockMvc.perform(
                        put(REQUEST_MAPPING_ROOT + "/" + rating.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("score", "9")
                                .content(body)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        RatingDto result = (RatingDto) getResponseBody(response, RatingDto.class);

        assertEquals(body, result.getComment());
        assertEquals(9, result.getScore());
    }

    @Test
    void update() throws Exception {
        Rating rating = getRating();

        String body = "apenas novo comentário";

        MockHttpServletResponse response = mockMvc.perform(
                        put(REQUEST_MAPPING_ROOT + "/" + rating.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        RatingDto result = (RatingDto) getResponseBody(response, RatingDto.class);

        assertEquals(body, result.getComment());
        assertEquals(rating.getScore(), result.getScore());
    }

    private Rating getRating() throws IOException {
        File file = getFile("insert.json");

        Rating rating = objectMapper.readValue(file, Rating.class);
        rating.setDate(TimeUtils.now());

        return ratingRepository.save(rating);
    }
}