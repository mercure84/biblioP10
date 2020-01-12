package com.biblioP7.test;

import com.biblioP7.beans.Emprunt;
import com.biblioP7.dao.EmpruntDao;
import com.biblioP7.restControllers.EmpruntControllerREST;
import org.junit.Before;
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
public class EmpruntControllerRESTTest {

    @InjectMocks
    EmpruntControllerREST empruntControllerREST;

    @Mock
    EmpruntDao empruntDao;

    @Test
    public void listeEmpruntsTest() {

        Emprunt emprunt1 = new Emprunt ();
        empruntDao.save(emprunt1);
        Emprunt emprunt2 = new Emprunt ();
        empruntDao.save(emprunt2);
        Emprunt emprunt3 = new Emprunt ();
        empruntDao.save(emprunt3);
        List<Emprunt> listeEmprunt = new ArrayList<>();
        listeEmprunt.add(emprunt1);
        listeEmprunt.add(emprunt2);
        listeEmprunt.add(emprunt3);
        when(empruntDao.findAll()).thenReturn(listeEmprunt);
        List<Emprunt> resultat = empruntControllerREST.listeEmprunts();
        System.out.println("Emprunts attendus = " + resultat.size());
        assertThat(resultat.size()).isEqualTo(3);
        }

    @Test
    public void listeEmpruntsTestBis() {

        Emprunt emprunt1 = new Emprunt ();
        List<Emprunt> listeEmprunt = new ArrayList<>();
        listeEmprunt.add(emprunt1);
        when(empruntDao.findAll()).thenReturn(listeEmprunt);
        List<Emprunt> resultat = empruntControllerREST.listeEmprunts();
        System.out.println("Emprunts attendus = " + resultat.size());
        assertThat(resultat.size()).isLessThan(3);
    }


    @Test
    public void listeEmpruntsEncoursTest() {
        Emprunt emprunt1 = new Emprunt ();
        emprunt1.setRendu(false);
        empruntDao.save(emprunt1);
        Emprunt emprunt2 = new Emprunt ();
        emprunt2.setRendu(true);
        empruntDao.save(emprunt2);
        Emprunt emprunt3 = new Emprunt ();
        emprunt3.setRendu(true);
        empruntDao.save(emprunt3);
        List<Emprunt> listeEmpruntEncours = new ArrayList<>();
        listeEmpruntEncours.add(emprunt1);
        when(empruntDao.findEmpruntsEncours(false)).thenReturn(listeEmpruntEncours);
        List<Emprunt> resultat = empruntControllerREST.listeEmpruntsEncours();
        assertThat(resultat.size()).isEqualTo(1);
    }

}