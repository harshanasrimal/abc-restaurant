package models;

import org.mindrot.jbcrypt.BCrypt;

public abstract class User {

    private int id;
    private String name;
    private String email;
    private String password;  // Store only the hash for security

    // Constructor is protected to ensure only subclasses can call it
    protected User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;  // Store password from DB
    }

    // Hash the password using BCrypt
    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));
    }

    // Method to verify the password
    public boolean checkPassword(String plainTextPassword) {
        return BCrypt.checkpw(plainTextPassword, this.password);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    // Ensure the password is hashed before setting
    public void setPassword(String plainTextPassword) {
        this.password = hashPassword(plainTextPassword);
    }
}
