package Login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for hashing strings using SHA-256.
 * This class provides a method to convert a string into its SHA-256 hash equivalent.
 */
public class HashingUtil {

    /**
     * Converts the given input string into a SHA-256 hash.
     *
     * @param input The string to be hashed.
     * @return The SHA-256 hash of the input string.
     * @throws RuntimeException If the hashing algorithm is not supported or encoding issues occur.
     */
    public String toHash(String input) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Perform the hashing
            byte[] hash = digest.digest(input.getBytes("UTF-8"));

            // Convert the byte array to hex format
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            // Propagate as a runtime exception if the algorithm is not available or if there is an encoding issue
            throw new RuntimeException(e);
        }
    }
}
