// Repository Package
package application.repository;

import application.DatabaseConnection;

import java.sql.Connection;
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
}
