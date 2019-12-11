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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class ReservationControllerREST {

    //private static final Logger logger = LoggerFactory.getLogger(EmpruntController.class);


    @Autowired
    private LivreDao livreDao;

    @Autowired
    private MembreDao membreDao;

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping(value="/api/reservationMembre")
    List<Reservation> listReservationMembre(int membreId){
        Membre membre = membreDao.findById(membreId);
        return  reservationDao.findAllByMembre(membre);
    }

    @GetMapping(value="/api/listeReservationEncours")
    public List<Reservation> listeReservationsEnCours(){
        return reservationDao.findReservationsEncours(true);
        }

    @GetMapping(value="/api/creerReservation")
    public Reservation creerReservation(@RequestBody Reservation reservation){

        Date dateDebut = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime((dateDebut));
        c.add(Calendar.DATE, 28);
        Date dateFin = c.getTime();
        reservation.setDebutDate(dateDebut);
        reservation.setFinDate(dateFin);
        reservation.setEnCours(true);
        reservationDao.save(reservation);
        return reservation;


    }



}
