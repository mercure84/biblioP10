package com.biblioP7.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="reservation")
@AllArgsConstructor @NoArgsConstructor
public class Reservation {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable= false, unique = true)
    private int id;

    @Column(name="debut_date")
    private Date debutDate;

    @Column(name="fin_date")
    private Date finDate;

    @ManyToOne()
    @JoinColumn(name="membre_id")
    private Membre membre;

    @ManyToOne()
    @JoinColumn(name="livre_id")
    private Livre livre;

    @Column(name="en_cours")
    private boolean isEnCours;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDebutDate() {
        return debutDate;
    }

    public void setDebutDate(Date debutDate) {
        this.debutDate = debutDate;
    }

    public Date getFinDate() {
        return finDate;
    }

    public void setFinDate(Date finDate) {
        this.finDate = finDate;
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

    public boolean isEnCours() {
        return isEnCours;
    }

    public void setEnCours(boolean enCours) {
        isEnCours = enCours;
    }
}
