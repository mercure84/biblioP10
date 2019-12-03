package com.biblioP7.restControllers;


import com.biblioP7.beans.Livre;
import com.biblioP7.dao.LivreDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LivreController {

    private static final Logger logger = LoggerFactory.getLogger(LivreController.class);


    @Autowired
    private LivreDao livreDao;

    @CrossOrigin("*")
    @RequestMapping(value="/api/Livre/listeLivres", method= RequestMethod.GET)
    public List<Livre> listeLivres(){
        List<Livre> livres = livreDao.findAll();
        return livres;
    }

    @CrossOrigin("*")
    @RequestMapping(value="/api/Livre/listeLivresDisponibles", method= RequestMethod.GET)
    public List<Livre> listeLivresDisponibles(){
        List<Livre> livres = livreDao.findLivresByDisponibleIsTrueOrderById();
        return livres;
    }



    @CrossOrigin("*")
    @RequestMapping(value="/api/Livre/nbLivres", method= RequestMethod.GET)
    public Map<String, Integer> nbLivres(){
        Map<String, Integer> resultat = new HashMap<>();
        int nbLivres = livreDao.findAll().size();
        int nbLivresDispo = livreDao.findLivresByDisponibleIsTrueOrderById().size();

        resultat.put("nbLivres", nbLivres);
        resultat.put("nbLivresDispo", nbLivresDispo);

        return resultat;
    }




    @CrossOrigin("*")
    @GetMapping(value="/api/Livre/{id}")
    public Livre detailLivre(@PathVariable int id){
        return livreDao.findById(id);
    }

    @CrossOrigin("*")
    @GetMapping(value="/api/Livre/randomLivre")
    public Livre randomLivreDispo(){
        List<Livre> livresDispo = livreDao.findLivresByDisponibleIsTrueOrderById();
        Random rand = new Random();
        Livre livreRandom = livresDispo.get(rand.nextInt(livresDispo.size()));
        return livreRandom;
    }

    @CrossOrigin("*")
    @GetMapping(value="/api/Livre/filtrerLivres")
    public ResponseEntity<?> filtrerLivres(@RequestParam(name="typeRecherche") String typeRecherche, @RequestParam(name="champRecherche") String champRecherche){

        try{
        List<Livre> resultat = new ArrayList<Livre>();

        switch(typeRecherche){
            case "Titre":
                resultat  = livreDao.filtrerTitres(champRecherche);
                break;

            case "Auteur":
                resultat = livreDao.filtrerAuteurs(champRecherche);
                break;

            case "Editeur":
               resultat = livreDao.filtrerEditeurs(champRecherche);
                break;

        }

        logger.info("[REST] Une recherche a été demandée, le résultat comporte " + resultat.size() + " ouvrages !");

            return new ResponseEntity<>(resultat, HttpStatus.OK);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur " + e );        }


    }




    @PostMapping(value="/api/Livre/ajouterLivre")
    public void ajouterLivre(@RequestBody Livre livre){
        livreDao.save(livre);
    }

}
