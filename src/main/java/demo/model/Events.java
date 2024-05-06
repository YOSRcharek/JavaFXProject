package demo.model;


import java.time.LocalDate;

public class Events {
    private int id;

    // private int associationId;
    private String nomEvent;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String localisation;
    private int capaciteMax;
    private int capaciteActuelle;
    private String image;
    private float latitude;
    private float longitude;
    private TypeEvent typeEvent;


    public Events(int id, String nomEvent, String description, LocalDate dateDebut, LocalDate dateFin, String localisation, int capaciteMax, int capaciteActuelle, String image, float v, float v1) {
        this.id=id;
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

    }
    public Events(String nomEvent, String description, LocalDate dateDebut, LocalDate dateFin, String localisation, int capaciteMax, String image) {

        this.nomEvent = nomEvent;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.localisation = localisation;
        this.capaciteMax = capaciteMax;
        this.capaciteActuelle = 0;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.typeEvent = typeEvent;
    }
    public Events(int id, int typeId, String nomEvent, String description, LocalDate dateDebut, LocalDate dateFin, String localisation, int capaciteMax, int capaciteActuelle, String image, float latitude, float longitude, TypeEvent typeEvent) {
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
    public Events(String nomEvent, String description, LocalDate dateDebut, LocalDate dateFin, String localisation, int capaciteMax, String image, float latitude, float longitude) {
        this.nomEvent = nomEvent;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.localisation = localisation;
        this.capaciteMax = capaciteMax;
        this.capaciteActuelle = 0; // Vous pouvez initialiser la capacité actuelle à 0 si nécessaire
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
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