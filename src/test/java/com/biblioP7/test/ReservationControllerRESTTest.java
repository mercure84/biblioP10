package com.biblioP7.test;

import com.biblioP7.beans.Emprunt;
import com.biblioP7.beans.Livre;
import com.biblioP7.beans.Membre;
import com.biblioP7.beans.Reservation;
import com.biblioP7.dao.EmpruntDao;
import com.biblioP7.dao.ReservationDao;
import com.biblioP7.exception.ReservationException;
import com.biblioP7.restControllers.ReservationControllerREST;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ReservationControllerRESTTest {

    @InjectMocks
    ReservationControllerREST reservationControllerREST;

    @Mock
    ReservationDao reservationDao;

    @Mock
    EmpruntDao empruntDao;


    @Test
    public void listeReservationTest(){

        Reservation reservation1 = new Reservation();
        reservationDao.save(reservation1);
        Reservation reservation2 = new Reservation();
        reservationDao.save(reservation2);
        Reservation reservation3 = new Reservation();
        reservationDao.save(reservation3);
        Reservation reservation4 = new Reservation();
        reservationDao.save(reservation4);

        List<Reservation> listeReservation = new ArrayList<>();
        listeReservation.add(reservation1);
        listeReservation.add(reservation2);
        listeReservation.add(reservation3);
        listeReservation.add(reservation4);
        when(reservationDao.findReservationsEncours(true)).thenReturn(listeReservation);
        List<Reservation> resultat = reservationControllerREST.listeReservationsEnCours();
        System.out.println("Reservations attendues = "+ resultat.size());
        assertThat(resultat.size()).isEqualTo(4);

    }

// test qui doit montrer que la résa est impossible car livre déjà trop réservé
    @Test(expected = ReservationException.class)
    public void creerReservationTestFail(){
        Reservation reservation = new Reservation();
        Livre livre = new Livre();
        livre.setStockTotal(2);
        reservation.setLivre(livre);
        List<Reservation> listeResaMock = new ArrayList<>();
        listeResaMock.add(new Reservation());
        listeResaMock.add(new Reservation());
        listeResaMock.add(new Reservation());
        listeResaMock.add(new Reservation());
        listeResaMock.add(new Reservation());
        listeResaMock.add(new Reservation());
        when(reservationDao.trouverResaEncoursParLivre(reservation.getLivre())).thenReturn(listeResaMock);
        Reservation resultat = reservationControllerREST.creerReservation("token", reservation);
        System.out.println(resultat);
}
//test qui doit montrer que la résa est impossible car le membre a déjà une réservation en cours pour ce livre
    @Test(expected = ReservationException.class)
    public void creerReservationTestFailBis(){
        //on instancie la réservation demandée
        Reservation reservation = new Reservation();
        Livre livre = new Livre();
        Membre membre = new Membre();
        reservation.setLivre(livre);
        reservation.setMembre(membre);

        //on instancie une réservation déjà existante pour le même livre pour générer le cas d'exception
        Reservation reservationMembre = new Reservation();
        reservationMembre.setLivre(livre);
        reservationMembre.setMembre(membre);
        reservationMembre.setEncours(true);

        List<Reservation> listeResaExistante = new ArrayList<>();
        listeResaExistante.add(reservationMembre);

        //bypass de la première condition d'exception (trop de livres réservés)
        when(reservationDao.trouverResaEncoursParLivre(reservation.getLivre())).thenReturn(new ArrayList<Reservation>());
        when(reservationDao.trouverResaEncoursParMembre(reservation.getMembre())).thenReturn(listeResaExistante);
        Reservation resultat = reservationControllerREST.creerReservation("token", reservation);
        }

//test qui doit montrer que la résa est impossible car le membre a déjà un emprunt en cours pour ce livre
    @Test(expected = ReservationException.class)
    public void creerReservationTestFailTer(){
        //on instancie la réservation demandée
        Reservation reservation = new Reservation();
        Livre livre = new Livre();
        Membre membre = new Membre();
        reservation.setLivre(livre);
        reservation.setMembre(membre);

        //on instancie un emprunt déjà existant et en cours pour le même livre pour générer le cas d'exception
        Emprunt emprunt = new Emprunt();
        emprunt.setLivre(livre);
        emprunt.setMembre(membre);
        emprunt.setRendu(false);

        List<Emprunt> listeEmpruntsExistant = new ArrayList<>();
        listeEmpruntsExistant.add(emprunt);

        //bypass de la première condition d'exception (trop de livres réservés?)
        when(reservationDao.trouverResaEncoursParLivre(reservation.getLivre())).thenReturn(new ArrayList<Reservation>());
        //bypass de la deuxième condition d'exception (réservation en cours ?)
        when(reservationDao.trouverResaEncoursParMembre(reservation.getMembre())).thenReturn(new ArrayList<Reservation>());
        when(empruntDao.trouverEmpruntEncoursParMembre(reservation.getMembre())).thenReturn(listeEmpruntsExistant);
        Reservation resultat = reservationControllerREST.creerReservation("token", reservation);
    }

    @Test
    public void creerReservationTestSuccess(){
        //on instancie la réservation demandée
        Reservation reservation = new Reservation();
        Livre livre = new Livre();
        livre.setStockTotal(3);
        Membre membre = new Membre();
        reservation.setLivre(livre);
        reservation.setMembre(membre);
        //bypass de la première condition d'exception (trop de livres réservés?)
        when(reservationDao.trouverResaEncoursParLivre(reservation.getLivre())).thenReturn(new ArrayList<Reservation>());
        //bypass de la deuxième condition d'exception (réservation en cours ?)
        when(reservationDao.trouverResaEncoursParMembre(reservation.getMembre())).thenReturn(new ArrayList<Reservation>());
        //bypass de la 3ème condition d'exception (emprunt en cours ?)
        when(empruntDao.trouverEmpruntEncoursParMembre(reservation.getMembre())).thenReturn(new ArrayList<Emprunt>());
        Reservation resultat = reservationControllerREST.creerReservation("token", reservation);
        assertThat(resultat.toString());
    }

    }


