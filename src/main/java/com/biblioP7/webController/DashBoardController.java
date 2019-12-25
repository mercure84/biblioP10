package com.biblioP7.webController;

import com.biblioP7.beans.Emprunt;
import com.biblioP7.beans.Membre;
import com.biblioP7.beans.ResaPosition;
import com.biblioP7.feignClient.EmpruntServiceClient;
import com.biblioP7.feignClient.MembreServiceClient;
import com.biblioP7.feignClient.ReservationServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class DashBoardController {

    @Autowired
    EmpruntServiceClient empruntServiceClient;

    @Autowired
    MembreServiceClient membreServiceClient;

    @Autowired
    ReservationServiceClient reservationServiceClient;

    @GetMapping("/client/dashboard")
    public String afficherDashBoard(Model model, HttpSession session) {

        String token = session.getAttribute("token").toString();
        String email = session.getAttribute("membreEmail").toString();
        int id = Integer.parseInt(session.getAttribute("membreId").toString());
        Date today = new Date();
        Membre membre = membreServiceClient.dataMembre(token, email);
        List<Emprunt> listeEmprunts = empruntServiceClient.empruntsParMembre(token, id);

        List<ResaPosition> listeResaPosition = reservationServiceClient.listeResaPositions(token, membre);
        model.addAttribute("listeResa", listeResaPosition);
        // on adresse la date today au front pour un contrôle sur la possibilité de prolonger l'emprunt
        model.addAttribute("today", today);
        model.addAttribute("membre", membre);
        model.addAttribute("listeEmprunts", listeEmprunts);
        return "dashboard";
    }

    @GetMapping("/client/prolongerEmprunt")
    public String prolongerEmprunt(Model model, String empruntId, HttpSession session) {
        String token = session.getAttribute("token").toString();
        Emprunt emprunt = empruntServiceClient.detailEmprunt(token, Integer.valueOf(empruntId));
        model.addAttribute("emprunt", emprunt);
        return "confirmerProlongation";
    }

    @GetMapping("/client/confirmerProlongation")
    public String confirmerProlongation(Model model, String empruntId, HttpSession session) {
        String token = session.getAttribute("token").toString();

        try {
            empruntServiceClient.prolongerEmprunt(token, Integer.valueOf(empruntId));
            return "redirect:/client/dashboard";

        } catch (Exception e) {
            return null;
        }
    }


}
