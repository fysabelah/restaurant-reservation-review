package com.restaurant.reservationreview.entities;

import com.restaurant.reservationreview.util.exception.ValidationsException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Document("ratings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rating implements Serializable {

    @Id
    private String id;

    private String name;

    private Integer score;

    private String comment;

    private LocalDateTime date;

    @DBRef
    private Restaurant restaurant;

    private ZoneId zoneId;

    public void setName(String name) throws ValidationsException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationsException("0003");
        }

        this.name = name;
    }

    public void setScore(Integer score) throws ValidationsException {
        if (score < 0) {
            throw new ValidationsException("0102");
        }

        this.score = score;
    }
}
