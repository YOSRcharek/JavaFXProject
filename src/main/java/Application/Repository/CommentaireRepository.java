package Application.Repository;


import Application.Database.Databaseconnection;
import Application.Model.Association;
import Application.Model.Categorie;
import Application.Model.Commentaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentaireRepository implements Icategorie<Commentaire> {

    private  final Connection cnx;

    public CommentaireRepository() throws SQLException {
        cnx = Databaseconnection.getInstance().getConnection();
    }

    public List<Commentaire> getAllCommentaires() {
        List<Commentaire> commentaires = new ArrayList<>();
        String sql = "SELECT * FROM commentaire";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String message = resultSet.getString("message");

                Commentaire commentaire = new Commentaire(id, message);
                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des commentaires depuis la base de données.", e);
        }

        return commentaires;
    }


    public List<String> getAllCommentaireMessages() {
        List<String> messages = new ArrayList<>();
        String sql = "SELECT message FROM commentaire";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String message = resultSet.getString("message");
                messages.add(message);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des messages de commentaire.", e);
        }

        return messages;
    }
    public Commentaire getCommentaireById(int id) {
        String sql = "SELECT * FROM commentaire WHERE id = ?";
        Commentaire commentaire = null;

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String message = resultSet.getString("message");
                    commentaire = new Commentaire(id, message);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du commentaire depuis la base de données.", e);
        }

        return commentaire;
    }
    public Commentaire getCommentaireByNom(String nom) {
        String sql = "SELECT * FROM commentaire WHERE message = ?";
        Commentaire commentaire = null;

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, nom);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String message = resultSet.getString("message");
                    commentaire = new Commentaire(id, message);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du commentaire depuis la base de données.", e);
        }

        return commentaire;
    }





    @Override
    public void update(Categorie categorie, int id) {

    }

    @Override
    public Association getassocaitionbyid(int id) {
        return null;
    }

    @Override
    public <T2> List<T2> getAllassociation() {
        return null;
    }

    @Override
    public Association getAssociationById(int id) {
        return null;
    }

    @Override
    public <T> void add(T t) throws SQLException {

    }

    @Override
    public <T> void update(T t, int id) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public <T> List<T> getAll() {
        return null;
    }

    @Override
    public <T> T getbyid(int id) {
        return null;
    }
}
