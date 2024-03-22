package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.entities.ReservationControl;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.framework.db.ReservationControlRepository;
import com.restaurant.reservationreview.framework.db.ReservationRepository;
import com.restaurant.reservationreview.framework.db.RestaurantRepository;
import com.restaurant.reservationreview.helper.ReservationHelper;
import com.restaurant.reservationreview.interfaceadapters.presenters.ReservationPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ReservationControlGateway {

    private final static Integer PLUS_ONE_DAY = 1;

    private final static Integer PLUS_RESERVATION_DAYS = 30;

    @Resource
    private RestaurantRepository restaurantRepository;

    @Resource
    private ReservationControlRepository reservationControlRepository;

    @Resource
    private ReservationPresenter reservationPresenter;

    @Resource
    private ReservationRepository reservationRepository;

    @Resource
    private ReservationHelper reservationHelper;

    public List<LocalDate> checkAvailableDates(ReservationDto dto, Integer table) throws ValidationsException {

        String restaurantId = dto.getRestaurantDto().getId();
        Restaurant restaurant = getRestaurant(restaurantId);
        LocalDateTime startDate = LocalDate.now().plusDays(PLUS_ONE_DAY).atStartOfDay();
        LocalDateTime finishDate = LocalDate.now().plusDays(PLUS_RESERVATION_DAYS).atStartOfDay();
        Optional<List<ReservationControl>> reservations = getReservationsByRestaurantAndDateNextDays(restaurantId, startDate, finishDate);

        if (!reservations.isEmpty()) {

            List<LocalDate> availableDates = reservationHelper.checkDateAvailability(restaurant, reservations.get(), table);

            return availableDates;

        }else{

            return reservationHelper.nextDaysList();

        }

    }

    public List<LocalTime> checkAvailableHours(ReservationDto dto, Integer table, LocalDate date) throws ValidationsException {

        String restaurantId = dto.getRestaurantDto().getId();
        Restaurant restaurant = getRestaurant(restaurantId);
        int dayOfYear = date.getDayOfYear();
        int year = date.getYear();
        LocalDateTime startDate = LocalDate.ofYearDay(year, dayOfYear).atStartOfDay();
        LocalDateTime finishDate = LocalDate.ofYearDay(year, dayOfYear).plusDays(PLUS_ONE_DAY).atStartOfDay();
        DayOfWeek weekDayEnum = DayOfWeek.valueOf(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US).toUpperCase());
        Optional<List<ReservationControl>> reservations = getReservationsByDate(restaurantId, startDate, finishDate);

        if (reservations.isPresent()) {

            List<LocalTime> availableHours = reservationHelper.checkAvailableHours(restaurant, reservations.get(), weekDayEnum, table);

            return availableHours;

        }else{

            return reservationHelper.checkAvailableHoursByDayOfWeek(restaurant, weekDayEnum);

        }
    }

    public ReservationDto schedule(Integer table, LocalDate date, LocalTime hour, ReservationDto dto) throws ValidationsException {

//        tentar usa o codium pra ajudar aqui
//        criar um atributo com a data e a hora recebidas, pra buscar em reservationControl
//          se existir, atualiza a disponibilidade de mesas para o horário
//          se não existir, criar registro para o dia e horário em reservationControl, pegando a quantidade de mesas disponíveis pelo restaurant.businessHours, de acordo com o dayOfWeek
//        no final, criar o registro da reserva

        String restaurantId = dto.getRestaurantDto().getId();
        Restaurant restaurant = getRestaurant(restaurantId);

        int dayOfYear = date.getDayOfYear();
        int year = date.getYear();
        int hours = hour.getHour();
        int minutes = hour.getMinute();
        LocalDateTime dateAndHour = LocalDate.ofYearDay(year, dayOfYear).atStartOfDay().plusHours(hours).plusMinutes(minutes);
        DayOfWeek weekDayEnum = DayOfWeek.valueOf(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US).toUpperCase());
        Optional<ReservationControl> reservationControl = getReservationsByDateAndHour(restaurantId, dateAndHour);

        if (reservationControl.isEmpty()) {

            reservationControlRepository.insert(reservationHelper.newReservationControl(restaurant, dateAndHour, hour, weekDayEnum, table));

        }else{

            reservationControlRepository.update(reservationHelper.updateReservationControl(reservationControl.get(), table));

        }

//      atribui os valores da nova reserva para salvar
        Reservation reservation = reservationHelper.newReservation(restaurant, table, dateAndHour, weekDayEnum, dto);

        return reservationPresenter.convert(reservationRepository.insert(reservation));

    }

    private Restaurant getRestaurant(String id) throws ValidationsException {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "Restaurante n\u00E3o encontrado", id));
    }

    private Optional<List<ReservationControl>> getReservationsByRestaurantAndDateNextDays(String id, LocalDateTime start, LocalDateTime finish) {

        return reservationControlRepository.findAvailableReservationDates(id, start, finish);

    }

    private Optional<List<ReservationControl>> getReservationsByDate(String id, LocalDateTime start, LocalDateTime finish) {

        return reservationControlRepository.findReservationsByDate(id, start, finish);

    }

    private Optional<ReservationControl> getReservationsByDateAndHour(String id, LocalDateTime dateAndHour) {

        return reservationControlRepository.findReservationsByDateAndHour(id, dateAndHour);

    }

}

