package com.biblioP7.webController;


import com.biblioP7.beans.FiltreRecherche;
import com.biblioP7.beans.Livre;
import com.biblioP7.feignClient.LivreServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RechercheLivreController {

    @Autowired
    LivreServiceClient livreServiceClient;

    @GetMapping("/client/recherche")
    public String  afficherRecherche(Model model, HttpSession session, String all) {
        model.addAttribute("filtre", new FiltreRecherche());
        model.addAttribute("afficherResultat", false);

        if(all != null && all.equals("oui")) {

            String token = session.getAttribute("token").toString();

            List<Livre> allBooks = livreServiceClient.listeLivres(token);
            model.addAttribute("allBooks", allBooks);
            model.addAttribute("afficherTout", true);
        }


        return "recherche";


        }


     @PostMapping("/client/recherche")
     public String rechercherLivre(@ModelAttribute FiltreRecherche filtre, Model model, HttpSession session){

         String token = session.getAttribute("token").toString();
         List<Livre> resultat = livreServiceClient.filtrerLivres(token, filtre.getType(), filtre.getChamps());
         model.addAttribute("resultat", resultat);
         model.addAttribute("filtre", new FiltreRecherche());
         model.addAttribute("afficherResultat", true);
         return "recherche";
     }





}
