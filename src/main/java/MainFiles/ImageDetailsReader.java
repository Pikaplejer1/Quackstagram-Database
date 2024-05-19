package MainFiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ImageDetailsReader {

    protected final FilePathInstance pathFile = FilePathInstance.getInstance();

    User currentUser = User.getInstance();

    //TODO nie mam pojecia co to gowno robi
    public String[][] createSampleData() {
        String current = currentUser.getUsername();
        String followedUsers = getFollowedUsers(current);

        String[][] tempData = new String[100][]; // Assuming a maximum of 100 posts
        int count = 0;

        try (BufferedReader reader = Files.newBufferedReader(pathFile.imageDetailsPath())) {
            String line;
            while ((line = reader.readLine()) != null && count < tempData.length) {
                String[] details = line.split(", ");
                String imagePoster = details[1].split(": ")[1];
                if (followedUsers.contains(imagePoster)) {
                    String imageID = details[0].split(": ")[1];
                    String imagePath = findImagePath(imageID);

                    String description = details[2].split(": ")[1];
                    String likes = "Likes: " + details[4].split(": ")[1];

                    tempData[count++] = new String[]{imagePoster, description, likes, imagePath};
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[][] sampleData = new String[count][];
        System.arraycopy(tempData, 0, sampleData, 0, count);

        return sampleData;
    }

    //TODO zamienic na czytanie z bazy danych chyba ze od razu tam gdzie potrzebne to do wyjebania
    private String findImagePath(String imageID) {
        String[] extensions = {".png", ".jpg", ".jpeg"};
        for (String ext : extensions) {
            String path = "img/uploaded/" + imageID + ext;
            File file = new File(path);
            if (file.exists()) {
                return path;
            }
        }
        return null; // or a default image path
    }

    //TODO po co to?
    protected String extractImageId(String path) {
        String regex = "img/uploaded/(.*)\\..*";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(path);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    //TODO jesli wgl potrzebne to juz istnieje w profileDetailsManager

    protected String getFollowedUsers(String currentUser) {
        String followedUsers = "";
        try {
            followedUsers = Files.lines(Paths.get("data", "following.txt"))
                    .filter(line -> line.startsWith(currentUser + ":"))
                    .findFirst()
                    .map(line -> line.split(":")[1].trim())
                    .orElse("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return followedUsers;
    }

    //TODO chyba do usuniecia
    protected void updateImageDetailsFile(Path detailsPath, String newContent) {
        try (BufferedWriter writer = Files.newBufferedWriter(detailsPath)) {
            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
