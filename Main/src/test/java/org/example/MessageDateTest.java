package org.example;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageDateTest {

    MessageDate md = new MessageDate();

    @Test
    void checkMessageID() {
        assertTrue(md.checkMessageID("ABCDEFGHIJ"));
        assertFalse(md.checkMessageID("ABC"));
        assertFalse(md.checkMessageID(null));
    }

    @Test
    void checkRecipientCell() {
        assertTrue(md.checkRecipientCell("+12345678901"));
        assertFalse(md.checkRecipientCell("123456"));
        assertFalse(md.checkRecipientCell(null));
        assertFalse(md.checkRecipientCell("+123"));
    }

    @Test
    void createMessageHash() {
        String hash = md.createMessageHash("AB12345678", 1, "Hello world!");
        assertEquals("AB:1:HELLO WORLD!", hash);

        // One-word message
        String singleWordHash = md.createMessageHash("CD12345678", 2, "Test");
        assertEquals("CD:2:TEST TEST", singleWordHash);

        // Invalid cases
        assertEquals("INVALID_HASH", md.createMessageHash(null, 1, "Hello"));
        assertEquals("INVALID_HASH", md.createMessageHash("A", 1, "Hello"));
        assertEquals("INVALID_HASH", md.createMessageHash("AB12345678", 1, "   "));
    }

    @Test
    void sentMessage() {
        int before = md.returnTotalMessages();
        String result = md.sentMessage("MSG1234567", "+12345678901", "Test message", "HASH123");
        int after = md.returnTotalMessages();

        assertEquals("Message sent.", result);
        assertEquals(before + 1, after);
    }

    @Test
    void printMessages() {
        // This will open a JOptionPane, but no assertion here
        md.printMessages(); // Should show "No messages" or stored ones
    }

    @Test
    void returnTotalMessages() {
        int initialCount = md.returnTotalMessages();
        md.sentMessage("MSG0000001", "+10000000000", "Text", "HASH");
        assertEquals(initialCount + 1, md.returnTotalMessages());
    }

    @Test
    void storeMessage() {
        String result = md.storeMessage("STORED1234", "+19999999999", "Store this message", "HASH987");
        assertEquals("Message stored.", result);

        // Check the file was written
        File file = new File("storedMessages.json");
        assertTrue(file.exists());
        try {
            String content = Files.readString(file.toPath());
            assertTrue(content.contains("STORED1234"));
            assertTrue(content.contains("Store this message"));
        } catch (IOException e) {
            fail("Failed to read the storedMessages.json file.");
        }
    }

    @Test
    void getMessageList() {
        md.sentMessage("MSG8765432", "+14444444444", "Test list", "HASH456");
        List<MessageDate.messageData> list = md.getMessageList();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(list.getLast().getId().equals("MSG8765432"));
    }
}
