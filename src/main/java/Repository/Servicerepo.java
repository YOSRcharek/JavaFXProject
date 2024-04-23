package Repository;
import Model.Service;
import Database.Databaseconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Servicerepo implements Iservice <Service>  {
    private Connection cnx;
    private Statement ste;

    public Servicerepo() throws SQLException {
        cnx= Databaseconnection.getInstance().getConnection();
    }

    public static void update(Service serviceSelectionne) {
    }

    public void add(Service service) throws SQLException {
        // Établir une connexion à la base de données.
        Databaseconnection dataSource = new Databaseconnection();
        Connection conn = dataSource.getConnection();

        // Vérifier si la connexion a été correctement établie.
        if (conn == null) {
            throw new RuntimeException("Failed to establish database connection");
        }

        // Convertir le booléen en entier pour la base de données.
        int disponibiliteInt = service.getDisponibilite() ? 1 : 0;

        // Utiliser disponibiliteInt dans votre instruction SQL au lieu de service.getDisponibilite().
        String sql = "INSERT INTO service (categorie_id,commentaire_id, association_id,nom_service,description,disponibilite) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        pstmt.setInt(1, service.getCategorie_id());
        pstmt.setInt(2, service.getCommentaire_id());
        pstmt.setInt(3, service.getAssociation_id());
        pstmt.setString(4,service.getNom_service());
        pstmt.setString(5,service.getDescription());
        pstmt.setInt(6,disponibiliteInt);



    }

    @Override
    public void update(Service service, int id) {

    }

    @Override
    public <T> void add(T t) throws SQLException {

    }


    public boolean serviceExists(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM service WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }
        return false;
    }

    public void ajouterService(Service service) throws SQLException  {
        try (Statement stmt = cnx.createStatement()) {
            stmt.execute("SET foreign_key_checks = 0;");
        }
        int categorie_Id = service.getCategorie_id();
        int association_Id = service.getAssociation_id ();
        int commentaire_Id = service.getCommentaire_id();
        if (categorie_Id == 0 || association_Id == 0 || commentaire_Id == 0) {
            throw new IllegalArgumentException("La catégorie, l'association ou le commentaire n'existe pas.");
        }

        if (service.getCommentaire_id() <= 0 || !commentaireExiste(service.getCommentaire_id())) {
            throw new IllegalArgumentException("Le commentaire avec l'ID " + service.getCommentaire_id() + " n'existe pas ou n'est pas valide.");
        }

        if (serviceExists(service.getId())) {
            throw new IllegalArgumentException("A service with ID " + service.getId() + " already exists.");
        }

        if (!service.getNom_service().isEmpty() && !service.getDescription().isEmpty()) {
            String req = "INSERT INTO service (categorie_id,commentaire_id, association_id,nom_service,description,disponibilite) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
                preparedStatement.setInt(1, service.getCategorie_id());
                preparedStatement.setInt(2, service.getCommentaire_id());
                preparedStatement.setInt(3, service.getAssociation_id());
                preparedStatement.setString(4, service.getNom_service());
                preparedStatement.setString(5, service.getDescription());
                preparedStatement.setBoolean(6, service.getDisponibilite());

                preparedStatement.executeUpdate();
            }
        } else {
            throw new IllegalArgumentException("Tous les champs ne sont pas remplis.");
        }
    }

    private int getCategorie_Id(String nomCategorie) throws SQLException {
        String req = "SELECT id FROM `categorie` WHERE nom_categorie=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, nomCategorie);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        return 0;
    }

    private int getAssociationId(String nomAssociation) throws SQLException {
        String req = "SELECT id FROM `association` WHERE nom_association=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, nomAssociation);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        return 0;
    }
    private int getCommentaireId(String contenuCommentaire) throws SQLException {
        String req = "SELECT id FROM `commentaire` WHERE contenu=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, contenuCommentaire);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        return 0;
    }


    public void modifierService(Service service) throws SQLException {
        String sql = "UPDATE `service` SET `nom_service`=?,`description`=?,`disponibilite`=?,`categorie_id`=?,`commentaire_id`=?,`association_id`=? WHERE id=?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, service.getNom_service());
            preparedStatement.setString(2, service.getDescription());
            preparedStatement.setBoolean(3, service.isDisponibilite());
            preparedStatement.setInt(4, service.getCategorie_id());
            preparedStatement.setInt(5, service.getCommentaire_id());
            preparedStatement.setInt(6, service.getAssociation_id());
            preparedStatement.setInt(7, service.getId());

            preparedStatement.executeUpdate();
        }
    }
    public void supprimerService(int id) throws SQLException {
        String deleteSql = "DELETE FROM service WHERE id=?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }
    public List<Service> recupererServices() throws SQLException {
        List<Service> services = new ArrayList<>();
        String req = "SELECT * FROM service";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Service service = new Service(
                        resultSet.getInt("id"),
                        resultSet.getString("nom_service"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("disponibilite"),
                        resultSet.getInt("categorie_id"),
                        resultSet.getInt("commentaire_id"),
                        resultSet.getInt("association_id")
                );
                services.add(service);
            }
        }
        return services;
    }


    @Override
    public <T> void update(T t, int id) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Service> getAll() {
        List<Service> list=new ArrayList<>();
        String sql="select * from service";
        try {
            ste=cnx.createStatement();
            ResultSet rs=ste.executeQuery(sql);
            while(rs.next()){
                list.add(new Service(rs.getString(1),
                        rs.getString(2),
                        rs.getBoolean(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  list;
    }

    @Override
    public Service getbyid(int id) {
        return null;
    }public boolean commentaireExiste(int commentaireId) throws SQLException {
        String req = "SELECT COUNT(*) FROM `commentaire` WHERE `id`=?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, commentaireId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }


}
