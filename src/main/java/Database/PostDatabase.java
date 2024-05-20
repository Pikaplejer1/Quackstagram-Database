package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostDatabase {
    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","julia");
    Connection conn = database.getConn();
    public List<String[]> getHomeUiPfp(List<String> folowedUsers){
        List<String[]> data = new ArrayList<>();
        if(folowedUsers.isEmpty()) return data;
        String clause = folowedUsers.stream()
                .map(user -> "'" + user + "'")
                .collect(Collectors.joining(","));
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT p.username, p.post_desc, p.post_photo_dir, COUNT(l.username_who_liked) AS likes " +
                    "FROM Post p " +
                    "LEFT JOIN Likes l ON p.post_id = l.post_id " +
                    "WHERE p.username IN ? " +
                    "GROUP BY p.post_id " +
                    "ORDER BY p.post_timestamp DESC " +
                    "LIMIT 100");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                String imagePoster = rs.getString("username");
                String description = rs.getString("post_desc");
                String imagePath = rs.getString("post_photo_dir");
                String likes = "Likes: " + rs.getInt("likes");
                data.add(new String[]{imagePoster,description,imagePath,likes});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public int getPostCount(String username){
        int imageCount =0;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select count (*)" +
                            "from Post" +
                            "where username = ? ");
            preparedStatement.setString(1,username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
                imageCount++;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return imageCount;
    }

    public int getPostId(){
        int postId =1;
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(
                        "select max(post_id) from Post"
                );
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next())
                    postId = rs.getInt(1) + 1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return postId;
    }
    //post_id INTEGE
    //username VARCH
    //post_photo_dir
    //post_timestamp
    //post_desc VARC

    public void savePfpDir (String username, int pictureId, String desc, String fileName){
        try {
        PreparedStatement preparedStatement = conn.prepareStatement(
              "insert into Post(post_id, username,post_photo_dir, post_desc)" +
                      "values ?,?,?,?");
        preparedStatement.setInt(1,pictureId);
        preparedStatement.setString(2,username);
        preparedStatement.setString(3,"pictures/posts"+fileName);
        preparedStatement.setString(4,desc);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getPostData(String[] postData, String imageId) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT COUNT(username_who_liked) AS likes FROM Likes WHERE post_id = ?");
            preparedStatement.setString(1,imageId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                int likes = rs.getInt("likes");
                postData[2] = "Likes: " + likes;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return postData;
    }
}
