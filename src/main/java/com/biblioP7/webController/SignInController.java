package com.biblioP7.webController;


import com.biblioP7.beans.LoginForm;
import com.biblioP7.beans.Membre;
import com.biblioP7.feignClient.MembreServiceClient;

import com.biblioP7.security.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.sql.SQLOutput;


@Controller
@SessionAttributes("tokenJWT")
public class SignInController {


    @Autowired
    MembreServiceClient membreServiceClient;


    @GetMapping("/client/signIn")
    public String signInPage(Model model, String connexion) {
        model.addAttribute("login", new LoginForm());
        try {
            if (connexion.equals("erreur")) {
                model.addAttribute("connexion", "nOK");
            }
        } catch (Exception e) {

        }
        return "signIn";
    }


    @PostMapping("/client/signIn")
    public String seConnecter(@ModelAttribute LoginForm login, Model model, HttpSession session) {

        try {

            // interrogation du WS gérant l'authentification
            JwtRequest jwtRequest = new JwtRequest();
            jwtRequest.setPassword(login.getPassword());
            jwtRequest.setUsername(login.getEmail());
            ResponseEntity reponseAuth = membreServiceClient.login(jwtRequest);

            if (reponseAuth.getStatusCode().value() == 200) {

                // récupération du token si la connexion est OK
                String token = reponseAuth.getBody().toString();
                String tokenJWT = "Bearer " + token.substring(7,token.length()-1);
                System.out.println("Token officiel = " + tokenJWT);

                // on en profite pour récupérer les infos du membre (nom / prenom) avec notre nouveau kikoo token

                Membre membre = membreServiceClient.dataMembre(tokenJWT, login.getEmail());
                System.out.println("Membre connecté = " + membre);
                //on stocke tout dans la session
                session.setAttribute("token", tokenJWT);
                session.setAttribute("membreNom", membre.getNom());
                session.setAttribute("membrePrenom", membre.getPrenom());
                session.setAttribute("membreEmail", membre.getEmail());
                session.setAttribute("membreId", membre.getId());
                session.setAttribute("membre", membre);
                session.setAttribute("membreRole", membre.getRole());

                return "redirect:/client";

            } else {
                return "redirect:/client/signIn?connexion=erreur";
            }

        } catch (Exception e) {
            System.out.println("Erreur : " + e);
            return "redirect:/client/signIn?connexion=erreur";

        }


    }


}
