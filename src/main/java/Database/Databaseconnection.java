// DatabaseConnection.java
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Databaseconnection {
    private String url="jdbc:mysql://localhost:3306/tunicollab";
    private String login="root";
    private String pwd="";
    private Connection connection;
    public static Databaseconnection instance;

    public Databaseconnection() {
        try {
            connection= DriverManager.getConnection(url,login,pwd);
            System.out.println("connexion etablie");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static Databaseconnection getInstance(){
        if (instance == null) {
            instance = new Databaseconnection();
        }

        return instance;
    }
    public Connection getConnection() {
        return this.connection;
    }
}
