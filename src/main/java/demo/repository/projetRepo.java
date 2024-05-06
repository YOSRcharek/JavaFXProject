// Repository Package
	package demo.repository;
	import demo.DatabaseConnection;
	import demo.model.Projet;

	import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;

public class projetRepo {
	    private Connection connection;

	    public projetRepo() {
	        connection = DatabaseConnection.getConnection();
	    }

		public static List<Projet> getProjets() {
			 List<Projet> projets = new ArrayList<>();
		        try (Connection connection = DatabaseConnection.getConnection()) {
		        	 String query = "SELECT * FROM projet";
		            PreparedStatement statement = connection.prepareStatement(query);
		            ResultSet resultSet = statement.executeQuery();

		            while (resultSet.next()) {
		                Projet projet = new Projet(0, query, query, null, null, null, 0);
		                projet.setId(resultSet.getInt("id"));
		                projet.setNomProjet(resultSet.getString("nom_projet"));
		                projet.setDateFin(resultSet.getDate("date_fin"));
		                projet.setDateDebut(resultSet.getDate("date_debut"));
		                projet.setStatus(resultSet.getString("status"));
		                projet.setDescription(resultSet.getString("description"));
		                projet.setIdAssociation(resultSet.getInt("association_id"));
		                projets.add(projet);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return projets;
		    }
		
	    public static void supprimerProjet(int projetId) {
	        try (Connection connection = DatabaseConnection.getConnection()) {
	            String query = "DELETE FROM projet WHERE id = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setInt(1, projetId);
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    
	    }
	   
	    public static void modifierProjet(int projetId, String nomProjet, java.util.Date dateDebut,java.util.Date dateFin,String status, String description) {
	        try (Connection connection = DatabaseConnection.getConnection()) {
	            String query = "UPDATE projet SET nom_projet = ?, date_debut = ?, date_fin = ?, description = ?, status = ? WHERE id = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, nomProjet);
	            statement.setDate(2, (Date) dateDebut);
	            statement.setDate(3, (Date) dateFin);
	            statement.setString(4, description);
	            statement.setString(5, status);
	            statement.setInt(6, projetId);
	            
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public static void ajouterProjet(String nomProjet, java.util.Date dateDebut,java.util.Date dateFin,String status, String description, int idAssociation) {
	    	try (Connection connection = DatabaseConnection.getConnection()) {
	    	    String query = "INSERT INTO projet(nom_projet, date_debut, date_fin, description, status, association_id ) VALUES (?, ?, ?, ?, ? , ?)";
	    	    PreparedStatement statement = connection.prepareStatement(query);
	    	    statement.setString(1, nomProjet);
	    	    statement.setDate(2, new java.sql.Date(dateDebut.getTime())); // Convert java.util.Date to java.sql.Date
	    	    statement.setDate(3, new java.sql.Date(dateFin.getTime())); // Convert java.util.Date to java.sql.Date
	    	    statement.setString(4, description);
	    	    statement.setString(5, status);
	            statement.setInt(6, idAssociation);
	    	    statement.executeUpdate();
	    	} catch (SQLException e) {
	    	    e.printStackTrace();
	    	}

	    }

	public static List<Projet> getProjetsProfil() {
		List<Projet> projets = new ArrayList<>();
		try (Connection connection = DatabaseConnection.getConnection()) {
			String query = "SELECT * FROM projet where association_id=8;";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Projet projet = new Projet(0, query, query, null, null, null, 0);
				projet.setId(resultSet.getInt("id"));
				projet.setNomProjet(resultSet.getString("nom_projet"));
				projet.setDateFin(resultSet.getDate("date_fin"));
				projet.setDateDebut(resultSet.getDate("date_debut"));
				projet.setStatus(resultSet.getString("status"));
				projet.setDescription(resultSet.getString("description"));
				projet.setIdAssociation(resultSet.getInt("association_id"));
				projets.add(projet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projets;
	}

	public static List<Projet> getProjetsProfilById(int id) {
		List<Projet> projets = new ArrayList<>();
		try (Connection connection = DatabaseConnection.getConnection()) {
			String query = "SELECT * FROM projet where association_id=?;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id); // Définir le paramètre de l'ID
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Projet projet = new Projet(0, query, query, null, null, null, 0);
				projet.setId(resultSet.getInt("id"));
				projet.setNomProjet(resultSet.getString("nom_projet"));
				projet.setDateFin(resultSet.getDate("date_fin"));
				projet.setDateDebut(resultSet.getDate("date_debut"));
				projet.setStatus(resultSet.getString("status"));
				projet.setDescription(resultSet.getString("description"));
				projet.setIdAssociation(resultSet.getInt("association_id"));
				projets.add(projet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projets;
	}

	public static int nbProjetTermine() {
		int count = 0;
		try (Connection connection = DatabaseConnection.getConnection()) {
			String query = "SELECT count(*) FROM projet WHERE status='termine'";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						count = resultSet.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public static int nbProjetEnCours() {
		int count = 0;
		try (Connection connection = DatabaseConnection.getConnection()) {
			String query = "SELECT count(*) FROM projet WHERE status='en cours'";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						count = resultSet.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

}

