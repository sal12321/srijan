package src.com.banking.passHash;


import java.security.MessageDigest;

public class PasswordHasher {


    // This method takes a password and returns its SHA-256 hash
    public static String hash(String password) {
        try {
            // Get a SHA-256 hasher
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Turn password into bytes and hash it
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert the bytes into a readable hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();

        } catch (Exception e) {
            System.out.println("Something went wrong while hashing the password.");
            return null;
        }
    }
}