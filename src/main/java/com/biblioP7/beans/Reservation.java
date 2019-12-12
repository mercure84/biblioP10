package com.biblioP7.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name= "reservation")
@AllArgsConstructor @NoArgsConstructor
public class Reservation {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable= false, unique = true)
    private int id;

    @Column(name="date_demande")
    private Date dateDemande;

    @Column(name= "date_debut")
    private Date dateDebut;

    @Column(name= "date_fin")
    private Date dateFin;

    @ManyToOne()
    @JoinColumn(name="membre_id")
    private Membre membre;

    @ManyToOne()
    @JoinColumn(name="livre_id")
    private Livre livre;

    @Column(name= "en_cours")
    private boolean isEncours;

    @Column(name="detail")
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }



    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public boolean isEncours() {
        return isEncours;
    }

    public void setEncours(boolean encours) {
        isEncours = encours;
    }
}
