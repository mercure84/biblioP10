package com.biblioP7.restControllers;
import com.biblioP7.beans.*;
import com.biblioP7.dao.EmpruntDao;
import com.biblioP7.dao.LivreDao;
import com.biblioP7.dao.MembreDao;
import com.biblioP7.dao.ReservationDao;
import com.biblioP7.security.JwtTokenUtil;
import com.biblioP7.security.UserDetailsServiceImpl;
import com.biblioP7.serviceMail.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class EmpruntControllerREST {

    private static final Logger logger = LoggerFactory.getLogger(EmpruntControllerREST.class);


    @Autowired
    private EmailService emailService;

    @Autowired
    private EmpruntDao empruntDao;

    @Autowired
    private MembreDao membreDao;

    @Autowired
    private LivreDao livreDao ;

    @Autowired
    private ReservationDao reservationDao;


    // on va utiliser cette classe utilitaire pour parser le token reçu, notamment pour la méthode prolongerEmprunt
    // et vérifier que c'est bien le bon membre qui prolonge son propre emprunt et pas celui du voisin
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private UserDetailsServiceImpl userDetails;



    @GetMapping(value="/api/listeEmprunts")
    public List<Emprunt> listeEmprunts(){
        List<Emprunt> emprunts = empruntDao.findAll();
        logger.info("[REST] Demande d'une liste d'emprunt");
        return emprunts;
    }

    @GetMapping(value="/api/listeEmpruntsEncours")
    public List<Emprunt> listeEmpruntsEncours(){
        List<Emprunt> emprunts = empruntDao.findEmpruntsEncours(false);
        logger.info("[REST] Demande d'une liste d'emprunt en cours");

        return emprunts;
    }

    @GetMapping(value="/api/Emprunt/{id}")
    public Emprunt detailEMprunt(@PathVariable int id){

        logger.info("[REST] Demande du détail de l'emprunt n° " + id);

        return empruntDao.findById(id);
    }


    @RequestMapping(value="/api/EmpruntsMembre/{membreId}")
    public List<Emprunt> empruntsParMembre(@PathVariable int membreId){
        Membre membre = membreDao.findById(membreId);

        List<Emprunt> listeEmprunts = empruntDao.findEmpruntsByMembre(membre);

        logger.info("[REST] Une liste d'emprunt est demandée pour le membre " + membre.getEmail());

        return listeEmprunts;
    }



    @PostMapping(value="/api/creerEmprunt")
    public Emprunt creerEmprunt(@RequestBody CreationEmprunt creationEmprunt){

        System.out.println("dataJSON = " + creationEmprunt);
        int membreId = Integer.parseInt(creationEmprunt.getMembreId());
        int livreId = Integer.parseInt(creationEmprunt.getLivreId());

        System.out.println("livreId = " + livreId + "membreId = " + membreId) ;

        Date dateDebut = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime((dateDebut));
        c.add(Calendar.DATE, 28);
        Date dateFin = c.getTime();
        System.out.println("date début = " + dateDebut +", date de fin = " + dateFin);

        Membre membre = membreDao.findById(membreId);
        Livre livre = livreDao.findById(livreId);

        if (livre.getStockDisponible()==0){
            //le livre n'est pas disponible, on ne peut pas l'emprunter !
            logger.error("[REST] Impossible de créer l'emprunt : Membre =" + membre.getEmail() + " Livre =" + livre.getId() );
            return null;

        }

        else {

            Emprunt nouvelEmprunt = new Emprunt();

            nouvelEmprunt.setDebutDate(dateDebut);
            nouvelEmprunt.setFinDate(dateFin);
            nouvelEmprunt.setLivre(livre);
            nouvelEmprunt.setMembre(membre);

            empruntDao.save(nouvelEmprunt);

            //l'emprunt est validé, on sauvegarde le livre en diminuant son stock de 1
            livre.emprunterLivre();
            livreDao.save(livre);
            logger.info(" [REST] Nouvel emprunt créé id = " + nouvelEmprunt.getId() + " membre = " + nouvelEmprunt.getMembre().getNom() + " livre = " + nouvelEmprunt.getLivre().getTitre() );
            return nouvelEmprunt;
        }

    }

    @RequestMapping(value="/api/prolongerEmprunt/{id}", method= RequestMethod.GET)
    public Emprunt prolongerEmprunt(@PathVariable int id, @RequestHeader("Authorization") String token){

        token = token.substring(7);
        Emprunt empruntAProlonger = empruntDao.findById(id);
        // on recherche le membre Userdetails concerné par l'emprunt à prolonger :

        String emailMembre = empruntAProlonger.getMembre().getEmail();

        UserDetails user = userDetails.loadUserByUsername(emailMembre);


        //on regarde si le token reçu dans la requête correspond bien au membre
        boolean isTokenValide = jwtTokenUtil.validateToken(token, user);

        // on traite la requête uniquement si le token a été validé

        if (isTokenValide){

// CONTROLE BACKEND DE LA POSSIBILITE DE PROLONGER LEMPRUNT

        if(empruntAProlonger.isProlonge() || empruntAProlonger.getFinDate().before(new Date()) || empruntAProlonger.isRendu()){
            logger.error("[REST] impossible de prolonger l'emprunt " + empruntAProlonger.getId());
            return null;


        } else {
            empruntAProlonger.setProlonge(true);
            //ajout de 28 jours à dateFin
            Date dateFin = empruntAProlonger.getFinDate();
            Calendar c = Calendar.getInstance();
            c.setTime(dateFin);
            c.add(Calendar.DATE, 28);
            Date dateFinBis = c.getTime();

            empruntAProlonger.setFinDate(dateFinBis);
            empruntDao.save(empruntAProlonger);
            logger.info("[REST] L'emprunt n° " + empruntAProlonger.getId() + " a bien été prolongé !");
            return empruntAProlonger;
        }}
        else {

            return null;
        }
    }

    @RequestMapping(value="/api/stopperEmprunt/{id}")
    public Livre livreRendu(@PathVariable int id){

        //on tope l'emprunt à "rendu true" + modif de la date de fin
        Emprunt emprunt = empruntDao.findById(id);
        emprunt.setFinDate(new Date());
        emprunt.setRendu(true);
        empruntDao.save(emprunt);
        logger.info("[REST] Arrêt de l'emprunt " + emprunt);

        //RG un livre est rendu, avant de le rentrer en stock on regarde si une réservation peut être servie
        Livre livreRendu = livreDao.findById(emprunt.getLivre().getId());

        List<Reservation> listeResaLivre = reservationDao.trouverResaEncoursParLivre(livreRendu);

        // si on trouve une liste de réservation sur le livre on va parcourir cette liste et opérer les traitements adequats
        boolean absenceNewOption = true;
        if (listeResaLivre.size() > 0){
            //on parcours la liste des réservation qui est ordonnée par les ID
            for (int i = 0 ; i < listeResaLivre.size() ; i++){
                if ((listeResaLivre.get(i).isEncours()) && (listeResaLivre.get(i).getDateDebut() == null)){
                    //si on est ici c'est qu'on a attrapé une résa en cours qui n'a pas encore commencée !!
                    Reservation premiereResa = listeResaLivre.get(i) ;
                    premiereResa.setDetail("Livre Disponible");
                    // on met une date de fin à la résa (+48h)
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(Calendar.DATE, 2);
                    Date dateFin = c.getTime();
                    premiereResa.setDateFin(dateFin);
                    premiereResa.setDateDebut(new Date());
                    // on informe le membre par mail :
                    Membre premierMembre = premiereResa.getMembre();
                    Livre livre = premiereResa.getLivre();
                    logger.info("[REST] Envoi d'un mail sur la résa n° : " + premiereResa.getId() + " " + premierMembre.getEmail());

                    String mailResa = "Cher " + premierMembre.getPrenom() + " " + premierMembre.getNom() + ", vous attendiez le livre " +
                            livre.getTitre() + " depuis "+ premiereResa.getDateDemande() + ", le voici disponible pour 48h à votre bibliothèque préférée !! Ne tardez pas à venir le chercher.";
                    emailService.sendMail("julien.marcesse@gmail.com", "resaP10", mailResa);

                    // on sauvegarde la résa en persistance !
                    reservationDao.save(premiereResa);
                    absenceNewOption = false;
                    // on sort de notre boucle for
                    break;
                }
            }

            }

        if (absenceNewOption){

           //on remplit le stock du livre +1
            logger.info("[REST] Rentrée en stock du libre " + livreRendu.getId());
            livreRendu.restituerLivre();
            livreDao.save(livreRendu);
        }
        return livreRendu;
    }


    @RequestMapping(value="/api/listeEmpruntsExpires", method= RequestMethod.GET)
    public List<Emprunt> empruntsExpires (){
        Date today = new Date();
        List<Emprunt> emprunts = empruntDao.findEmpruntsExpires(false, today);
        logger.info("[REST] Demande de la liste des emprunts expirés");

        return emprunts;
    }


//    IMPLEMENTATION DU BATCH DE RELANCE ==> création d'un fichier avec la liste des mails à envoyer !

    @RequestMapping(value="/api/runBatch", method=RequestMethod.GET)
    public String batch(){
        String resultat = null;
        try {

            List<Emprunt> empruntsEchusEncours = empruntDao.findEmpruntsExpires(false, new Date());

            List<String> messages = new ArrayList<String>();

            if (empruntsEchusEncours.size() == 0){
                messages.add("AUCUN MEMBRE A RELANCER :)");
            } else {
                for(int i = 0; i<empruntsEchusEncours.size(); i++){

                    messages.add(genererMail(empruntsEchusEncours.get(i)));

                }
            }

            fichierMails(empruntsEchusEncours, messages);
            emailService.sendMail("julien.marcesse@gmail.com", "mailP10", messages.toString());
            resultat = "Le batch a été exécuté sans erreur, vous pouvez consulter le fichier de retour dans le dossier habituel";
                    } catch(Exception error){
            resultat = "Le batch a échoué !" + error;
        }
        logger.info("[REST] Traitement du batch des emprunts échus");
        return resultat;
    }


    private String genererMail(Emprunt emprunt) {
        Membre membre = emprunt.getMembre();
        Livre livre = emprunt.getLivre();
        String texte = "\n[SEND TO :" + membre.getEmail() + "] \n"
                +"Bonjour " + membre.getPrenom() + membre.getNom() + ", \n"+
                "Votre bibliothèque attend le retour du livre : " + livre.getTitre() + ", que vous avez emprunté en date du " + emprunt.getDebutDate() + ". \n" +
                "Ce livre aurait du être restitué avant le " + emprunt.getFinDate() + ". \n" +
                "Nous vous prions de nous le rapporter dans les meilleurs délais pour que d'autres lecteurs puissent emprunter cet ouvrage. \n" +
                "En vous remerciant pour votre compréhension. \n" + "Votre bibliothécaire préféré. \n";


        return texte;
    }


    private void fichierMails(List<Emprunt> empruntsEchus, List<String> messages) throws IOException {

        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(new Date());
            FileWriter writer = new FileWriter("mails"+dateString+".txt");
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(empruntsEchus.toString());
            bw.write(messages.toString());
            bw.close();

        } catch (IOException error){
            logger.error("[REST] problème dans la génération des mails auto " + error);
        }

    }


}
