package Notifications;

import Database.DatabaseInstance;
import MainFiles.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class NotificationsCreator {

    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","julia");
    Connection conn = database.getConn();
    User currentUser = User.getInstance();
    public void newNotification(String notifiedUser, String type){
        Instant timestamp = Instant.now();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO Notifications (username_send, username_received, Timestamp, type)" +
                            "VALUES (?, ?, ?, ?)");

            preparedStatement.setString(1, currentUser.getUsername());
            preparedStatement.setString(2, notifiedUser);
            preparedStatement.setTimestamp(3, Timestamp.from(timestamp));
            preparedStatement.setString(4, type);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Notified successfully.");
            } else {
                System.out.println("Something went wrong");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
