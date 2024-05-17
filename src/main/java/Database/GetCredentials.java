package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCredentials {

    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/quackstagram_db","root","marcel");
    Connection conn = database.getConn();
    public String getUserPassword(String username){
        String hashedPassword;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select password" +
                            "from credentials" +
                            "where username = ?;");
            preparedStatement.setString(1, username);
            ResultSet password = preparedStatement.executeQuery();
            hashedPassword = password.getString("password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hashedPassword;
    }
    public boolean doesUsernameExists(String username){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "Select EXISTS(select username from credentials where username = ?);"
            );
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}