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
public class LivreControllerREST {

    private static final Logger logger = LoggerFactory.getLogger(LivreControllerREST.class);


    @Autowired
    private LivreDao livreDao;

    @CrossOrigin("*")
    @RequestMapping(value="/api/Livre/listeLivres", method= RequestMethod.GET)
    public List<Livre> listeLivres(){
        List<Livre> livres = livreDao.findAll();
        logger.info("[REST] Affichage de la liste de livres");

        return livres;
    }

    @CrossOrigin("*")
    @RequestMapping(value="/api/Livre/listeLivresDisponibles", method= RequestMethod.GET)
    public List<Livre> listeLivresDisponibles(){
        List<Livre> livres = livreDao.findLivresByStockDisponibleGreaterThanOrderByTitre(0);
        logger.info("[REST] Affichage de la liste de livres disponibles");

        return livres;
    }



    @CrossOrigin("*")
    @RequestMapping(value="/api/Livre/nbLivres", method= RequestMethod.GET)
    public Map<String, Integer> nbLivres(){
        Map<String, Integer> resultat = new HashMap<>();
        int nbLivres = livreDao.calculerStockTotal();
        int nbLivresDispo = livreDao.calculerStockDispo();

        resultat.put("nbLivres", nbLivres);
        resultat.put("nbLivresDispo", nbLivresDispo);
        logger.info("[REST] Affichage du nombres de livres dispo :" + nbLivresDispo + " et total : " + nbLivres);

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
        List<Livre> livresDispo = livreDao.findLivresByStockDisponibleGreaterThanOrderByTitre(0);
        Random rand = new Random();
        Livre livreRandom = livresDispo.get(rand.nextInt(livresDispo.size()));
        logger.info("[REST] Un livre au hasard : " + livreRandom);

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

            logger.error("[REST] Problème dans la recherche d'un libre typeRecherche // champRecherche:"  + typeRecherche + " // " + champRecherche);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur " + e );        }
    }


    @PostMapping(value="/api/Livre/ajouterLivre")
    public void ajouterLivre(@RequestBody Livre livre){
        logger.info("[REST] Aujout d'un nouveau livre : "+ livre);
        livreDao.save(livre);
    }

}
