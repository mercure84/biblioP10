package com.biblioP7.restControllers;


import com.biblioP7.beans.Livre;
import com.biblioP7.beans.Membre;
import com.biblioP7.beans.ResaPosition;
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

import java.util.*;

@RestController
public class ReservationControllerREST {

    //private static final Logger logger = LoggerFactory.getLogger(EmpruntController.class);


    @Autowired
    private MembreDao membreDao;

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private LivreDao livreDao;

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
    public void annulerReservation(@RequestHeader("Authorization") String token, @RequestParam int resaId, @RequestParam String detail){

        Reservation reservation = reservationDao.findById(resaId);
        reservation.setEncours(false);
        reservation.setDetail(detail);
        reservationDao.save(reservation);
        }

    int positionResaMembre(int livreId, int membreId){
        int position = 1;
        // récupération de la liste des résa pour le livre donné
        List<Reservation> listeResa = reservationDao.trouverResaEncoursParLivre(livreDao.findById(livreId));

        for (Reservation resa : listeResa
             ) {
            position = (resa.getMembre().getId() == membreId ? position : position +1);
            }
        return position;
    }

    @PostMapping(value="/api/listeResaMembrePositions")
    List<ResaPosition> listeResaPositions (@RequestHeader("Authorization") String token, @RequestBody Membre membre){
    List<ResaPosition> listeResaMembre = new ArrayList<ResaPosition>();
    List<Reservation> listeResa = reservationDao.findAllByMembre(membre);

        for (Reservation resa : listeResa
             ) {
            int position = 0;
            if (resa.isEncours()){
            position = this.positionResaMembre(resa.getLivre().getId(), membre.getId());}
            listeResaMembre.add(new ResaPosition(resa, position));
            }
//        System.out.println("Liste resa avec position = " + listeResaMembre);
        return listeResaMembre;

    }



}
