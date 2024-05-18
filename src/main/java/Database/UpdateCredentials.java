package Database;

import java.sql.*;

public class UpdateCredentials {
    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/quackstagram_db","root","marcel");
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
}