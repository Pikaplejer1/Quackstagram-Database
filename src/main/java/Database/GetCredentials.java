package Database;

import Utils.DataType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCredentials {

    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","julia");
    Connection conn = database.getConn();

    public String getUserData(String username, DataType typeOfData) {
        String result = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select " + typeOfData.getColumnName() +
                            " from credentials " +
                            "where username = ?;");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString(typeOfData.getColumnName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
