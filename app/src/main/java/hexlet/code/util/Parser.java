package hexlet.code.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Ilya Tankov on 5/8/2023.
 **/
public class Parser {

    public static String parseFile(String filePath) throws IOException {
        Path path = Paths.get(filePath).toAbsolutePath().normalize();

        try {
            return Files.readString(path);
        } catch (Exception exception) {
            throw new IOException("File \"" + filePath + "\" does not exists.");
        }
    }
}
