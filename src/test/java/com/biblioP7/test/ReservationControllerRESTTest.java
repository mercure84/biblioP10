package com.biblioP7.test;

import com.biblioP7.beans.Livre;
import com.biblioP7.beans.Reservation;
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
    public void creerReservationTest(){
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
}}