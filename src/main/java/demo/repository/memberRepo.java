package demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import demo.DatabaseConnection;
import demo.model.Association;
import demo.model.Membre;
import demo.model.Projet;

public class memberRepo {

	 private Connection connection;

	    public memberRepo() {
	        connection = DatabaseConnection.getConnection();
	    }

		public static List<Membre> getMembres() {
			 List<Membre> membres = new ArrayList<>();
		        try (Connection connection = DatabaseConnection.getConnection()) {
		        	 String query = "SELECT * FROM membre";
		            PreparedStatement statement = connection.prepareStatement(query);
		            ResultSet resultSet = statement.executeQuery();

		            while (resultSet.next()) {
		                Membre membre = new Membre(0, query, query, null, null, null, 0);
		                membre.setId(resultSet.getInt("id"));
		                membre.setNomMembre(resultSet.getString("nom_membre"));
		                membre.setPrenomMembre(resultSet.getString("prenom_membre"));
		                membre.setFonction(resultSet.getString("fonction"));
		                membre.setTelephone(resultSet.getString("telephone"));
		                membre.setEmailMembre(resultSet.getString("email_membre"));
		                membre.setIdAssociation(resultSet.getInt("association_id"));
		                membres.add(membre);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return membres;
		    }
		
	    public static void supprimerMembre(int membreId) {
	        try (Connection connection = DatabaseConnection.getConnection()) {
	            String query = "DELETE FROM membre WHERE id = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setInt(1, membreId);
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    
	    }
	   
	    public static void modifierMembre(int membreId, String nomMembre, String prenomMembre, String fonction,String telephone,String emailMembre) {
	        try (Connection connection = DatabaseConnection.getConnection()) {
	        	String query = "UPDATE membre SET nom_membre = ?, fonction = ?, prenom_membre = ?, telephone = ?, email_membre = ? WHERE id = ?";

	        	PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, nomMembre);
	            statement.setString(2, fonction);
	            statement.setString(3, prenomMembre);
	            statement.setString(4, telephone);
	            statement.setString(5, emailMembre);
	            statement.setInt(6, membreId);
	            
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public static void ajouterMembre(String nomMembre, String prenomMembre, String fonction,String telephone,String emailMembre, int idAssociation) {
	    	try (Connection connection = DatabaseConnection.getConnection()) {
	    	    String query = "INSERT INTO membre(nom_membre,fonction, prenom_membre,telephone,email_membre,association_id ) VALUES (?, ?, ?, ?, ?, ?)";
	    	    PreparedStatement statement = connection.prepareStatement(query);
	    	    statement.setString(1, nomMembre);
	    	   statement.setString(2, fonction);
	    	    statement.setString(3, prenomMembre);
	            statement.setString(4, telephone);
	            statement.setString(5, emailMembre);
	            statement.setInt(6, idAssociation);
	    	    statement.executeUpdate();
	    	} catch (SQLException e) {
	    	    e.printStackTrace();
	    	}

	    }

	public static List<Membre> getMembersProfil(String email) {
		List<Membre> membres = new ArrayList<>();
		Association asso=associationRepo.getAssociationByEmail(email);
		try (Connection connection = DatabaseConnection.getConnection()) {
			String query = "SELECT * FROM membre where association_id=?;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1,asso.getId());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Membre membre = new Membre(0, query, query, null, null, null, 0);
				membre.setId(resultSet.getInt("id"));
				membre.setNomMembre(resultSet.getString("nom_membre"));
				membre.setPrenomMembre(resultSet.getString("prenom_membre"));
				membre.setFonction(resultSet.getString("fonction"));
				membre.setTelephone(resultSet.getString("telephone"));
				membre.setEmailMembre(resultSet.getString("email_membre"));
				membre.setIdAssociation(resultSet.getInt("association_id"));
				membres.add(membre);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return membres;
	}
	public static List<Membre> getMembersProfilById(int id) {
		List<Membre> membres = new ArrayList<>();
		try (Connection connection = DatabaseConnection.getConnection()) {
			String query = "SELECT * FROM membre where association_id=?;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id); // Définir le paramètre de l'ID
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Membre membre = new Membre(0, query, query, null, null, null, 0);
				membre.setId(resultSet.getInt("id"));
				membre.setNomMembre(resultSet.getString("nom_membre"));
				membre.setPrenomMembre(resultSet.getString("prenom_membre"));
				membre.setFonction(resultSet.getString("fonction"));
				membre.setTelephone(resultSet.getString("telephone"));
				membre.setEmailMembre(resultSet.getString("email_membre"));
				membre.setIdAssociation(resultSet.getInt("association_id"));
				membres.add(membre);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return membres;
	}
}