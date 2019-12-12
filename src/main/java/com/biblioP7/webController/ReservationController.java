package com.biblioP7.webController;

import com.biblioP7.beans.Livre;
import com.biblioP7.beans.Membre;
import com.biblioP7.beans.Reservation;
import com.biblioP7.feignClient.LivreServiceClient;
import com.biblioP7.feignClient.MembreServiceClient;
import com.biblioP7.feignClient.ReservationServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;


@Controller
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    ReservationServiceClient reservationServiceClient;

    @Autowired
    MembreServiceClient membreServiceClient;

    @Autowired
    LivreServiceClient livreServiceClient;


    @GetMapping("/client/reservation")
    public String afficherReservation(HttpSession session, Model model, String livreId){

        String token = session.getAttribute("token").toString();
        Livre livreToReservation = livreServiceClient.detailLivre(token, Integer.parseInt(livreId));
        String email = session.getAttribute("membreEmail").toString();

        Membre membre = membreServiceClient.dataMembre(token, email);
        model.addAttribute("membre", membre);
        model.addAttribute("livre", livreToReservation);

        //TODO
        // vérifier que le livre respecte les RG de la réservation : liste résa = maximum de 2 x le nombre d'exemplaire dispo de l'ouvrage
        // impossible de réserver un livre déjà emprunté par le même utilisateur

        return "reservation";
    }

    @GetMapping("/client/reserverLivre")
    public String creerReservation(HttpSession session, Model model, String livreId){

        String token = session.getAttribute("token").toString();
        Livre livre = livreServiceClient.detailLivre(token, Integer.parseInt(livreId));
        String email = session.getAttribute("membreEmail").toString();

        Membre membre = membreServiceClient.dataMembre(token, email);
        Reservation reservation = new Reservation();
        reservation.setMembre(membre);
        reservation.setLivre(livre);
        reservationServiceClient.creerReservation(token, reservation);
        return "redirect:/client/dashboard";
    }



}
