package Notifications;

import Database.DatabaseInstance;
import MainFiles.FilePathInstance;
import MainFiles.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class NotificationsReader {
    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","julia");
    Connection conn = database.getConn();
    User currentUser = User.getInstance();

    public List<String> readNotifications() {

        String query = "SELECT username_send, timestamp, type FROM Notification WHERE username_received = ?";

        ArrayList<String> notifications = new ArrayList<>();

        try (PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, currentUser.getUsername());

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String type = resultSet.getString("type");
                    String username_send = resultSet.getString("username_send");
                    Timestamp timestamp = resultSet.getTimestamp("timestamp");

                    String notify = username_send;

                    if (type.equals("follow")) {

                        notify += " followed you - ";

                    } else if (type.equals("like")) {

                        notify += " liked your post - ";

                    } else {
                        System.out.println("wrong type of notification!");
                        continue; // Skip this notification
                    }

                    notify += calculateTime(timestamp);
                    notifications.add(notify);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("SQL query went wrong.");
            }

            return notifications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String calculateTime(Timestamp timestamp) {

        StringBuilder builder = new StringBuilder();

        Instant timestampInstant = timestamp.toInstant();
        Instant currentTime = Instant.now();

        Duration duration = Duration.between(timestampInstant, currentTime);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        if (days > 0) {
            builder.append(days).append(" days ");
        }
        if (hours > 0) {
            builder.append(hours).append(" hours ");
        }
        if (minutes > 0) {
            builder.append(minutes).append(" minutes ");
        }
        if (seconds > 0) {
            builder.append(seconds).append(" seconds ");
        }

        if (builder.length() > 0) {
            builder.append("ago.");
        } else {
            builder.append("just now.");
        }

        return builder.toString().trim();
    }

}
