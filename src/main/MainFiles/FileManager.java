package MainFiles;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages file operations for storing and retrieving data.
 */
class FileManager {
    private String filePath;

    /**
     * Constructs a FileManager for handling file operations.
     *
     * @param filePath The path of the file to be managed.
     */
    FileManager(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads likes information from a file and returns a map of image IDs to sets of usernames who liked them.
     * The file is expected to have each line formatted as "imageID:username1,username2,...,usernameN".
     *
     * @return A map where each key is an image ID and each value is a set of usernames.
     * @throws IOException If an I/O error occurs when reading from the file.
     */
    Map<String, Set<String>> readLikes() throws IOException {
        Map<String, Set<String>> likesMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String imageID = parts[0];
                Set<String> users = Arrays.stream(parts[1].split(","))
                        .collect(Collectors.toSet());
                likesMap.put(imageID, users);
            }
        }
        return likesMap;
    }
}
