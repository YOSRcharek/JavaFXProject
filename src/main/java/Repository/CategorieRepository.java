package Repository;

import Model.Categorie;
import Database.Databaseconnection;
import Model.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieRepository implements Icategorie<Categorie> {

    private final Connection cnx;

    public CategorieRepository() throws SQLException {
        cnx = Databaseconnection.getInstance().getConnection();
    }


    public void update(Categorie categorie, int id) {
        String sql = "UPDATE categorie SET nom_categorie = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, categorie.getNom_categorie());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public <T> void add(T t) throws SQLException {
        if (t instanceof Categorie) {
            Categorie categorie = (Categorie) t;
            String sql = "INSERT INTO categorie (nom_categorie) VALUES (?)";

            try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
                preparedStatement.setString(1, categorie.getNom_categorie());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public <T> void update(T t, int id) {

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM categorie WHERE id = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> getAll() {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT * FROM categorie";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom_categorie = resultSet.getString("nom_categorie");

                Categorie categorie = new Categorie(id, nom_categorie);
                categories.add(categorie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (List<T>) categories;
    }

    @Override
    public Categorie getbyid(int id) {
        String sql = "SELECT * FROM categorie WHERE id = ?";
        Categorie categorie = null;

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nom_categorie = resultSet.getString("nom_categorie");
                    categorie = new Categorie(id, nom_categorie);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categorie;
    }
}
