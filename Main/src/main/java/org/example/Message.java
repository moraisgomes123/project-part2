package org.example;
import javax.swing.*;
import java.util.*;

public class Message {

    // List to store sent or stored message summaries
    static List<String> messageList = new ArrayList<>();
    static int totalMessagesToSend;
    static int sentMessageCount = 0;

    public static void main(String[] args) {
        // Welcome message shown at the start of the program
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");

        // Ask user how many messages they want to send
        try {
            totalMessagesToSend = Integer.parseInt(
                    JOptionPane.showInputDialog("Enter how many messages you wish to send:")
            );
        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(null, "Invalid number. Exiting...");
            return;
        }

        // Main loop for menu interaction
        while (true) {
            // Options for user to choose
            String[] options = {"Send Messages", "Show recently sent messages", "Quit"};

            // Display main menu
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Choose an option:",
                    "Main Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            // Handle user's choice
            switch (choice) {
                case 0 -> {
                    // If message limit reached, notify user
                    if (sentMessageCount >= totalMessagesToSend) {
                        JOptionPane.showMessageDialog(null, "Message limit reached.");
                    } else {
                        // Otherwise, begin message sending process
                        sendMessageProcedure();
                    }
                }
                case 1 -> {
                    // Show list of recently sent or stored messages
                    if (!messageList.isEmpty()) {
                        StringBuilder sb = new StringBuilder("Recently sent/stored messages:\n");
                        for (String msg : messageList) {
                            sb.append("- ").append(msg).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, sb.toString());
                    } else {

                        JOptionPane.showMessageDialog(null, "Coming soon.");
                    }
                }
                case 2 -> {
                    // Exit the application
                    JOptionPane.showMessageDialog(null, "Exiting QuickChat. Goodbye!");
                    return;
                }
                default -> {
                    // Invalid menu choice
                    JOptionPane.showMessageDialog(null, "Invalid option.");
                }
            }
        }
    }

    // Method to handle the process of sending a message
    private static void sendMessageProcedure() {
        // Generate a random 10-digit message ID
        long messageID = 1_000_000_000L + (long) (Math.random() * 9_000_000_000L);

        // Immediately increment message count
        sentMessageCount++;

        // Ask for recipient's number and validate it
        String recipient;
        while (true) {
            recipient = JOptionPane.showInputDialog("Enter Recipient’s international cell number (max 10 characters):");
            if (recipient != null && recipient.matches("^\\+\\d{10,15}$") && recipient.startsWith("+")) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid number. Must include '+' and be at most 10 characters.");
            }
        }

        // Get the message text from user
        String messageText = JOptionPane.showInputDialog("Enter your message (max 250 characters):");
        if (messageText.length() > 250) {
            JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
            return;
        }

        // Create message hash using ID, message count, first and last words
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        // Construct the hash value (
        String messageHash = (String.valueOf(messageID).substring(0, 2) + ":" + sentMessageCount + ":" + firstWord + lastWord).toUpperCase();

        // Ask user what to do with this message
        String[] options = {"Send Message", "Disregard Message", "Store Message"};
        int action = JOptionPane.showOptionDialog(
                null,
                "Choose an action:",
                "Message Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Handle user's action
        switch (action) {
            case 0 -> {
                // User chose to send the message
                messageList.add("SENT -> ID: " + messageID + ", To: " + recipient + ", Hash: " + messageHash);
                JOptionPane.showMessageDialog(null, "Message sent successfully.");
            }
            case 1 -> {
                // User chose to discard the message
                JOptionPane.showMessageDialog(null, "Message discarded.");
            }
            case 2 -> {
                // User chose to store the message for later
                messageList.add("PENDING -> ID: " + messageID + ", To: " + recipient + ", Hash: " + messageHash);
                JOptionPane.showMessageDialog(null, "Message stored for later.");
            }
            default -> {
                // Invalid action selection
                JOptionPane.showMessageDialog(null, "Invalid option. Message not saved.");
            }
        }
    }
}
