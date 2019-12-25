package com.biblioP7.feignClient;

import com.biblioP7.beans.*;
import com.biblioP7.exception.ReservationException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="reservation-service", url="http://localhost:8080")
public interface ReservationServiceClient {

    @GetMapping(value="/api/listeReservationsEnCours")
    List<Reservation> listeReservationsEnCours(@RequestHeader("Authorization") String token);

    @PostMapping(value="/api/listeReservationsMembre")
    List<Reservation> listeReservationsMembre(@RequestHeader("Authorization") String token, @RequestBody int membreId);

    @PostMapping(value="/api/creerReservation")
    Reservation creerReservation(@RequestHeader("Authorization") String token, @RequestBody Reservation reservation) throws ReservationException;

    @GetMapping(value="/api/detailReservation")
    Reservation detailReservation(@RequestHeader("Authorization") String token, @RequestParam int resaId);

    @GetMapping(value="/api/annulerReservation")
    void annulerReservation(@RequestHeader("Authorization") String token, @RequestParam int resaId, @RequestParam String detail);

    @PostMapping(value="/api/listeResaMembrePositions")
    List<ResaPosition> listeResaPositions (@RequestHeader("Authorization") String token, @RequestBody Membre membre);

    @GetMapping(value="/api/batchPurgerReservations")
    List<Reservation> purgerListeResa (@RequestHeader("Authorization") String token);

    @GetMapping(value="/api/validerOption")
    Emprunt validerOption(@RequestHeader("Authorization")String token, @RequestParam int resaId);
}
