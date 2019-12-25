package com.biblioP7.test;


import com.biblioP7.beans.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class BeansTest {

    @Test
    public void CreationEmpruntTest(){

        CreationEmprunt creationEmprunt = new CreationEmprunt();
        creationEmprunt.setLivreId("1");
        creationEmprunt.setMembreId("1");
        creationEmprunt.toString();

    }


    @Test
    public void EmpruntTest(){

        Emprunt emprunt = new Emprunt();
        emprunt.setFinDate(new Date());
        emprunt.setMembre(new Membre());
        emprunt.setLivre(new Livre());
        emprunt.setDebutDate(new Date());
        emprunt.setProlonge(false);
        emprunt.setRendu(false);
        emprunt.setId(10);
        emprunt.toString();
    }

    @Test
    public void FiltreRechercheTest(){

        FiltreRecherche filtreRecherche = new FiltreRecherche();
        filtreRecherche.setChamps("test champs");
        filtreRecherche.setType("test type");
        filtreRecherche.toString();

    }


    @Test
    public void LivreTest(){
        Livre livre = new Livre();
        livre.setIsbn("123456789");
        livre.setAuteurNom("NomAuteur Test");
        livre.setAuteurPrenom("PrenomAuteur Test");
        livre.setCollection("collection test");
        livre.setEditeur("Editeur Test");
        livre.setEtiquette("Etiquette 123456");
        livre.setTitre("Titre test");
        livre.toString();
    }


    @Test
    public void LoginFormTest(){
        LoginForm loginform = new LoginForm();
        loginform.setEmail("email@email.fr");
        loginform.setPassword("password12345678");
        loginform.toString();
    }

    @Test
    public void MembreTest(){

        Membre membre = new Membre();
        membre.setEmail("email.email@email.fr");
        membre.setNom("nomtest");
        membre.setPrenom("prenomtest");
        membre.setDateInscription(new Date());
        membre.setPortable("0606060606");
        membre.setRole("test");
        membre.toString();

    }

    @Test
    public void RegisterFormTest(){
        RegisterForm registerForm = new RegisterForm();
        registerForm.setEmail("email@email.fr");
        registerForm.setNom("nomtest");
        registerForm.setPrenom("prenomtest");
        registerForm.setPassword("passwordtest");
        registerForm.setRepassword("passwordtest");
        registerForm.toString();

    }






}
