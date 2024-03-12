package com.restaurant.reservationreview.controller.service;

import com.restaurant.reservationreview.helper.DateAvailabilityHelper;
import com.restaurant.reservationreview.model.documents.Reservation;
import com.restaurant.reservationreview.model.documents.Restaurant;
import com.restaurant.reservationreview.model.repository.ReservationRepository;
import com.restaurant.reservationreview.model.repository.RestaurantRepository;
import com.restaurant.reservationreview.util.converter.RestaurantConverter;
import com.restaurant.reservationreview.util.dto.ReservationDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class ReservationService {

    private final MongoTemplate mongoTemplate;
    private final static Integer PLUS_DAYS_ONE = 1;
    private final static Integer PLUS_THIRTY_ONE_DAYS = 30;

    @Resource
    private RestaurantRepository restaurantRepository;

    @Resource
    private RestaurantConverter converter;

    @Resource
    private ReservationRepository reservationRepository;

    private DateAvailabilityHelper dateAvailabilityHelper;

    public ReservationService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<LocalDate> checkAvailableDates(ReservationDto dto, Integer table) throws ValidationsException{

        String restaurantId = dto.getRestaurant().getId();
        Restaurant restaurant = getRestaurant(restaurantId);
        LocalDateTime startDate = LocalDate.now().plusDays(PLUS_DAYS_ONE).atStartOfDay();
        LocalDateTime finishDate = LocalDate.now().plusDays(PLUS_THIRTY_ONE_DAYS).atStartOfDay();
        List<Reservation> reservations = getReservationsByRestaurantAndDateNextThirtyDays(restaurantId, startDate, finishDate);

        List<LocalDate> availableDates = dateAvailabilityHelper.checkDateAvailability(restaurant, reservations, table);

        // buscar em reservations pelo restaurante entre hoje +1 e hoje + 30

        return availableDates;
    }

    public List<LocalTime> availableHours(ReservationDto dto, Integer table, Date date) throws ValidationsException{
        return null;
    }

    public ReservationDto schedule(Integer table, Date date, LocalTime hour, ReservationDto dto) throws ValidationsException {

        Restaurant restaurant = getRestaurant(dto.getRestaurant().getId());

        if(restaurant.getTableAmountAvailable() > reservationDto.getReservationAmount()){
            Integer tableAmountAvailable = restaurant.getTableAmountAvailable() - reservationDto.getReservationAmount();
            restaurant.setTableAmountAvailable(tableAmountAvailable);

        }else{
            throw new ValidationsException("Temos apenas " + restaurant.getTableAmountAvailable()
                                            + " mesas dispon\u00EDveis para reserva. Cada mesa comporta 4 pessoas");
        }

        restaurant = repository.save(restaurant);
        return converter.convert(restaurant);
    }

    public ReservationDto findByEmail(ReservationDto dto){
        return null;
    }

    private Restaurant getRestaurant(String id) throws ValidationsException {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "Restaurante n\u00E3o encontrado", id));
    }

    private List<Reservation> getReservationsByRestaurantAndDateNextThirtyDays(String id, LocalDateTime start, LocalDateTime finish) throws ValidationsException {

        return reservationRepository.findReservationDateAvailability(id, start, finish);

//        TypedAggregation<Reservation> aggregation = Aggregation.newAggregation(Reservation.class,
//                Aggregation.match(Criteria.where("Restaurant.id").is(id)
//                        .and("date").gte(start).lte(finish)));
//
//        AggregationResults<Reservation> result = mongoTemplate.aggregate(aggregation, Reservation.class);
//        return result.getMappedResults();


//        AggregationOperation match = Aggregation.match(
//        Criteria.where("Restaurant.id").is(id)
//                .and("data").gte(start).lte(finish)
//        );
//
//        Aggregation aggregation = Aggregation.newAggregation(match);
//
//        // Executar a agregação
//        return mongoTemplate.aggregate(aggregation, "Reservation", Reservation.class).getMappedResults();

    }


}
