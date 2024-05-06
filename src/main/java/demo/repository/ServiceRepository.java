package demo.repository;


import demo.model.Association;
import demo.model.Categorie;
import demo.model.Commentaire;
import demo.model.Service;

import demo.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRepository implements Iservice<Service> {
    private final Connection cnx;
    private Statement ste;

    public ServiceRepository() {
        cnx =DatabaseConnection.getConnection();
    }

    @Override
    public void update(Service service, int id) throws SQLException {

    }

    @Override
    public <T> void add(T t) throws SQLException {

    }

   /* @Override
    public <T> void add(T t) throws SQLException {
        if (!(t instanceof Service)) {
            throw new IllegalArgumentException("Objet de type Service requis.");
        }

        Service service = (Service) t;
        int disponibiliteInt = service.getDisponibilite() ? 1 : 0;

        String sql = "INSERT INTO service (categorie_id, commentaire_id, association_id, nom_service, description, disponibilite) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, service.getCategorie_id());
            pstmt.setInt(2, service.getCommentaire_id());
            pstmt.setInt(3, service.getAssociation_id());
            pstmt.setString(4, service.getNom_service());
            pstmt.setString(5, service.getDescription());
            pstmt.setInt(6, disponibiliteInt);
            pstmt.executeUpdate();
            cnx.commit();
        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée (journalisation, remontée, etc.)
            throw new RuntimeException("Erreur lors de l'ajout du service.", e);
        }
    }*/

    @Override
    public <T> void update(T t, int id) {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public <T> List<T> getAll() throws SQLException {
        return null;
    }

    @Override
    public <T> T getbyid(int id) {
        return null;
    }

    @Override
    public Service getById(int id) throws SQLException {
        return null;
    }

    @Override
    public void ajouter(Service service) throws SQLException {
        // Vérifier si le nom du service est null
        String nom_service = service.getNom_service();
        //if (nomService == null) {
            // Gérer le cas où le nom du service est null
          //  throw new IllegalArgumentException("Le nom du service ne peut pas être null");
        //}

        int disponibiliteInt = service.getDisponibilite() ? 1 : 0;

        // Préparer la déclaration SQL
        String sql = "INSERT INTO service (nom_service, description, association_id, categorie_id, commentaire_id, disponibilite) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setString(1, nom_service);
            pstmt.setString(2, service.getDescription());
            // Passer null si l'association est nulle
            pstmt.setObject(3, service.getAssociation_id() != null ? service.getAssociation_id().getId() : null);
            // Passer null si la catégorie est nulle
            pstmt.setObject(4, service.getCategorie_id() != null ? service.getCategorie_id().getId() : null);
            // Passer null si le commentaire est nul
            pstmt.setObject(5, service.getCommentaire_id() != null ? service.getCommentaire_id().getId() : null);
            pstmt.setInt(6, disponibiliteInt);

            // Exécuter la mise à jour
            pstmt.executeUpdate();

        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée (journalisation, remontée, etc.)
            throw new RuntimeException("Erreur lors de l'ajout du service.", e);
        }
    }


  /*  @Override
    public void update(Service service) throws SQLException {

    }

    @Override
    public void delete(Service service) throws SQLException {

    }




@Override
public void update(Service service) throws SQLException {
    String request = "UPDATE service SET nom_service=?, description=?, disponibilite=? WHERE id=?";

    PreparedStatement preparedStatement = cnx.prepareStatement(request);
    preparedStatement.setString(1, service.getNom_service());
    preparedStatement.setString(2, service.getDescription());
    preparedStatement.setBoolean(3, service.isDisponibilite());
    preparedStatement.setInt(4, service.getId());
    preparedStatement.executeUpdate();
}*/
@Override
public void delete(Service service) throws SQLException {
    String request = "DELETE FROM service WHERE id = ?";
    PreparedStatement preparedStatement = cnx.prepareStatement(request);
    preparedStatement.setInt(1, service.getId());
    preparedStatement.executeUpdate();
}
  public List<Service> display() throws SQLException {
      List<Service> services = new ArrayList<>();

      String sql = "SELECT s.*, a.*, c.*, cm.* " +
              "FROM service s " +
              "LEFT JOIN association a ON s.association_id = a.id " +
              "LEFT JOIN categorie c ON s.categorie_id = c.id " +
              "LEFT JOIN commentaire cm ON s.commentaire_id = cm.id";

      try (Statement stmt = cnx.createStatement();
           ResultSet rs = stmt.executeQuery(sql)) {

          while (rs.next()) {
              int serviceId = rs.getInt("s.id");
              String nomService = rs.getString("s.nom_service");
              String description = rs.getString("s.description");
              boolean disponibilite = rs.getBoolean("s.disponibilite");

              // Création de l'objet Association associé
              Association association = new Association(
                      rs.getInt("a.id"),
                      rs.getString("a.nom"),
                      rs.getString("a.description")
              );

              // Création de l'objet Categorie associé
              Categorie categorie = new Categorie(
                      rs.getInt("c.id"),
                      rs.getString("c.nom_categorie"));

              // Création de l'objet Commentaire associé
              Commentaire commentaire = new Commentaire(
                      rs.getInt("cm.id")

              );

              // Création de l'objet Service complet
              Service service = new Service(
                      serviceId,
                      nomService,
                      description,
                      disponibilite,
                      categorie,
                      commentaire,
                      association
              );

              services.add(service);
          }
      }

      return services;
  }
    @Override
    public void update(Service service) throws SQLException {
        String nom_service = service.getNom_service();
        // Préparer la déclaration SQL
        String sql = "UPDATE service SET nom_service = ?, description = ?, association_id = ?, categorie_id = ?, commentaire_id = ?, disponibilite = ? WHERE id = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setString(1, service.getNom_service());
            pstmt.setString(2, service.getDescription());
            // Passer null si l'association est nulle
            pstmt.setObject(3, service.getAssociation_id() != null ? service.getAssociation_id().getId() : null);
            // Passer null si la catégorie est nulle
            pstmt.setObject(4, service.getCategorie_id() != null ? service.getCategorie_id().getId() : null);
            // Passer null si le commentaire est nul
            pstmt.setObject(5, service.getCommentaire_id() != null ? service.getCommentaire_id().getId() : null);
            pstmt.setBoolean(6, service.getDisponibilite());
            pstmt.setInt(7, service.getId());

            // Exécuter la mise à jour
            pstmt.executeUpdate();

        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée (journalisation, remontée, etc.)
            throw new RuntimeException("Erreur lors de la mise à jour du service.", e);
        }
    }








}
