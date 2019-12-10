package com.biblioP7.feignClient;

import com.biblioP7.beans.Reservation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name="reservation-service", url="http://localhost:8080")
public interface ReservationServiceClient {

    @GetMapping(value="/api/listeReservations")
    List<Reservation> listeReservations(@RequestHeader("Authorization") String token);

    @GetMapping(value="/api/listeReservationEnCours")
    List<Reservation> listeReservationsEnCours(@RequestHeader("Auhtorization") String token);



}
