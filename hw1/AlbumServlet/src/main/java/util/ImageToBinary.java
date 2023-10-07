package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageToBinary {

    // Read the image into a byte[]
    public static byte[] readImageAsBytes(String imagePath) throws IOException {
        return Files.readAllBytes(Paths.get(imagePath));
    }

    // Convert byte[] to a binary string (optional)
    public static String byteArrayToBinaryString(byte[] bytes) {
        StringBuilder binaryString = new StringBuilder();
        for (byte b : bytes) {
            String binaryByte = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            binaryString.append(binaryByte);
        }
        return binaryString.toString();
    }
}
