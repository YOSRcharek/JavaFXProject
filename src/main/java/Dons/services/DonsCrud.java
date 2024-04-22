package Dons.services;
import Dons.entities.Association;
import Dons.entities.Dons;
import Dons.entities.Typedons;
import Dons.tools.MyConnection;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.*;

public class DonsCrud implements ICrud<Dons>{

    static Connection cnx2;

    public DonsCrud() {
        cnx2 = MyConnection.getInstance().getCnx();
    }


    public List<Association> getAllAssociations() {
        List<Association> associations = new ArrayList<>();
        String query = "SELECT * FROM association"; // Assurez-vous que le nom de la table est correct

        try(PreparedStatement pst = cnx2.prepareStatement(query);
            ResultSet resultSet = pst.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nom");
                Association association = new Association( name,id);
                associations.add(association);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des associations : " + e.getMessage());
        }

        return associations;
    }

    public List<Typedons> getAllTypedons() {
        List<Typedons> typedonsList = new ArrayList<>();
        String query = "SELECT * FROM type_dons"; // Assurez-vous que le nom de la table est correct

        try (PreparedStatement pst = cnx2.prepareStatement(query);
             ResultSet resultSet = pst.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nom");
                Typedons typedons = new Typedons(id, name);
                typedonsList.add(typedons);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des types de dons : " + e.getMessage());
        }

        return typedonsList;
    }


    public Typedons getTypedonsById(int typeId) {
        Typedons typedons = null;
        String query = "SELECT id, nom FROM type_dons WHERE id = ?";
        try (PreparedStatement pst = cnx2.prepareStatement(query)) {
            pst.setInt(1, typeId);
            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("nom");
                    typedons = new Typedons(typeId, name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typedons;
    }

    public Association getAssociationById(int associationId) {
        Association association = null;
        String query = "SELECT id, nom FROM association WHERE id = ?";
        try (PreparedStatement pst = cnx2.prepareStatement(query)) {
            pst.setInt(1, associationId);
            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("nom");
                    association = new Association( name,associationId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return association;
    }

    public List<Dons> afficherEntite() {
        List<Dons> donsList = new ArrayList<>();
        String query = "SELECT id, montant, type_id, association_id, date_mis_don FROM dons";

        try (PreparedStatement pst = cnx2.prepareStatement(query);
             ResultSet resultSet = pst.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int montant = resultSet.getInt("montant");
                int typeId = resultSet.getInt("type_id");
                int associationId = resultSet.getInt("association_id");
                Date date_mis_don = resultSet.getDate("date_mis_don");

                // Récupérer les objets Typedons et Association à partir de leurs identifiants
                Typedons typedons = getTypedonsById(typeId);
                Association association = getAssociationById(associationId);

                // Créer un nouvel objet Dons avec les objets récupérés
                Dons dons = new Dons(id, typedons, association, montant, date_mis_don);
                donsList.add(dons);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donsList;
    }



    public void ajouterEntite(Dons p) {
        String req1 = "INSERT INTO dons (montant, type_id, association_id, date_mis_don) VALUES(?, ?, ?, ?)";
        try {
             PreparedStatement pst = cnx2.prepareStatement(req1) ;

            // Définir les valeurs pour les colonnes montant, type_id et association_id
            pst.setInt(1, p.getMontant());
            pst.setInt(2, p.getType_id().getId());
            pst.setInt(3, p.getAssociation_id().getId());


            // Vérifier si la date est nulle
            Date date_mis_don = p.getDate_mis_don();
            if (date_mis_don != null) {
                pst.setDate(4, new java.sql.Date(date_mis_don.getTime()));
            } else {
                pst.setNull(4, java.sql.Types.DATE);
            }

            // Exécuter la requête
            cnx2.setAutoCommit(false);
            pst.executeUpdate();
            cnx2.commit();
            System.out.println("Don ajouté avec succès");

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du don : " + e.getMessage());
            e.printStackTrace(); // Afficher la trace de la pile pour déboguer
        }
    }





    @Override
    public void supprimerEntite(Dons don) {
        String requet = "DELETE FROM dons WHERE id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requet) ;
            pst.setInt(1, don.getId());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Suppression réussie");
            } else {
                System.out.println("Aucune suppression effectuée. Vérifiez l'ID.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void modifierEntite(Dons don) {
        try {
            // Préparer la requête SQL pour mettre à jour l'entité
            String query = "UPDATE dons SET montant = ?, type_id = ?, association_id = ?, date_mis_don = ? WHERE id = ?";
            PreparedStatement pst = cnx2.prepareStatement(query);

            // Paramétrer les valeurs de la requête avec les données de l'entité
            pst.setInt(1, don.getMontant());
            pst.setInt(2, don.getType_id().getId());
            pst.setInt(3, don.getAssociation_id().getId());
            pst.setDate(4, new java.sql.Date(don.getDate_mis_don().getTime())); // Convertir Date en java.sql.Date
            pst.setInt(5, don.getId());

            // Exécuter la requête de mise à jour
            pst.executeUpdate();

            // Fermer le statement
            pst.close();

            System.out.println("Don modifié avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du don : " + e.getMessage());
            e.printStackTrace(); // Afficher la trace de la pile pour déboguer
        }
    }


}
