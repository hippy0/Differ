package hexlet.code.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Ilya Tankov on 5/8/2023.
 **/
public class Parser {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new YAMLMapper();

    private static String parseFile(String filePath) throws Exception {
        Path path = Paths.get(filePath).toAbsolutePath().normalize();

        return Files.readString(path);
    }

    public static JsonNode createNode(String filePath) throws Exception {
        String fileFormat = filePath.split("\\.")[filePath.split("\\.").length - 1];

        String parsedFile = parseFile(filePath);

        if (fileFormat.equals("json")) {
            return JSON_MAPPER.readTree(parsedFile);
        } else {
            return YAML_MAPPER.readTree(parsedFile);
        }
    }
}
