// Repository Package
package demo.repository;

import demo.DatabaseConnection;
import demo.model.Association;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class associationRepo {
    private static Connection connection;

    public associationRepo() {
        connection = DatabaseConnection.getConnection();
    }

    
    public static List<Association> getDemandes() {
        List<Association> associations = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
        	/* String query = "SELECT a.* FROM association a\r\n"
	            		+ "JOIN user u ON a.email = u.email\r\n"
	            		+ "WHERE u.is_verified = 1 and a.status=false\r\n"
	            		+ "AND u.roles = '[\"ROLE_ASSOCIATION\"]';";
           */String query = "SELECT * FROM association WHERE status=false;";
           PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Association association = new Association(0, query, query, query, null, null, null, null, null, null,false);
                association.setId(resultSet.getInt("id"));
                association.setNom(resultSet.getString("nom"));
                association.setDomaineActivite(resultSet.getString("domaine_activite"));
                association.setEmail(resultSet.getString("email"));
                association.setDateDemande(resultSet.getDate("date_demande"));
                association.setStatus(resultSet.getBoolean("status"));
                association.setDocument(resultSet.getBlob("document"));
                associations.add(association);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return associations;
    }

    public static void approveAssociation(int associationId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE association SET status = true WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, associationId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disapproveAssociation(int associationId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM association WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, associationId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public static List<Association> getAssociations() {
		 List<Association> associations = new ArrayList<>();
	        try (Connection connection = DatabaseConnection.getConnection()) {
	        	/* String query = "SELECT a.* FROM association a\r\n"
		            		+ "JOIN user u ON a.email = u.email\r\n"
		            		+ "WHERE u.is_verified = 1 and a.status=true\r\n"
		            		+ "AND u.roles = '[\"ROLE_ASSOCIATION\"]';";
                        */String query = "SELECT * FROM association WHERE status=true;";
	            PreparedStatement statement = connection.prepareStatement(query);
	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                Association association = new Association(0, query, query, query, query, query, null, null, null,null, false);
	                association.setId(resultSet.getInt("id"));
	                association.setNom(resultSet.getString("nom"));
	                association.setDomaineActivite(resultSet.getString("domaine_activite"));
	                association.setEmail(resultSet.getString("email"));
	                association.setAdresse(resultSet.getString("adresse"));
	                association.setStatus(resultSet.getBoolean("status"));
	                association.setDescription(resultSet.getString("description"));
	                association.setTelephone(resultSet.getInt("telephone"));
	                
	                associations.add(association);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return associations;
	    }



    public static void supprimerAssociation(int associationId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM association WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, associationId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }
   
    public static void modifierAssociation(int associationId, String nom, String domaineActivite, String adresse, String description, Integer telephone) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE association SET nom = ?, domaine_activite = ?, adresse = ?, description = ?, telephone = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nom);
            statement.setString(2, domaineActivite);
            statement.setString(3, adresse);
            statement.setString(4, description);
            statement.setInt(5, telephone);
            statement.setInt(6, associationId);
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ajouterAssociation(String nom, String domaine, String email, String password, Blob document, String adresse, String description, Integer telephone) {
        try (Connection connection = DatabaseConnection.getConnection()) {
             LocalDate dateDemande = LocalDate.now();

            String query = "INSERT INTO association(nom, password,domaine_activite, adresse, email, description, telephone, date_demande, document, status,active_compte) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nom);
            statement.setString(2, password);
            statement.setString(3, domaine);
            statement.setString(4, adresse);
            statement.setString(5, email);
            statement.setString(6, description);
            statement.setString(7, String.valueOf(telephone));
            statement.setDate(8, java.sql.Date.valueOf(dateDemande));
            statement.setBlob(9, document);
            statement.setBoolean(10, false);
            statement.setBoolean(11, false);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean emailExists(String email) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM association WHERE email = ?")) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static int nombreAssoc() {
        int count = 0;
        try (Connection connection = DatabaseConnection.getConnection();
            // PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM user WHERE is_verified=true AND roles='[\"ROLE_ASSOCIATION\"]'")) {
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM association WHERE status=true")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public static int nombreDem() {
        int count = 0;
        try (Connection connection = DatabaseConnection.getConnection();
          //   PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM user WHERE is_verified=true AND roles='[\"ROLE_ASSOCIATION\"]'")) {
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM association WHERE status=false")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public static Association getAssociationById(int id) {
        Association association = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM association WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id); // Définir le paramètre de l'ID
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                association = new Association();
                association.setId(resultSet.getInt("id"));
                association.setNom(resultSet.getString("nom"));
                association.setDomaineActivite(resultSet.getString("domaine_activite"));
                association.setEmail(resultSet.getString("email"));
                association.setAdresse(resultSet.getString("adresse"));
                association.setStatus(resultSet.getBoolean("status"));
                association.setDescription(resultSet.getString("description"));
                association.setTelephone(resultSet.getInt("telephone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return association;
    }
    public static Association getOneAssociation() {
        Association association = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM association WHERE id = 8;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                association = new Association();
                association.setId(resultSet.getInt("id"));
                association.setNom(resultSet.getString("nom"));
                association.setDomaineActivite(resultSet.getString("domaine_activite"));
                association.setEmail(resultSet.getString("email"));
                association.setAdresse(resultSet.getString("adresse"));
                association.setStatus(resultSet.getBoolean("status"));
                association.setDescription(resultSet.getString("description"));
                association.setTelephone(resultSet.getInt("telephone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return association;
    }
    public static boolean connect(String email, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    String storedPassword = resultSet.getString("password");

                    return storedPassword.equals(password);
                }
            }
        }
        return false;
    }
    public Association getassocaitionbyid(int id) {
        return null;
    }

    public <T> List<T> getAllassociation() {
        List<Association> associations = new ArrayList<>();
        String sql = "SELECT * FROM association";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
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

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
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


    public Association getAssociationByNom(String nomAssociation) {
        String sql = "SELECT * FROM association WHERE nom = ?";
        Association association = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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


}

