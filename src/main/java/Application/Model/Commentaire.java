package Application.Model;

public class Commentaire {
    private int id;
    private String message;
    public Commentaire(int id, String message) {
        this.id = id;
        this.message = message;
    }
    public Commentaire(int id) {
        this.id = id;
    }
    public Commentaire(String message) {this.message=message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                '}';
    }
}
