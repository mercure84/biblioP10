package com.biblioP7.dao;


import com.biblioP7.beans.Emprunt;
import com.biblioP7.beans.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmpruntDao extends JpaRepository<Emprunt, Integer> {


    Emprunt findById (int id);


    @Query("SELECT emprunts FROM Emprunt emprunts WHERE emprunts.isRendu = ?1 AND emprunts.finDate < ?2")
    List<Emprunt> findEmpruntsExpires(boolean isRendu, Date today);


    List<Emprunt> findEmpruntsByMembre(Membre membre);

    @Query("SELECT emprunts FROM Emprunt emprunts WHERE emprunts.isRendu = ?1 ORDER BY emprunts.id")
    List<Emprunt> findEmpruntsEncours(boolean isRendu);
}
