package com.biblioP7.beans;

import lombok.Data;

@Data
public class FiltreRecherche {

    private String type ;
    private String champs;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChamps() {
        return champs;
    }

    public void setChamps(String champs) {
        this.champs = champs;
    }

    public FiltreRecherche() {
    }
}
