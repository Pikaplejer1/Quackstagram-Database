package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCredentials {

    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","julia");
    Connection conn = database.connect();
    public String getUserPassword(String username){
        String hashedPassword;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select *" +
                    "from credentials\n" +
                    "where username = ?;");
            preparedStatement.setString(1, username);
            ResultSet password = preparedStatement.executeQuery();
            hashedPassword = password.getString("password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hashedPassword;
    }
}
