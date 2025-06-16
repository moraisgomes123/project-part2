package org.example;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Create a new Login
        Login user = new Login("username", "password", "email", "firstName", "lastName");

        // Show registration
        JOptionPane.showMessageDialog(null, "=== Registration ===");

        String registrationMessage = user.registerUser();
        JOptionPane.showMessageDialog(null, registrationMessage);

        // Proceed only if registration was successful
        if (registrationMessage.contains("Registration successful!")) {
            // Attempt login
            boolean isSuccess = user.loginUser();

            JOptionPane.showMessageDialog(null, user.returnLoginStatus(isSuccess));

            // If login is successful, launch the QuickChat message
            if (isSuccess) {
                Message.main(new String[0]);
            } else {
                JOptionPane.showMessageDialog(null, "Cannot proceed to QuickChat due to login failure.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cannot proceed to login due to registration failure.");
        }
    }
}