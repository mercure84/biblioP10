package com.biblioP7.restControllers;


import com.biblioP7.beans.*;
import com.biblioP7.dao.EmpruntDao;
import com.biblioP7.dao.LivreDao;
import com.biblioP7.dao.MembreDao;
import com.biblioP7.dao.ReservationDao;
import com.biblioP7.exception.FunctionalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ReservationControllerREST {

    //private static final Logger logger = LoggerFactory.getLogger(EmpruntController.class);

    private static final Logger logger = LoggerFactory.getLogger(MembreControllerREST.class);

    @Autowired
    private MembreDao membreDao;

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private LivreDao livreDao;

    @Autowired
    private EmpruntDao empruntDao;

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
    public Reservation creerReservation(@RequestHeader("Authorization") String token, @RequestBody Reservation reservation) throws FunctionalException {


        // RG : on peut réserver le livre uniquement si la liste n'est pas pleine à savoir nbResa du livre < 2 x stock total
        int nbResaLivre = reservationDao.trouverResaEncoursParLivre(reservation.getLivre()).size();
        if (nbResaLivre >=(reservation.getLivre().getStockTotal() * 2)){
            throw new FunctionalException("Le livre " + reservation.getLivre().getTitre() + " ne peut pas être réservé, trop d'exemlaires demandés");

        }

            //RG : on peut réserver le livre uniquement si on ne l'a déjà pas en cours d'emprunt et si on ne l'a pas déjà en cours de réservation
            List<Reservation> listeResaMembre = reservationDao.trouverResaEncoursParMembre(reservation.getMembre());
            List<Emprunt> listeEmpruntMembre = empruntDao.trouverEmpruntEncoursParMembre(reservation.getMembre());

            for (Reservation resa : listeResaMembre
            ) {
                if (resa.getLivre().equals(reservation.getLivre())) {
                    throw new FunctionalException("le livre " + reservation.getLivre().getTitre() + " ne peut pas être réservé, car le membre a déjà une réservation en cours pour cet ouvrage");


                }
            }
            for (Emprunt emprunt : listeEmpruntMembre) {
                if (emprunt.getLivre().equals(reservation.getLivre())) {
                    throw new FunctionalException("le livre " + reservation.getLivre().getTitre() + " ne peut pas être réservé car le membre a déjà un emprunt en cours pour cet ouvrage");
                }
            }


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

    //la réservation est annulée à l'initiative du membre
    @GetMapping(value="/api/annulerReservation")
    public void annulerReservation(@RequestHeader("Authorization") String token, @RequestParam int resaId, @RequestParam String detail){

        Reservation reservation = reservationDao.findById(resaId);
        // si nous sommes dans les 48h d'une option il faut aussi remettre l livre en stock et surtout regarder s'il n'y a pas un autre membre du attend le livre
        if (reservation.getDateFin()  != null){
            Livre livreLibere = reservation.getLivre();
            gererListeAttente(livreLibere);
        }

        reservation.setEncours(false);
        reservation.setDetail(detail);
        reservationDao.save(reservation);
        }

    private int positionResaMembre(int livreId, int membreId){
        int position = 1;
        // récupération de la liste des résa pour le livre donné
        List<Reservation> listeResa = reservationDao.trouverResaEncoursParLivre(livreDao.findById(livreId));

        for (int i = 0; i< listeResa.size() ; i++
             ) {
            if (listeResa.get(i).getMembre().getId() == membreId ){
             position = i+1;
             break;
            }
            }
        return position;
    }

    @PostMapping(value="/api/listeResaMembrePositions")
    public List<ResaPosition> listeResaPositions (@RequestHeader("Authorization") String token, @RequestBody Membre membre){
    List<ResaPosition> listeResaMembre = new ArrayList<ResaPosition>();
    List<Reservation> listeResa = reservationDao.findAllByMembre(membre);

        for (Reservation resa : listeResa
             ) {
            int position = 1;
            if (resa.isEncours()){
            position = this.positionResaMembre(resa.getLivre().getId(), membre.getId());}
            listeResaMembre.add(new ResaPosition(resa, position));
            }
        return listeResaMembre;

    }

    //batch qui va nettoyer les réservations échues : à lancer tous les jours à 0h01
    @GetMapping(value="/api/batchPurgerReservations")
    public List<Reservation> purgerListeResa (@RequestHeader("Authorization") String token){
        // récupération de la liste des réservation qui sont en cours et qui ont une date > date du jour
        // nous sommes le 15/12 : toutes les réservations en cours avec pour échéance le 14/12 doivent être passée en "expirées"
        List<Reservation> listeResaPurge = reservationDao.listerResaExpiree(new Date());

        // on parcourt chaque réservation et on l'annule :
        // le livre doit revenir en stock : on contrôle comme une restitution si un autre membre l'a réservé ou pas
        for (Reservation resa : listeResaPurge
             ) {
            resa.setEncours(false);
            resa.setDetail("Expirée");
            Livre livreLibere = resa.getLivre();

            gererListeAttente(livreLibere);
        }
        logger.info("Les réservation suivantes ont été mises en statut expiré : " + listeResaPurge);
        return listeResaPurge;
    }


// méthode qui va traiter la liste d'attente d'un livre quand une option est annulée par le batch ou un membre directement
    private void gererListeAttente(Livre livreParam){
        List<Reservation> listeResaLivre = reservationDao.trouverResaEncoursParLivre(livreParam);
        // si on trouve une liste de réservations sur le livre on va parcourir cette liste et opérer les traitements adequats
        boolean absenceNewOption = true;
        if (listeResaLivre.size() > 0){
            //on parcours la liste des réservation qui est ordonnée par les ID
            for (int i = 0 ; i < listeResaLivre.size() ; i++){
                if ((listeResaLivre.get(i).isEncours()) && (listeResaLivre.get(i).getDateDebut() == null)){
                    //si on est ici c'est qu'on a attrapé une résa en cours qui n'a pas encore commencée !!
                    Reservation premiereResa = listeResaLivre.get(i) ;
                    premiereResa.setDetail("Livre Disponible");
                    // on met une date de fin à la résa (+48h)
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(Calendar.DATE, 2);
                    Date dateFin = c.getTime();
                    premiereResa.setDateFin(dateFin);
                    premiereResa.setDateDebut(new Date());
                    // on informe le membre par mail :
                    Membre premierMembre = premiereResa.getMembre();
                    Livre livre = premiereResa.getLivre();
                    System.out.println("Cher " + premierMembre.getPrenom() + " " + premierMembre.getPrenom() + ", vous attendiez le livre " +
                            livre.getTitre() + " depuis "+ premiereResa.getDateDemande() + ", le voici disponible pour 48h à votre bibliothèque préférée !! Ne tardez pas à venir le chercher.");
                    // on sauvegarde la résa en persistance !
                    reservationDao.save(premiereResa);
                    absenceNewOption = false;
                    // on sort de notre boucle for
                    break;
                }
            }
        }
        if (absenceNewOption){

            //on remplit le stock du livre +1
            livreParam.restituerLivre();
            livreDao.save(livreParam);
            logger.info("Le Livre suivant doit être remis en stock : " + livreParam.getTitre() + " Id :" + livreParam.getId());
        }

    }




}
