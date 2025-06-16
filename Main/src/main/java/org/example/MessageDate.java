package org.example;

import org.json.JSONObject;
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageDate {

    public static class messageData {
        // Attributes
        private String id;
        private String recipient;
        private String messageText;
        private String hash;
        private String status;

        // Constructor to initialize message details
        public messageData(String id, String recipient, String messageText, String hash, String status) {
            this.id = id;
            this.recipient = recipient;
            this.messageText = messageText;
            this.hash = hash;
            this.status = status;
        }

        //representation of the message
        @Override
        public String toString() {
            return "ID: " + id + ", To: " + recipient + ", Message: " + messageText +
                    ", Hash: " + hash + ", Status: " + status;
        }

        // Getters
        public String getId() {
            return id; }
        public String getRecipient() {
            return recipient; }
        public String getMessageText()
        { return messageText; }
        public String getHash() {
            return hash; }
        public String getStatus() {
            return status; }
        //Setters
        public void setId(String id) {
            this.id = id; }
        public void setRecipient(String recipient) {
            this.recipient = recipient; }
        public void setMessageText(String messageText) {
            this.messageText = messageText; }
        public void setHash(String hash) {
            this.hash = hash; }
        public void setStatus(String status) {
            this.status = status; }
    }

    // List to store message objects
    private final List<messageData> messageList = new ArrayList<>();

    // Validates that message ID is exactly 10 characters long
    public boolean checkMessageID(String messageID) {
        return messageID != null && messageID.length() == 10;
    }

    // Validates that the recipient's phone number matches international format
    public boolean checkRecipientCell(String cellNumber) {
        return cellNumber != null && cellNumber.matches("^\\+\\d{10,15}$");
    }

    // Creates a hash for the message using ID, message number, and message content
    public String createMessageHash(String messageID, int msgNumber, String msgText) {
        // Check for invalid inputs
        if (messageID == null || messageID.length() < 2 || msgText == null || msgText.isBlank()) {
            return "INVALID_HASH";
        }

        // Extract first and last words from the message
        String[] words = msgText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        // Use first two characters of message ID as prefix
        String prefix = messageID.substring(0, 2);

        // Combine to create the hash
        return (prefix + ":" + msgNumber + ":" + firstWord + " " + lastWord).toUpperCase();
    }

    // Sends a message: adds to list and shows confirmation popup
    public String sentMessage(String id, String recipient, String messageText, String hash) {
        messageData msg = new messageData(id, recipient, messageText, hash, "SENT");

        // Show message details in a dialog
        JOptionPane.showMessageDialog(null,
                "Message ID: " + id +
                        "\nHash: " + hash +
                        "\nRecipient: " + recipient +
                        "\nMessage Text: " + messageText,
                "Message Sent",
                JOptionPane.INFORMATION_MESSAGE);

        // Add to message list
        messageList.add(msg);
        return "Message sent.";
    }

    // Displays all stored messages in a dialog
    public void printMessages() {
        if (messageList.isEmpty()) {
            // No messages to show
            JOptionPane.showMessageDialog(null, "No messages to display.");
        } else {
            // Build and display all messages
            StringBuilder output = new StringBuilder("==== Stored Messages ====\n");
            for (messageData msg : messageList) {
                output.append(msg).append("\n-------------------------\n");
            }
            JOptionPane.showMessageDialog(null, output.toString(), "Stored Messages", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Returns the total number of messages stored
    public int returnTotalMessages() {
        return messageList.size();
    }

    // Stores a message and writes it to a JSON file
    public String storeMessage(String id, String recipient, String messageText, String hash) {

        messageData msg = new messageData(id, recipient, messageText, hash, "PENDING");
        messageList.add(msg);

        // Create a JSON object representing the message
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("recipient", recipient);
        json.put("messageText", messageText);
        json.put("hash", hash);
        json.put("status", "PENDING");

            try (FileWriter file = new FileWriter("storedMessages.json", true)) {
                file.write(json.toString() + System.lineSeparator());

            } catch (IOException e) {
                return "Failed to store message: " + e.getMessage();
            }

            JOptionPane.showMessageDialog(null, "Message stored to send later.");
            return "Message stored.";
        }

    // Getter to access the list of stored messages
    public List<messageData> getMessageList() {
        return messageList;
    }
}
