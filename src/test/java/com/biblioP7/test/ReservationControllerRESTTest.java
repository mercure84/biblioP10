package com.biblioP7.test;

import com.biblioP7.dao.ReservationDao;
import com.biblioP7.restControllers.ReservationControllerREST;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ReservationControllerRESTTest {

    @InjectMocks
    ReservationControllerREST reservationControllerREST;

    @Mock
    ReservationDao reservationDao;

    @Test
    public void creerReservation() {


    }

}