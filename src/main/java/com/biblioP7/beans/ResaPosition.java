package com.biblioP7.beans;

import lombok.Data;

// classe qui retourne un objet Reservation  et un entier correspondant à une position (position du membre dans la liste d'attente d'un livre).

@Data
public class ResaPosition {

    private Reservation reservation;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position = 0;

    public ResaPosition(Reservation reservation, int position) {
        this.reservation = reservation;
        this.position = position;
    }
}
