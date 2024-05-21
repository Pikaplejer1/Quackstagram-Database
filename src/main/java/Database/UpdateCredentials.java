package Database;

import MainFiles.User;

import java.sql.*;

public class UpdateCredentials {
    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","marcel");
    Connection conn = database.getConn();
    public void createUser(String username, String password, String bio) throws SQLException {
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO User(username, bio, password, photoDir)" +
                            " values ?,?,?,?"
            );
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,bio);
            preparedStatement.setString(3,password);
            preparedStatement.setString(4,"photos/pfp/" + username + "jpg");
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateUser(User user, String prevUsername) {
        String username = user.getUsername();
        String bio = user.getBio();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = conn.prepareStatement(
                    "UPDATE User_profile " +
                            "SET bio = ? " +
                            "WHERE username = ?");

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
                    // Log this exception, but do not rethrow to avoid hiding the original exception.
                    e.printStackTrace();
                }
            }
        }
    }


    public void updateProfilePicture(String dir, String username){
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "update User" +
                            "set pfp_dir = ? " +
                            "where username = ?");
            preparedStatement.setString(1,dir);
            preparedStatement.setString(2,username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}