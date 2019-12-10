package com.biblioP7.dao;


import com.biblioP7.beans.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservationDao extends JpaRepository<Reservation, Integer> {

    Reservation findById(int id);

    @Query("SELECT reservations FROM Reservation reservations WHERE reservations.isEnCours =?1 ORDER BY reservations.id")
    List<Reservation> findReservationsEncours(boolean isRendu);

}