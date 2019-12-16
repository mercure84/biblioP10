package com.biblioP7.webController;


import com.biblioP7.beans.CreationEmprunt;
import com.biblioP7.beans.Emprunt;
import com.biblioP7.beans.Livre;
import com.biblioP7.beans.Reservation;
import com.biblioP7.feignClient.EmpruntServiceClient;
import com.biblioP7.feignClient.LivreServiceClient;
import com.biblioP7.feignClient.MembreServiceClient;
import com.biblioP7.feignClient.ReservationServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    @Autowired
    EmpruntServiceClient empruntServiceClient;

    @Autowired
    MembreServiceClient membreServiceClient;

    @Autowired
    LivreServiceClient livreServiceClient;

    @Autowired
    ReservationServiceClient reservationServiceClient;

    @GetMapping("/client/admin")
    public String afficherPageAdmin(HttpSession session, Model model, String message){

        String token = session.getAttribute("token").toString();
        String displayMessage = null;
        if ( message !=null){
        switch (message){
            case "batchEmprunt" :
                displayMessage = "Batch lancé sur les emprunts échus : voir le fichier text généré";
                break;
            case "batchResa" :
                displayMessage = "Batch lancé sur les réservations échues : voir les logs !";
                break;
            case "emprunt" :
                displayMessage = "Emprunt créé avec succès";
                break;
            case "empruntStop" :
                displayMessage = "Emprunt arrêté";
                break;
            default :
                displayMessage = null;
        }}

        //on charge la liste des emprunts en cours
        List<Emprunt> empruntsEnCours = empruntServiceClient.listeEmpruntsEncours(token);

        //on charge la liste des résa en cours
        List<Reservation> resaEncours = reservationServiceClient.listeReservationsEnCours(token);

        model.addAttribute("resaEncours", resaEncours);
        model.addAttribute("empruntsEncours", empruntsEnCours);
        model.addAttribute("creationEmprunt", new CreationEmprunt());
        model.addAttribute("listeMembre", membreServiceClient.listeMembres(token));
        model.addAttribute("listeLivresDispo", livreServiceClient.listeLivresDisponibles(token));
        model.addAttribute("displayMessage", displayMessage);

        return "admin";
    }


    @PostMapping("/client/creerEmprunt")
    public String creerEmprunt(@ModelAttribute CreationEmprunt creationEmprunt, HttpSession session, Model model){

        String token = session.getAttribute("token").toString();
        empruntServiceClient.creerEmprunt(token, creationEmprunt);

        return "redirect:/client/admin?message=emprunt";
    }

    @GetMapping("/client/stopperEmprunt")
    public String stopperEmprunt(Model model, String empruntId, HttpSession session){

        String token = session.getAttribute("token").toString();
        try{
            Livre livreRendu = empruntServiceClient.livreRendu(token, Integer.parseInt(empruntId));
            String message = "Le livre " + livreRendu.getTitre() + " a bien été restitué, pensez à le ranger dans la bibliothèque !";
            model.addAttribute("livreRendu", livreRendu);
            model.addAttribute("message", message);
        }

         catch (Exception e){
            logger.error("Erreur :" + e);
            return null;

        }
        return "redirect:/client/admin?message=empruntStop";


    }

    @GetMapping("/client/admin/batchEmprunt")
    public String lancerBatchEmprunt(HttpSession session){
        String token = session.getAttribute("token").toString();
        empruntServiceClient.lancerBatchEmprunt(token);
        return "redirect:/client/admin?message=batchEmprunt";
    }

    @GetMapping("/client/admin/batchResa")
    public String lancerBatchResa(HttpSession session){
        String token = session.getAttribute("token").toString();
        reservationServiceClient.purgerListeResa(token);
        return "redirect:/client/admin?message=batchResa";
    }



}
