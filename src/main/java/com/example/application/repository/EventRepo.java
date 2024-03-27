package com.example.application.repository;

import com.example.application.DatabaseConnection;
import com.example.application.model.Events;

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
        String sql = "INSERT INTO event (nom_event, description, date_debut, date_fin, localisation, capacite_max, capacite_actuelle, image, latitude, longitude) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Préparation de la requête
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, event.getNomEvent());
            statement.setString(2, event.getDescription());
            statement.setDate(3, new java.sql.Date(event.getDateDebut().getTime()));
            statement.setDate(4, new java.sql.Date(event.getDateFin().getTime()));
            statement.setString(5, event.getLocalisation());
            statement.setInt(6, event.getCapaciteMax());
            statement.setInt(7, event.getCapaciteActuelle());
            statement.setString(8, event.getImage());
            statement.setFloat(9, event.getLatitude());
            statement.setFloat(10, event.getLongitude());

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
            statement.setDate(3, new java.sql.Date(event.getDateDebut().getTime()));
            statement.setDate(4, new java.sql.Date(event.getDateFin().getTime()));
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
                        resultSet.getString("nom_event"),
                        resultSet.getString("description"),
                        resultSet.getDate("date_debut"),
                        resultSet.getDate("date_fin"),
                        resultSet.getString("localisation"),
                        resultSet.getInt("capacite_max"),
                        resultSet.getInt("capacite_actuelle"),
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
}
