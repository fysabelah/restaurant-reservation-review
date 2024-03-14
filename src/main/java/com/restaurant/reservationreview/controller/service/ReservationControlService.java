package com.restaurant.reservationreview.controller.service;

import com.restaurant.reservationreview.helper.DateAvailabilityHelper;
import com.restaurant.reservationreview.model.documents.reservation.ReservationControl;
import com.restaurant.reservationreview.model.documents.reservation.Reservations;
import com.restaurant.reservationreview.model.documents.restaurant.Restaurant;
import com.restaurant.reservationreview.model.repository.ReservationControlRepository;
import com.restaurant.reservationreview.model.repository.ReservationRepository;
import com.restaurant.reservationreview.model.repository.RestaurantRepository;
import com.restaurant.reservationreview.util.converter.RestaurantConverter;
import com.restaurant.reservationreview.util.dto.reservation.ReservationDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class ReservationControlService {

    private final MongoTemplate mongoTemplate;

    private final static Integer PLUS_ONE_DAY = 1;

    private final static Integer PLUS_RESERVATION_DAYS = 30;

    @Resource
    private RestaurantRepository restaurantRepository;

    @Resource
    private ReservationControlRepository reservationControlRepository;

    @Resource
    private RestaurantConverter converter;

    @Resource
    private ReservationRepository reservationRepository;

    private DateAvailabilityHelper dateAvailabilityHelper;

    public ReservationControlService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<LocalDate> checkAvailableDates(ReservationDto dto, Integer table) throws ValidationsException {

        String restaurantId = dto.getRestaurant().getId();
        Restaurant restaurant = getRestaurant(restaurantId);
        LocalDateTime startDate = LocalDate.now().plusDays(PLUS_ONE_DAY).atStartOfDay();
        LocalDateTime finishDate = LocalDate.now().plusDays(PLUS_RESERVATION_DAYS).atStartOfDay();
        List<ReservationControl> reservations = getReservationsByRestaurantAndDateNextThirtyDays(restaurantId, startDate, finishDate);

        List<LocalDate> availableDates = dateAvailabilityHelper.checkDateAvailability(restaurant, reservations, table);

        // buscar em reservations pelo restaurante entre hoje +1 e hoje + 30

        return availableDates;
    }

    public List<LocalTime> availableHours(ReservationDto dto, Integer table, Date date) throws ValidationsException {
        return null;
    }

    public ReservationDto schedule(Integer table, Date date, LocalTime hour, ReservationDto dto) throws ValidationsException {

        Restaurant restaurant = getRestaurant(dto.getRestaurant().getId());

        if (restaurant.getTableAmountAvailable() > reservationDto.getReservationAmount()) {
            Integer tableAmountAvailable = restaurant.getTableAmountAvailable() - reservationDto.getReservationAmount();
            restaurant.setTableAmountAvailable(tableAmountAvailable);

        } else {
            throw new ValidationsException("Temos apenas " + restaurant.getTableAmountAvailable()
                    + " mesas dispon\u00EDveis para reserva. Cada mesa comporta 4 pessoas");
        }

        restaurant = repository.save(restaurant);
        return converter.convert(restaurant);
    }

    public ReservationDto findByEmail(ReservationDto dto) {
        return null;
    }

    private Restaurant getRestaurant(String id) throws ValidationsException {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "Restaurante n\u00E3o encontrado", id));
    }

    private List<ReservationControl> getReservationsByRestaurantAndDateNextThirtyDays(String id, LocalDateTime start, LocalDateTime finish) throws ValidationsException {

        return reservationControlRepository.findAvailableReservationDates(id, start, finish);

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

}
