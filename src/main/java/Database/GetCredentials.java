package Database;

import Utils.CredentialsDataType;
import Utils.PostDataType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCredentials {

    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","julia");
    Connection conn = database.getConn();

    public String getUserData(String username, CredentialsDataType typeOfData) {
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


    public String getPostData(String filePath, PostDataType dataType){
        String imageId = filePath.split("\\.")[0];
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT u.username, u.bio, p.post_timestamp, " +
                            "(SELECT COUNT(*) FROM Likes WHERE post_id = p.post_id) AS likes " +
                            "FROM User_profile u " +
                            "JOIN Post p ON u.username = p.username " +
                            "WHERE p.post_id = ?");
            preparedStatement.setString(1,imageId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getString(dataType.getColumnName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
