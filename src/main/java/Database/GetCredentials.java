package Database;

import MainFiles.ImageLikesManager;
import Utils.CredentialsDataType;
import Utils.PostDataType;

import java.io.IOException;
import java.sql.*;

public class GetCredentials {

    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","marcel");
    Connection conn = database.getConn();

    public String getUserData(String username, CredentialsDataType typeOfData) {
        String result = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select " + typeOfData.getColumnName() +
                            " from User_profile " +
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
        int imageId;
//        System.out.println(filePath);
//        System.out.println(dataType);
        try {
            ImageLikesManager manager = new ImageLikesManager();
            imageId = manager.getPostId(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT u.username, p.post_desc, p.post_timestamp, " +
                            "(SELECT COUNT(*) FROM Likes WHERE post_id = p.post_id) AS likes " +
                            "FROM User_profile u " +
                            "JOIN Post p ON u.username = p.username " +
                            "WHERE p.post_id = ?");
            preparedStatement.setInt(1, imageId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String columnName = dataType.getColumnName();
                if ("likes".equals(columnName)) {
                    // Use getInt for numerical values
                    return String.valueOf(rs.getInt("likes"));
                } else {
                    return rs.getString(columnName);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
