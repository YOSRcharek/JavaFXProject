package com.example.application.repository;

import com.example.application.DatabaseConnection;
import com.example.application.model.TypeEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeEventRepo {
    private final Connection connection;
    public TypeEventRepo(){
        connection = DatabaseConnection.getConnection();
    }
    public void ajouter(TypeEvent typeEvent) {
        // Requête SQL pour insérer un nouveau type d'événement dans la base de données
        String sql = "INSERT INTO type_event (nom) VALUES (?)";

        try {
            // Préparation de la requête
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, typeEvent.getNom());

            // Exécution de la requête
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Le type d'événement a été ajouté avec succès !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du type d'événement : " + e.getMessage());
        }
    }
    public void modifier(TypeEvent typeEvent) {
        // Requête SQL pour mettre à jour un type d'événement dans la base de données
        String sql = "UPDATE type_event SET nom = ? WHERE id = ?";

        try {
            // Préparation de la requête
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, typeEvent.getNom());
            statement.setInt(2, typeEvent.getId()); // Utilisez l'ID pour identifier le type d'événement à mettre à jour

            // Exécution de la requête
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Le type d'événement a été modifié avec succès !");
            } else {
                System.out.println("Aucun type d'événement n'a été trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du type d'événement : " + e.getMessage());
        }
    }
    public void supprimerEvenementsPourType(int id) {
        String sql = "DELETE FROM event WHERE type_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            System.out.println(rowsDeleted + " événements associés au type d'événement ont été supprimés.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression des événements associés au type d'événement : " + e.getMessage());
        }
    }
    public List<TypeEvent> getAllTypeEvents() {
        List<TypeEvent> typeEvents = new ArrayList<>();

        // Requête SQL pour récupérer tous les types d'événements
        String sql = "SELECT * FROM type_event";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                // Parcours des résultats et création des objets TypeEvent correspondants
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    TypeEvent typeEvent = new TypeEvent(id, nom);
                    typeEvents.add(typeEvent);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des types d'événements : " + e.getMessage());
        }

        return typeEvents;
    }


}
