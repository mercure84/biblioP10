package com.biblioP7.dao;

import com.biblioP7.beans.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LivreDao extends JpaRepository<Livre, Integer>{

    Livre findById(int it);

    List<Livre> findLivresByStockDisponibleGreaterThan(int mini);


    // requêtes de calculs

    @Query("Select sum(c.stockTotal) from Livre c")
    int calculerStockTotal();


    @Query("Select sum(c.stockDisponible) from Livre c")
    int calculerStockDispo();






    // requetes pour la recherche

    @Query("Select c from Livre c where lower(unaccent(c.titre)) like ('%' || lower(unaccent(:titre)) || '%')")
    List<Livre> filtrerTitres(@Param("titre") String titre);

    @Query("Select c from Livre c where lower(unaccent(c.auteurNom)) like ('%' || lower(unaccent(:auteurNom)) || '%')")
    List<Livre> filtrerAuteurs(@Param("auteurNom") String auteurNom);

    @Query("Select c from Livre c where lower(unaccent(c.editeur)) like ('%' || lower(unaccent(:editeur)) || '%')")
    List<Livre> filtrerEditeurs(@Param("editeur") String editeur);




}
