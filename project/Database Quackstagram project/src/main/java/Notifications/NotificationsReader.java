package Notifications;
import MainFiles.FilePathInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads and processes notification data for the current user.
 */
public class NotificationsReader {
    private String currentUsername;
    private final FilePathInstance pathFile = FilePathInstance.getInstance();

    /**
     * Constructor for NotificationsReader.
     * Initializes the reader and reads the current user's username.
     */
    public NotificationsReader() {
        readCurrentUsername();
    }

    /**
     * Reads the current user's username from the users file.
     */
    private void readCurrentUsername() {
        try (BufferedReader reader = Files.newBufferedReader(pathFile.usersPath())) {
            String line = reader.readLine();
            if (line != null) {
                currentUsername = line.split(":")[0].trim();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads and returns a list of notifications relevant to the current user.
     *
     * @return A list of string arrays, each representing a notification's details.
     */
    public List<String[]> readNotifications() {
        List<String[]> notifications = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(pathFile.notificationsPath())) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].trim().equals(currentUsername)) {
                    notifications.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notifications;
    }
}
