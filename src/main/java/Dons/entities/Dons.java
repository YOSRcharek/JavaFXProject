package Dons.entities;

import java.util.Date;

public class Dons {
    private int id;
    private Typedons type_id;
    private Association association_id;
    private int montant;
    private Date date_mis_don;

    // Constructeur avec tous les attributs
    public Dons(int id, Typedons type_id, Association association_id, int montant, Date date_mis_don) {
        this.id = id;
        this.type_id = type_id;
        this.association_id = association_id;
        this.montant = montant;
        this.date_mis_don = date_mis_don;
    }

    // Constructeur sans l'ID
    public Dons( int montant,Typedons type_id, Association association_id, Date date_mis_don) {
        this.montant = montant;
        this.type_id = type_id;
        this.association_id = association_id;

        this.date_mis_don = date_mis_don;
    }



    public Dons() {

    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Typedons getType_id() {
        return type_id;
    }

    public void setType_id(Typedons type_id) {
        this.type_id = type_id;
    }

    public Association getAssociation_id() {
        return association_id;
    }

    public void setAssociation_id(Association association_id) {
        this.association_id = association_id;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public Date getDate_mis_don() {
        return date_mis_don;
    }

    public void setDate_mis_don(Date date_mis_don) {
        this.date_mis_don = date_mis_don;
    }

    @Override
    public String toString() {
        return "Dons{" +
                "id=" + id +
                ", type_id=" + type_id +
                ", association_id=" + association_id +
                ", montant=" + montant +
                ", date_mis_don=" + date_mis_don +
                '}';
    }
}