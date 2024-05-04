package com.example.application.repository;

import com.example.application.DatabaseConnection;
import com.example.application.model.Events;
import com.example.application.model.TypeEvent;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventRepo {
    private final Connection connection;

    public EventRepo() {
        connection = DatabaseConnection.getConnection();
    }

    public void ajouter(Events event) {
        // Requête SQL pour insérer un nouvel événement dans la base de données
        String sql = "INSERT INTO event (nom_event, description, date_debut, date_fin, localisation, capacite_max, capacite_actuelle, image, latitude, longitude, type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Préparation de la requête
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, event.getNomEvent());
            statement.setString(2, event.getDescription());
            statement.setDate(3, java.sql.Date.valueOf(event.getDateDebut()));
            statement.setDate(4, java.sql.Date.valueOf(event.getDateFin()));
            statement.setString(5, event.getLocalisation());
            statement.setInt(6, event.getCapaciteMax());
            statement.setInt(7, event.getCapaciteActuelle());
            statement.setString(8, event.getImage());
            statement.setFloat(9, event.getLatitude());
            statement.setFloat(10, event.getLongitude());
            statement.setInt(11, event.getTypeEvent().getId()); // Ajoutez l'ID du type d'événement

            // Exécution de la requête
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("L'événement a été ajouté avec succès !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'événement : " + e.getMessage());
        }
    }

    public void modifier(Events event) {
        // Requête SQL pour mettre à jour un événement dans la base de données
        String sql = "UPDATE event SET nom_event = ?, description = ?, date_debut = ?, date_fin = ?, localisation = ?, capacite_max = ?, capacite_actuelle = ?, image = ?, latitude = ?, longitude = ? WHERE id = ?";

        try {
            // Préparation de la requête
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, event.getNomEvent());
            statement.setString(2, event.getDescription());
            statement.setDate(3, java.sql.Date.valueOf(event.getDateDebut()));
            statement.setDate(4, java.sql.Date.valueOf(event.getDateFin()));
            statement.setString(5, event.getLocalisation());
            statement.setInt(6, event.getCapaciteMax());
            statement.setInt(7, event.getCapaciteActuelle());
            statement.setString(8, event.getImage());
            statement.setFloat(9, event.getLatitude());
            statement.setFloat(10, event.getLongitude());
            statement.setInt(11, event.getId()); // Assurez-vous d'inclure l'ID de l'événement dans la clause WHERE

            // Exécution de la requête
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("L'événement a été modifié avec succès !");
            } else {
                System.out.println("Aucun événement trouvé avec l'ID spécifié.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de l'événement : " + e.getMessage());
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM event WHERE id = ?";

        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("L'événement avec l'ID " + id + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun événement trouvé avec l'ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'événement : " + e.getMessage());
        }
    }
    public List<Events> getAllEvents() {
        String sql = "SELECT e.*, t.id AS type_id, t.nom AS type_nom " +
                "FROM event e " +
                "JOIN type_event t ON e.type_id = t.id";

        List<Events> eventsList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Events event = new Events(
                        resultSet.getInt("id"),
                        resultSet.getInt("type_id"),
                        resultSet.getString("nom_event"),
                        resultSet.getString("description"),
                        resultSet.getDate("date_debut").toLocalDate(),
                        resultSet.getDate("date_fin").toLocalDate(),
                        resultSet.getString("localisation"),
                        resultSet.getInt("capacite_max"),
                        resultSet.getInt("capacite_actuelle"),
                        resultSet.getString("image"),
                        resultSet.getFloat("latitude"),
                        resultSet.getFloat("longitude"),
                        new TypeEvent(resultSet.getInt("type_id"), resultSet.getString("type_nom"))
                );
                eventsList.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des événements : " + e.getMessage());
        }

        return eventsList;
    }

    public void afficherTous() {
        String sql = "SELECT * FROM event";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            List<Events> eventsList = new ArrayList<>();
            while (resultSet.next()) {
                // Créer un objet Events pour chaque ligne de résultat et l'ajouter à la liste
                Events event = new Events(

                        resultSet.getInt("id"),
                        resultSet.getString("nomEvent"),
                        resultSet.getString("description"),
                        resultSet.getDate("dateDebut").toLocalDate(),
                        resultSet.getDate("dateFin").toLocalDate(),
                        resultSet.getString("localisation"),
                        resultSet.getInt("capaciteMax"),
                        resultSet.getInt("capaciteActuelle"),
                        resultSet.getString("image"),
                        resultSet.getFloat("latitude"),
                        resultSet.getFloat("longitude")
                        // Assurez-vous d'ajouter d'autres champs si nécessaire
                );
                eventsList.add(event);
            }

            // Afficher tous les événements
            for (Events event : eventsList) {
                System.out.println(event);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des événements : " + e.getMessage());
        }
    }
    public void participer(Events event) {
        try {
            // Incrémenter la capacité actuelle
            event.setCapaciteActuelle(event.getCapaciteActuelle() + 1);

            // Mettre à jour la capacité actuelle dans la base de données
            String sql = "UPDATE event SET capacite_actuelle = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, event.getCapaciteActuelle());
            statement.setInt(2, event.getId());
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("La capacité actuelle de l'événement a été mise à jour avec succès !");
            } else {
                System.out.println("Aucun événement trouvé avec l'ID spécifié.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la capacité actuelle de l'événement : " + e.getMessage());
        }
    }
    public void importerImage(int eventId, String imagePath) {
        try {
            // Lire le contenu de l'image sous forme de tableau d'octets
            FileInputStream inputStream = new FileInputStream(imagePath);
            byte[] imageBytes = inputStream.readAllBytes();

            // Requête SQL pour mettre à jour l'image de l'événement dans la base de données
            String sql = "UPDATE event SET image = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBytes(1, imageBytes); // Image sous forme de tableau d'octets
            statement.setInt(2, eventId); // ID de l'événement à mettre à jour

            // Exécuter la requête
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("L'image de l'événement a été importée avec succès !");
            } else {
                System.out.println("Aucun événement trouvé avec l'ID spécifié.");
            }

            // Fermer le flux d'entrée
            inputStream.close();
        } catch (IOException | SQLException e) {
            System.err.println("Erreur lors de l'importation de l'image : " + e.getMessage());
        }
    }

}
