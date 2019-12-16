package com.biblioP7.dao;


import com.biblioP7.beans.Livre;
import com.biblioP7.beans.Membre;
import com.biblioP7.beans.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ReservationDao extends JpaRepository<Reservation, Integer> {

    Reservation findById(int id);

    List<Reservation> findAllByMembre(Membre membre);

    List<Reservation> findAllByMembreOrderById(Membre membre);

    @Query("Select reservations from Reservation  reservations Where reservations.isEncours = true and reservations.dateFin <?1")
    List<Reservation> listerResaExpiree(Date date);


    @Query("Select reservations from Reservation  reservations Where reservations.isEncours = true and reservations.livre =?1 order by  reservations.id")
    List<Reservation> trouverResaEncoursParLivre(Livre livre);

    @Query("SELECT reservations FROM Reservation reservations WHERE reservations.isEncours =?1 ORDER BY reservations.id")
    List<Reservation> findReservationsEncours(boolean isEncours);

    @Query("Select reservations from Reservation  reservations Where reservations.isEncours = true and reservations.membre =?1 order by  reservations.id")
    List<Reservation> trouverResaEncoursParMembre(Membre membre);


}
