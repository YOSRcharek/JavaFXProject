// Model Package
package Application.Model;

import java.sql.Blob;
import java.sql.Date;

public class Association {


    private int id;
    private String nom;
    private String domaine_activite;
    private String email;
    private String adresse;
    private String description;
    private Integer telephone;
    private Date date_demande;
    private boolean status;
    private Blob document;

    private String password;
    public Association(int id, String nom,String password, String domaine_activite,String adresse, String email,String description,Integer telephone, Date dateDemande,Blob document, boolean status) {
        super();
        this.id = id;
        this.nom = nom;
        this.domaine_activite = domaine_activite;
        this.email = email;
        this.date_demande = date_demande;
        this.password = password;
        this.status = status;
        this.telephone = telephone;
        this.description=description;
        this.setDocument(document);
        this.adresse=adresse;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDomaineActivite() {
        return domaine_activite;
    }

    public void setDomaineActivite(String domaine_activite) {
        this.domaine_activite = domaine_activite;
    }
    public Association(int id, String nom, String description) {
        // Initialisation des champs de l'objet Association
        this.id = id;
        this.nom = nom;
        this.description = description;

        // Initialiser d'autres champs si nécessaire
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateDemande() {
        return date_demande;
    }

    public void setDateDemande(Date date_demande) {
        this.date_demande = date_demande;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Blob getDocument() {
        return document;
    }

    public void setDocument(Blob document) {
        this.document = document;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Association{" +
                "id=" + id +
                '}';
    }
}
