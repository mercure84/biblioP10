package com.biblioP7.dao;

import com.biblioP7.beans.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MembreDao extends JpaRepository<Membre, Integer> {

    Membre findById(int id);

    Membre findByEmail(String email);

    List<Membre> findAllByOrderByIdAsc();


}
