package com.biblioP7.restControllers;


import com.biblioP7.beans.Membre;
import com.biblioP7.beans.RegisterForm;
import com.biblioP7.dao.MembreDao;
import com.biblioP7.security.EncryptedPasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MembreControllerREST {

    private static final Logger logger = LoggerFactory.getLogger(MembreControllerREST.class);



    @Autowired
    private MembreDao membreDao;

    @RequestMapping(value="/api/listeMembres", method= RequestMethod.GET)
    public List<Membre> listeMembres(){
        List<Membre> membres = membreDao.findAllByOrderByIdAsc();
        logger.info("[REST] Demande de la liste des membre");

        return membres;
    }

    @GetMapping(value="/api/Membre/data/{email}")
    public Membre dataMembre(@PathVariable String email){

        logger.info("[REST] Appel des détails d'un membre : " + email);

        return membreDao.findByEmail(email);
    }


    @GetMapping(value="/api/Membre/{id}")
    public Membre detailMembre(@PathVariable int id){

        logger.info("[REST] Appel des détails du membre " + id);

        return membreDao.findById(id);
    }


    @PostMapping(value="/api/ajouterMembre")
    public Membre ajouterMembre(@RequestBody RegisterForm userForm){
        if(!userForm.getPassword().equals(userForm.getRepassword()))
            throw new RuntimeException(("You must confirm your password"));
        Membre user = membreDao.findByEmail(userForm.getEmail());
        if(user != null) throw new RuntimeException("Utilisateur déjà enregistré !");
        Membre membre = new Membre();
        membre.setPrenom(userForm.getPrenom());
        membre.setNom(userForm.getNom());
        membre.setEmail(userForm.getEmail());
        String encodedPassword = EncryptedPasswordUtils.encryptePassword(userForm.getPassword());
        membre.setPassword(encodedPassword);
        membreDao.save(membre);
        logger.info("[REST] Un nouveau membre a été ajouté à la BDD " + membre);
        return membre;
    }

    @GetMapping(value="/api/Membre/supprimer/{id}")
    void supprimerMembre(@PathVariable int id){
        Membre membreToDetele = membreDao.findById(id);
        membreDao.delete(membreToDetele);
        logger.info("[REST] Suppression d'un membre" + membreToDetele.toString());
    }



}
