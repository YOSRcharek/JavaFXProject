package Dons.entities;

public class Typedons {
    private int id;
    private String name;

    public Typedons(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Typedons() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
