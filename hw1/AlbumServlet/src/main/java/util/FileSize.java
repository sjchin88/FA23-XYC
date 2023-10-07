package util;

import java.io.File;

public class FileSize {

    public static long getFileSize(String filePath){
        File file = new File(filePath);
        return file.length();
    }

}
