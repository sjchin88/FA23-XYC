package util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    // 3. Hash the byte[] or binary string
    public static String hashWithSHA256(byte[] inputBytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(inputBytes);
            return bytesToHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot find SHA-256 algorithm", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        try {
            // Path to the image
            String imagePath = "files/nmtb.png";

            // Convert image to byte array
            byte[] imageBytes = ImageToBinary.readImageAsBytes(imagePath);

            // Hash the byte array
            String hashedValue = hashWithSHA256(imageBytes);

            System.out.println(hashedValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
