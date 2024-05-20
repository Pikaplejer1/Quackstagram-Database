package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FolowingDatabase {
    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","julia");
    Connection conn = database.getConn();
    public List<String> getFollowedUsers(String username){
        List<String> followedUser = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(
                    "Select username_followed" +
                        "From Followers " +
                            "Where username_following = ? ");
            preparedStatement.setString(1,username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                followedUser.add(rs.getString("username_followed"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return followedUser;
    }

}
