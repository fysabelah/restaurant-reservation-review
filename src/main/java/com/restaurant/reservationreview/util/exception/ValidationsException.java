package com.restaurant.reservationreview.util.exception;


import com.restaurant.reservationreview.util.MessageUtil;

public class ValidationsException extends Exception {

    public ValidationsException(String code) {
        super(MessageUtil.getMessage(code));
    }

    public ValidationsException(String code, String... args) {
        super(MessageUtil.getMessage(code, args));
    }
}