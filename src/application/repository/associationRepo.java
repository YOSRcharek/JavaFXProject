// Repository Package
package application.repository;

import application.DatabaseConnection;
import application.model.Association;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class associationRepo {
    private Connection connection;

    public associationRepo() {
        connection = DatabaseConnection.getConnection();
    }

    public List<String> getAllAssociationNames() {
        List<String> associationNames = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT nom FROM association";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String associationName = resultSet.getString("nom");
                associationNames.add(associationName);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return associationNames;
    }
    public static List<Association> getDemandes() {
        List<Association> associations = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
        	 String query = "SELECT a.* FROM association a\r\n"
	            		+ "JOIN user u ON a.email = u.email\r\n"
	            		+ "WHERE u.is_verified = 1 and a.status=false\r\n"
	            		+ "AND u.roles = '[\"ROLE_ASSOCIATION\"]';";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Association association = new Association(0, query, query, query, null, null, null, null, null, false);
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
	        	 String query = "SELECT a.* FROM association a\r\n"
		            		+ "JOIN user u ON a.email = u.email\r\n"
		            		+ "WHERE u.is_verified = 1 and a.status=true\r\n"
		            		+ "AND u.roles = '[\"ROLE_ASSOCIATION\"]';";
	            PreparedStatement statement = connection.prepareStatement(query);
	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                Association association = new Association(0, query, query, query, query, query, null, null, null, false);
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
    
   }

