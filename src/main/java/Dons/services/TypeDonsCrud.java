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
public class TypeDonsCrud implements ITypeDonsCrud<Typedons>{
    static Connection cnx2;

    public TypeDonsCrud() {
        cnx2 = MyConnection.getInstance().getCnx();
    }


    @Override
    public void ajouterType(Typedons p) {
        try {
            String req = "INSERT INTO type_dons (nom) VALUES (?)";
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setString(1, p.getName());
            pst.executeUpdate();
            System.out.println("Type de don ajouté avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du type de don: " + e.getMessage());
        }
    }

    @Override
    public List<Typedons> afficherType() {
        List<Typedons> types = new ArrayList<>();
        try {
            String req = "SELECT * FROM type_dons";
            Statement stm = cnx2.createStatement();
            ResultSet rs = stm.executeQuery(req);
            while (rs.next()) {
                Typedons type = new Typedons(rs.getInt("id"), rs.getString("nom"));
                types.add(type);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des types de dons: " + e.getMessage());
        }
        return types;
    }

    @Override
    public void modifierType(Typedons p) {
        try {
            String req = "UPDATE type_dons SET nom = ? WHERE id = ?";
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setString(1, p.getName());
            pst.setInt(2, p.getId());
            pst.executeUpdate();
            System.out.println("Type de don modifié avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du type de don: " + e.getMessage());
        }
    }

    public void supprimerType(Typedons p) {
        try {
            String req = "DELETE FROM type_dons WHERE id = ?";
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setInt(1, p.getId());
            pst.executeUpdate();
            System.out.println("Type de don supprimé avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du type de don: " + e.getMessage());
        }
    }
}
