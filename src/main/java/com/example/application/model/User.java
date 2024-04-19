package com.example.application.model;
import java.util.Arrays;
import org.mindrot.jbcrypt.BCrypt;



public class User {
    private int id;
    private String email;
    private String password;
    private String roles; // Assuming roles will be stored as a JSON array-like string
    private boolean isVerified;

    // Constructors, getters, setters

    // Constructor without the 'id' field (assuming it's auto-generated)
    public User(String email, String password, String roles, boolean isVerified) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.isVerified = isVerified;
    }
    public User() {
        // This constructor initializes the object with default values or leaves them null/empty
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        this.password = hashedPassword;
    }
    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
