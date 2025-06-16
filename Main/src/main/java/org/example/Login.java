package org.example;
import javax.swing.JOptionPane;

public class Login {
    // Attributes
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellNumber;
    // Constructor
    public Login(String firstName, String lastName, String username, String password, String cellNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
    }
    // Getters
    public String getUsername() {
        return username; }
    public String getPassword() {
        return password; }
    public String getCellNumber() {
        return cellNumber; }
    public String getFirstName() {
        return firstName; }
    public String getLastName() {
        return lastName; }
    // Setters
    public void setUsername(String username) {
        this.username = username; }
    public void setPassword(String password) {
        this.password = password; }
    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber; }
    public void setFirstName(String firstName) {
        this.firstName = firstName; }
    public void setLastName(String lastName) {
        this.lastName = lastName; }

    // Username must contain '_' and be no longer than 5 characters
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // Password complexity
    public boolean checkPasswordComplexity() {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*()].*");
    }

    // Cell number must match South African format: +27XXXXXXXXX
    public boolean checkCellNumber() {
        return cellNumber.matches("\\+27\\d{9}");
    }

    // Registration logic with dialog input
    public String registerUser() {
        setFirstName(JOptionPane.showInputDialog("Enter first name:"));
        setLastName(JOptionPane.showInputDialog("Enter last name:"));
        setCellNumber(JOptionPane.showInputDialog("Enter cell number (e.g., +27XXXXXXXXX):"));
        setUsername(JOptionPane.showInputDialog("Enter username (must contain '_' and be <= 5 characters):"));
        setPassword(JOptionPane.showInputDialog("Enter password (must include uppercase, lowercase, number, special character):"));

        if (!checkUserName()) {
            return "Invalid username. Must contain an underscore(_) and be no more than 5 characters.";
        } else if (!checkPasswordComplexity()) {
            return "Weak password. Must include uppercase, lowercase, number, and special character.";
        } else if (!checkCellNumber()) {
            return "Invalid South African cell number. Must be in format +27XXXXXXXXX.";
        } else {
            return "Registration successful!";
        }
    }
    // Login
    public boolean loginUser() {
        String inputUsername = JOptionPane.showInputDialog("Enter username:");
        String inputPassword = JOptionPane.showInputDialog("Enter password:");
        return inputUsername.equals(username) && inputPassword.equals(password);
    }
    // Returns login status message
    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Login successful. Welcome, " + firstName + " " + lastName + "!";
        } else {
            return "Login failed. Please check your username and password.";
        }
    }
}


