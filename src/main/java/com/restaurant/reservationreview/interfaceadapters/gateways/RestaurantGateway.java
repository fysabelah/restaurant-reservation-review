package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.frameworks.db.RestaurantRepository;
import com.restaurant.reservationreview.usercase.RestaurantBusiness;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantGateway {
    @Resource
    private RestaurantRepository repository;

    @Resource
    private RestaurantBusiness business;

    public Restaurant insert(Restaurant restaurant) throws ValidationsException {
        restaurant = this.business.create(restaurant);

        return this.repository.insert(restaurant);
    }

    public Page<Restaurant> findAll(Pageable pageable){
        return this.repository.findAll(pageable);
    }

    public Restaurant findById(String id) throws ValidationsException {
        return this.repository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "Restaurant", id));
    }

    public Restaurant findByName(String nameRestaurant) {
        return this.repository.findByNameEquals(nameRestaurant);
    }

    public void delete(Restaurant restaurant) {
        this.repository.delete(restaurant);
    }

    public Restaurant update(String idRestaurant, Restaurant restaurant) throws ValidationsException{
        // business irá verificar se o Id enviado na requisição está correto e retorna as informações atuais do restaurant
        Optional<Restaurant> restaurantUpdate = this.repository.findById(idRestaurant);

        Restaurant updateRestaurant = this.business.update(restaurantUpdate);

        // atribuímos no restaurante atual as novas informações
        updateRestaurant.setName(restaurant.getName());
        updateRestaurant.setLocation(restaurant.getLocation());
        updateRestaurant.setFoodType(restaurant.getFoodType());
        updateRestaurant.setActive(restaurant.getActive());
        updateRestaurant.setAverageRating(restaurant.getAverageRating());
        updateRestaurant.setQuantityTables(restaurant.getQuantityTables());
        updateRestaurant.setBusinessHours(restaurant.getBusinessHours());

        // por fim salvamos no banco de dados
        return this.repository.save(updateRestaurant);
    }
}