package com.biblioP7.webController;


import com.biblioP7.feignClient.LivreServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class MainController {

    @Autowired
    LivreServiceClient livreServiceClient;


    @GetMapping(value="navbar")
    public String afficherNavbar(HttpSession session){
        return "navbar";
        }


    @GetMapping(value="/client")
    public String index (HttpSession session, Model model){



        // on passe les stats de stock de livres à titre d'informations une fois que le membre est connecté
        Map<String, Integer> compterLivres = livreServiceClient.nbLivres();

        model.addAttribute("nbLivres", compterLivres.get("nbLivres"));
        model.addAttribute("nbLivresDispo", compterLivres.get("nbLivresDispo"));
        model.addAttribute("nbLivresEmpruntes", compterLivres.get("nbLivres") - compterLivres.get("nbLivresDispo"));

        return "index";
    }

    @GetMapping(value="/client/error")
    public String error(Model model){


        return "error";

    }

    @GetMapping(value="/client/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }



}





