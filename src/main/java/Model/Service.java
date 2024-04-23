package Model;

public class Service {
    private int id;
    private String nom_service;
    private String description;
    private boolean disponibilite;
    private int categorie_id;
    private int commentaire_id;

    public Service( int categorie_id,  int association_id, int commentaire_id,String nom_service,String description,boolean disponibilite) {
        this.nom_service = nom_service;
        this.description = description;
        this.disponibilite = disponibilite;
        this.categorie_id = categorie_id;
        this.commentaire_id = commentaire_id;
        this.association_id = association_id;
    }

    private int association_id;
    public Service(int id, String nom_service, String description, boolean disponibilite, int categorie_id, int commntaire_id, int association_id) {
        this.id = id;
        this.nom_service = nom_service;
        this.description = description;
        this.disponibilite = disponibilite;
        this.categorie_id = categorie_id;
        this.commentaire_id = commntaire_id;
        this.association_id = association_id;
    }
    public Service(String nom_service, String description, boolean disponibilite) {
        this.nom_service = nom_service;
        this.description = description;
        this.disponibilite = disponibilite;
    }

    public Service(String nomService, String description, boolean disponibilite, boolean categorieId, int commentaireId) {
    }

    public Service(int id, String nomService, String description, boolean disponibilite, int categorieId, int commentaireId) {
    }

    public Service() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_service() {
        return nom_service;
    }

    public void setNomservice(String nomservice) {
        this.nom_service = nomservice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(boolean disponibilite) {
        this.disponibilite= disponibilite;
    }

    public int getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(int categorie_id) {
        this.categorie_id = categorie_id;
    }

    public int getCommentaire_id() {
        return commentaire_id;
    }

    public void setCommentaire_id(int commntaire_id) {
        this.commentaire_id = commntaire_id;
    }

    public int getAssociation_id() {
        return association_id;
    }

    public void setAssociation_id(int association_id) {
        this.association_id = association_id;
    }
    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", nom_service='" + nom_service + '\'' +
                ", description='" + description + '\'' +
                ", disponibilite=" + disponibilite +
                ", categorie_id=" + categorie_id +
                ", commentaire_id=" + commentaire_id +
                ", association_id=" + association_id +
                '}';
    }

    public boolean getDisponibilite() {
        return false;
    }

    public String getName() {
        return null;
    }




    public Object getNom_categorie() {
        return null;
    }

    public void getNomAssociation() {
    }

    public void getContenuCommentaire() {
    }

    public String getNomCategorie() {
        return null;
    }
}


