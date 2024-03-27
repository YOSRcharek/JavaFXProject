package com.example.application.model;

import java.util.Date;

public class Events {
    private int id;

    // private int associationId;
    private String nomEvent;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private String localisation;
    private int capaciteMax;
    private int capaciteActuelle;
    private String image;
    private float latitude;
    private float longitude;
    private TypeEvent typeEvent;


    public Events(int id, String nomEvent, String description, Date dateDebut, Date dateFin, String localisation, int capaciteMax, int capaciteActuelle, String image, float latitude, float longitude) {
        this.id = id;
        this.nomEvent = nomEvent;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.localisation = localisation;
        this.capaciteMax = capaciteMax;
        this.capaciteActuelle = capaciteActuelle;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.typeEvent = typeEvent;
    }

    public Events(int id, int typeId, int associationId, String nomEvent, String description, Date dateDebut, Date dateFin, String localisation, int capaciteMax, int capaciteActuelle, String image, String latitude, String longitude) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

 /*   public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getAssociationId() {
        return associationId;
    }

    public void setAssociationId(int associationId) {
        this.associationId = associationId;
    }*/

    public String getNomEvent() {
        return nomEvent;
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public int getCapaciteActuelle() {
        return capaciteActuelle;
    }

    public void setCapaciteActuelle(int capaciteActuelle) {
        this.capaciteActuelle = capaciteActuelle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public TypeEvent getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(TypeEvent typeEvent) {
        this.typeEvent = typeEvent;
    }


    @Override
    public String toString() {
        return "Events{" +
                "id=" + id +
                ", nomEvent='" + nomEvent + '\'' +
                ", description='" + description + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", localisation='" + localisation + '\'' +
                ", capaciteMax=" + capaciteMax +
                ", capaciteActuelle=" + capaciteActuelle +
                ", image='" + image + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", typeEvent=" + typeEvent +
                '}';
    }
}
