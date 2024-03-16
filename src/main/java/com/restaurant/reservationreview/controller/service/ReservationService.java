package com.restaurant.reservationreview.controller.service;

import com.restaurant.reservationreview.model.repository.ReservationRepository;
import com.restaurant.reservationreview.util.converter.ReservationConverter;
import com.restaurant.reservationreview.util.dto.reservation.ReservationDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Resource
    private ReservationConverter reservationConverter;
    private ReservationRepository reservationRepository;

    public ReservationDto findByEmail(ReservationDto dto) throws ValidationsException{

        return reservationConverter.convert(reservationRepository.findByEmail(dto.getPersonDto().getEmail()));

    }
//
//    public List<LocalTime> availableHours(ReservationDto dto, Integer table, Date date) throws ValidationsException{
//        return null;
//    }
//
//    public ReservationDto schedule(Integer table, Date date, LocalTime hour, ReservationDto dto) throws ValidationsException {
//
//        Restaurant restaurant = getRestaurant(dto.getRestaurant().getId());
//
//        if(restaurant.getTableAmountAvailable() > reservationDto.getReservationAmount()){
//            Integer tableAmountAvailable = restaurant.getTableAmountAvailable() - reservationDto.getReservationAmount();
//            restaurant.setTableAmountAvailable(tableAmountAvailable);
//
//        }else{
//            throw new ValidationsException("Temos apenas " + restaurant.getTableAmountAvailable()
//                                            + " mesas dispon\u00EDveis para reserva. Cada mesa comporta 4 pessoas");
//        }
//
//        restaurant = repository.save(restaurant);
//        return converter.convert(restaurant);
//    }
//
//    public ReservationDto findByEmail(ReservationDto dto){
//        return null;
//    }
//
//    private Restaurant getRestaurant(String id) throws ValidationsException {
//        return restaurantRepository.findById(id)
//                .orElseThrow(() -> new ValidationsException("0001", "Restaurante n\u00E3o encontrado", id));
//    }
//
//    private List<Reservation> getReservationsByRestaurantAndDateNextThirtyDays(String id, LocalDateTime start, LocalDateTime finish) throws ValidationsException {
//
//        return reservationRepository.findReservationDateAvailability(id, start, finish);
//
////        TypedAggregation<Reservation> aggregation = Aggregation.newAggregation(Reservation.class,
////                Aggregation.match(Criteria.where("Restaurant.id").is(id)
////                        .and("date").gte(start).lte(finish)));
////
////        AggregationResults<Reservation> result = mongoTemplate.aggregate(aggregation, Reservation.class);
////        return result.getMappedResults();
//
//
////        AggregationOperation match = Aggregation.match(
////        Criteria.where("Restaurant.id").is(id)
////                .and("data").gte(start).lte(finish)
////        );
////
////        Aggregation aggregation = Aggregation.newAggregation(match);
////
////        // Executar a agregação
////        return mongoTemplate.aggregate(aggregation, "Reservation", Reservation.class).getMappedResults();
//
//    }


}
