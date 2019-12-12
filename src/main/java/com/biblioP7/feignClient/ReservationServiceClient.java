package com.biblioP7.feignClient;

import com.biblioP7.beans.Reservation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name="reservation-service", url="http://localhost:8080")
public interface ReservationServiceClient {

    @GetMapping(value="/api/listeReservationsEnCours")
    List<Reservation> listeReservationsEnCours(@RequestHeader("Authorization") String token);

    @PostMapping(value="/api/listeReservationsMembre")
    List<Reservation> listeReservationsMembre(@RequestHeader("Authorization") String token, @RequestBody Integer membreId);

    @PostMapping(value="/api/creerReservation")
    Reservation creerReservation(@RequestHeader("Authorization") String token, @RequestBody Reservation reservation);



}
