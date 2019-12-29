package com.biblioP7.feignClient;

import com.biblioP7.exception.ReservationException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    public Exception decode (String invoqueur, Response reponse){

        if(reponse.status() == 403){

            return new ReservationException("Réservation impossible !");
        }
        return defaultErrorDecoder.decode (invoqueur, reponse);
    }


}
