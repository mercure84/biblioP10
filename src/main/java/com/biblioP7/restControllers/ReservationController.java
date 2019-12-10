package com.biblioP7.restControllers;


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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

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



    @GetMapping(value="/api/listeReservationEncours")
    public List<Reservation> listeReservationsEnCours(){
        return reservationDao.findReservationsEncours(true);
        }






}
