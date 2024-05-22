package Database;

import MainFiles.User;

import java.sql.*;

public class UpdateCredentials {
    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","marcel");
    Connection conn = database.getConn();

    public void createUser(String username, String password, String bio) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(
                    "INSERT INTO user_profile(username, bio, password, pfp_dir) VALUES (?, ?, ?, ?)"
            );
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, bio);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, "photos/pfp/" + username + ".png");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateUser(User user, String prevUsername) {
        String username = user.getUsername();
        String bio = user.getBio();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = conn.prepareStatement(
                    "UPDATE user_profile SET bio = ? WHERE username = ?"
            );
            preparedStatement.setString(1, bio);
            preparedStatement.setString(2, prevUsername);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateProfilePicture(String dir, String username) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(
                    "UPDATE user_profile SET pfp_dir = ? WHERE username = ?"
            );
            preparedStatement.setString(1, dir);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
