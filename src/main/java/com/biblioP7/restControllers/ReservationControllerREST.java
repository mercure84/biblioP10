package com.biblioP7.restControllers;


import com.biblioP7.beans.Membre;
import com.biblioP7.beans.Reservation;
import com.biblioP7.dao.LivreDao;
import com.biblioP7.dao.MembreDao;
import com.biblioP7.dao.ReservationDao;
import com.biblioP7.security.JwtTokenUtil;
import com.biblioP7.security.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class ReservationControllerREST {

    //private static final Logger logger = LoggerFactory.getLogger(EmpruntController.class);


    @Autowired
    private MembreDao membreDao;

    @Autowired
    private ReservationDao reservationDao;

    @CrossOrigin("*")
    @PostMapping(value="/api/listeReservationsMembre")
    public List<Reservation> listeReservationsMembre(@RequestHeader("Authorization") String token, @RequestBody int membreId){
        Membre membre = membreDao.findById(membreId);
        return  reservationDao.findAllByMembre(membre);
    }

    @CrossOrigin("*")
    @GetMapping(value="/api/listeReservationsEnCours")
    public List<Reservation> listeReservationsEnCours(){
        return reservationDao.findReservationsEncours(true);
        }

    @CrossOrigin("*")
    @PostMapping(value="/api/creerReservation")
    public Reservation creerReservation(@RequestHeader("Authorization") String token, @RequestBody Reservation reservation){

        Date dateDemande = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime((dateDemande));
        reservation.setDateDemande(dateDemande);
        reservation.setEncours(true);
        reservationDao.save(reservation);
        return reservation;


    }

    @GetMapping(value="/api/detailReservation")
    public Reservation detailReservation(@RequestHeader("Authorization") String token, @RequestParam int resaId){
        Reservation reservation = reservationDao.findById(resaId);
        return reservation;
    }

    @GetMapping(value="/api/annulerReservation")
    public void annulerReservation(@RequestHeader("Authorization") String token, @RequestParam int resaId){

        Reservation reservation = reservationDao.findById(resaId);
        reservation.setEncours(false);
        reservationDao.save(reservation);
        }



}
