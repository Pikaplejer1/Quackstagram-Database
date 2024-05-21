package MainFiles;

import Database.DatabaseInstance;
import Database.GetCredentials;
import Database.PostDatabase;
import Utils.CredentialsDataType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Reads and sets various details related to a user's profile, including image count, followers, following, and bio.
 */
public class ProfileDetailsReader {

    private final FilePathInstance pathFile = FilePathInstance.getInstance();
    DatabaseInstance database = DatabaseInstance.getInstance("jdbc:mysql://localhost:3306/Quackstagram","root","julia");
    Connection conn = database.getConn();
    //there is no need for user instance!



    public void readImageDetails(User user) {
        PostDatabase postCount = new PostDatabase();
        int imageCount  = postCount.getPostCount(user.getUsername());
        user.setPostCount(imageCount);
    }

    public void readAndSetFollowing(User user) {
        int followingCount = 0;

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(username_followed) AS result FROM followers WHERE username_following = ? ");

            preparedStatement.setString(1, user.getUsername());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    followingCount = resultSet.getInt("result");
                }
            }

            user.setFollowingCount(followingCount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void readAndSetFollowed(User user) {
        int followersCount = 0;

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(username_following) AS result FROM followers WHERE username_followed = ? ");

            preparedStatement.setString(1, user.getUsername());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    followersCount = resultSet.getInt("result");
                }
            }

            user.setFollowersCount(followersCount);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void bioReader(User user) {
        GetCredentials getBio = new GetCredentials();
        user.setBio(getBio.getUserData(user.getUsername(), CredentialsDataType.BIO));
    }
}