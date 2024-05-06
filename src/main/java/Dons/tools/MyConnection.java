package Dons.tools;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public String url="jdbc:mysql://localhost:3308/tunicollab1" ;
    public String login="root";
    public String pwd="" ;
    Connection cnx ;
    public static MyConnection instance ;
    public MyConnection() {
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connection établie");
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
    public Connection getCnx(){
        return cnx ;
    }

    public static MyConnection getInstance(){
        if (instance==null){
            instance=new MyConnection() ;
        }
        return instance ;



    }
}

