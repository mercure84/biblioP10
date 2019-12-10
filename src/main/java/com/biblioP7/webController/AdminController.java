package com.biblioP7.webController;


import com.biblioP7.beans.CreationEmprunt;
import com.biblioP7.beans.Emprunt;
import com.biblioP7.beans.Livre;
import com.biblioP7.feignClient.EmpruntServiceClient;
import com.biblioP7.feignClient.LivreServiceClient;
import com.biblioP7.feignClient.MembreServiceClient;
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

    @GetMapping("/client/admin")
    public String afficherPageAdmin(HttpSession session, Model model, String batchMessage){

        String token = session.getAttribute("token").toString();

        if(batchMessage!=null){
            model.addAttribute("batchMessage", true);}
        else {
            model.addAttribute("batchMessage", false);}

        //on charge la liste des emprunts en cours
        List<Emprunt> empruntsEnCours = empruntServiceClient.listeEmpruntsEncours(token);
        model.addAttribute("empruntsEncours", empruntsEnCours);
        model.addAttribute("creationEmprunt", new CreationEmprunt());
        model.addAttribute("listeMembre", membreServiceClient.listeMembres(token));
        model.addAttribute("listeLivresDispo", livreServiceClient.listeLivresDisponibles(token) );

        return "admin";
    }


    @PostMapping("/client/creerEmprunt")
    public String creerEmprunt(@ModelAttribute CreationEmprunt creationEmprunt, HttpSession session, Model model){

        String token = session.getAttribute("token").toString();
        empruntServiceClient.creerEmprunt(token, creationEmprunt);
        String message = "L'emprunt a bien été créé !";
        model.addAttribute("message", message);


        return "admin";
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
        return "admin";


    }

    @GetMapping("/client/admin/batch")
    public String lancerBatch(HttpSession session){
        String token = session.getAttribute("token").toString();

        empruntServiceClient.lancerBatch(token);

        return "redirect:/client/admin?batchMessage=oui";
    }



}
