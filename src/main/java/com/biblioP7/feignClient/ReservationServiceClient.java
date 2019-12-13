package com.biblioP7.feignClient;

import com.biblioP7.beans.Livre;
import com.biblioP7.beans.Membre;
import com.biblioP7.beans.Reservation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name="reservation-service", url="http://localhost:8080")
public interface ReservationServiceClient {

    @GetMapping(value="/api/listeReservationsEnCours")
    List<Reservation> listeReservationsEnCours(@RequestHeader("Authorization") String token);

    @PostMapping(value="/api/listeReservationsMembre")
    List<Reservation> listeReservationsMembre(@RequestHeader("Authorization") String token, @RequestBody int membreId);

    @PostMapping(value="/api/creerReservation")
    Reservation creerReservation(@RequestHeader("Authorization") String token, @RequestBody Reservation reservation);

    @GetMapping(value="/api/detailReservation")
    Reservation detailReservation(@RequestHeader("Authorization") String token, @RequestParam int resaId);

    @GetMapping(value="/api/annulerReservation")
    void annulerReservation(@RequestHeader("Authorization") String token, @RequestParam int resaId);

    @PostMapping(value="/api/listeResaMembrePositions")
    Map<Integer, Reservation> listeResaMembrePositions (@RequestHeader("Authorization") String token, @RequestBody Membre membre);
}
