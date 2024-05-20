package MainFiles;

import Database.FolowingDatabase;
import Database.PostDatabase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ImageDetailsReader {


    User currentUser = User.getInstance();

    public String[][] createSampleData() {
        FolowingDatabase followersDatabase = new FolowingDatabase();
        List<String> followedUsers = followersDatabase.getFollowedUsers(currentUser.getUsername());

        PostDatabase postDatabase  = new PostDatabase();
        List<String[]> data = postDatabase.getHomeUiPfp(followedUsers);

        String[][] sampleData = new String[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            sampleData[i] = data.get(i);
        }
        return sampleData;
    }

}
