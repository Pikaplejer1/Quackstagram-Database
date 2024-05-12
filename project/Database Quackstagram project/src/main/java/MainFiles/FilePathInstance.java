package MainFiles;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Singleton class providing an instance for managing file paths used throughout the application.
 * This class ensures a single, consistent point of reference for any file paths required.
 */
public class FilePathInstance {
    private static FilePathInstance instance;
    private FilePathInstance(){}

    /**
     * Gets the single instance of FilePathInstance.
     * If the instance doesn't already exist, it initializes a new one.
     *
     * @return The singleton instance of FilePathInstance.
     */
    public static FilePathInstance getInstance() {
        if (instance == null) {
            instance = new FilePathInstance();
        }
        return instance;
    }

    public Path credentialsPath(){

        Path credentialsFilePath = Paths.get("data", "credentials.txt");
        return credentialsFilePath;
    }

    public Path logoPath(){

        Path logoFilePath = Paths.get("img", "logos/DACS.png");
        return logoFilePath;
    }

    public Path usersPath(){

        Path userFilePath = Paths.get("data", "users.txt");
        return userFilePath;
    }

    public Path notificationsPath(){

        Path notificationsFilePath = Paths.get("data", "notifications.txt");
        return notificationsFilePath;
    }

    public Path followingPath(){

        Path followingFilePath = Paths.get("data", "following.txt");
        return followingFilePath;
    }

    public  Path imageDetailsPath(){

        Path imageDetailsFilePath = Paths.get("data", "image_details.txt");
        return imageDetailsFilePath;
    }

    public Path addIconPath(){

        Path addIconFilePath = Paths.get("img", "icons/add.png");
        return addIconFilePath;
    }

    public Path heartIconPath(){

        Path heartIconFilePath = Paths.get("img", "icons/heart.png");
        return heartIconFilePath;
    }

    public Path homeIconPath(){

        Path homeIconFilePath = Paths.get("img", "icons/home.png");
        return homeIconFilePath;
    }

    public Path profileIconPath(){

        Path profileIconFilePath = Paths.get("img", "icons/profile.png");
        return profileIconFilePath;
    }

    public Path searchIconPath(){

        Path searchIconFilePath = Paths.get("img", "icons/search.png");
        return searchIconFilePath;
    }

    public  String credentialsNamePath(){

        String credentialsNamePath = "data/credentials.txt";
        return credentialsNamePath;
    }

    public String followingNamePath(){

        String followingNamePath = "data/following.txt";
        return followingNamePath;
    }

    public String usersNamePath(){

        String usersNamePath = "data/users.txt";
        return usersNamePath;
    }

    public String notificationsNamePath(){

        String notificationsNamePath = "data/notifications.txt";
        return notificationsNamePath;
    }

    public String imageDetailsNamePath(){

        String imageDetailsNamePath = "data/image_details.txt";
        return imageDetailsNamePath;
    }

    public  String logoIconNamePath(){

        String logoIconNamePath = "img/logos/DACS.png";
        return logoIconNamePath;
    }


    public String storageProfileNamePath(){

        String storageProfileNamePath = "img/storage/profile";
        return storageProfileNamePath;
    }

    public  String profileIconNamePath(){

        String profileIconNamePath = "img/icons/profile.png";
        return profileIconNamePath;
    }

    public  String homeIconNamePath(){

        String homeIconNamePath = "img/icons/home.png";
        return homeIconNamePath;
    }

    public  String heartIconNamePath(){

        String heartIconNamePath = "img/icons/heart.png";
        return heartIconNamePath;
    }

    public  String searchIconNamePath(){

        String searchIconNamePath = "img/icons/search.png";
        return searchIconNamePath;
    }

    public  String addIconNamePath(){

        String addIconNamePath = "img/icons/add.png";
        return addIconNamePath;
    }

    public  String uploadedNamePath(){

        String uploadedNamePath = "img/uploaded";
        return uploadedNamePath;
    }

}
