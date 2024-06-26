package demo.model;

public class TypeEvent {
    private int id;
    private String nom;

    public TypeEvent() {

    }

    public TypeEvent(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    public TypeEvent(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "TypeEvent{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }


}
