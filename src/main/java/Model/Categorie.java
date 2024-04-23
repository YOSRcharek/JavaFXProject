package Model;

public class Categorie {
    private int id;
    private String nom_categorie;

    public Categorie(int id, String nom_categorie) {
        this.id = id;
        this.nom_categorie = nom_categorie;
    }

    public Categorie(String nom_categorie) {
        this.nom_categorie = nom_categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_categorie() {
        return nom_categorie;
    }

    public void setNom_categorie(String nom_categorie) {
        this.nom_categorie = nom_categorie;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom_categorie='" + nom_categorie + '\'' +
                '}';
    }
}
