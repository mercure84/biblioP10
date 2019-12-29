package com.biblioP7.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ReservationException extends RuntimeException {

    public ReservationException(String pMessage) {
        super(pMessage);
    }

}
