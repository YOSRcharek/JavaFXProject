package Application.Repository;

import Application.Database.Databaseconnection;
import Application.Model.Association;
import Application.Model.Categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.sql.Blob;

public class AssociationRepository implements Icategorie<Association> {
    private final Connection cnx;

    public AssociationRepository() throws SQLException {
        cnx = Databaseconnection.getInstance().getConnection();
    }

    @Override
    public void update(Categorie categorie, int id) {

    }

    @Override
    public Association getassocaitionbyid(int id) {
        return null;
    }

    @Override
    public <T> List<T> getAllassociation() {
        List<Association> associations = new ArrayList<>();
        String sql = "SELECT * FROM association";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String domaine_activite = resultSet.getString("domaine_activite");
                String adresse = resultSet.getString("adresse");
                String email = resultSet.getString("email");
                String description = resultSet.getString("description");
                int telephone = resultSet.getInt("telephone");
                String password = resultSet.getString("password");
                Date date_demande = resultSet.getDate("date_demande");
                Blob document = resultSet.getBlob("document");
                boolean status = resultSet.getBoolean("status");

                Association association = new Association(id, nom, password, domaine_activite, adresse, email, description, telephone, date_demande, document, status);
                associations.add(association);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (List<T>) associations;
    }


    public List<String> getAllNomAssociations() {
        List<String> associations = new ArrayList<>();
        String sql = "SELECT nom FROM association";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String nomAssociation = resultSet.getString("nom");
                associations.add(nomAssociation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return associations;
    }
    @Override
    public Association getAssociationById(int id) {
        String sql = "SELECT * FROM association WHERE id = ?";
        Association association = null;

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nom = resultSet.getString("nom");
                    String domaine_activite = resultSet.getString("domaine_activite");
                    String adresse = resultSet.getString("adresse");
                    String email = resultSet.getString("email");
                    String description = resultSet.getString("description");
                    int telephone = resultSet.getInt("telephone");
                    String password = resultSet.getString("password");
                    Date date_demande = resultSet.getDate("date_demande");
                    Blob document = resultSet.getBlob("document");
                    boolean status = resultSet.getBoolean("status");

                    association = new Association(id, nom, password, domaine_activite, adresse, email, description, telephone, date_demande, document, status);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return association;
    }
    public Association getAssociationByNom(String nomAssociation) {
        String sql = "SELECT * FROM association WHERE nom = ?";
        Association association = null;

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, nomAssociation);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    String domaine_activite = resultSet.getString("domaine_activite");
                    String adresse = resultSet.getString("adresse");
                    String email = resultSet.getString("email");
                    String description = resultSet.getString("description");
                    int telephone = resultSet.getInt("telephone");
                    String password = resultSet.getString("password");
                    Date date_demande = resultSet.getDate("date_demande");
                    Blob document = resultSet.getBlob("document");
                    boolean status = resultSet.getBoolean("status");

                    association = new Association(id, nom, password, domaine_activite, adresse, email, description, telephone, date_demande, document, status);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return association;
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
