package com.biblioP7.beans;


import lombok.Data;

@Data
public class CreationReservation {


    private String membreId;
    private String livreId;

    public String getMembreId() {
        return membreId;
    }

    public void setMembreId(String membreId) {
        this.membreId = membreId;
    }

    public String getLivreId() {
        return livreId;
    }

    public void setLivreId(String livreId) {
        this.livreId = livreId;
    }
}
