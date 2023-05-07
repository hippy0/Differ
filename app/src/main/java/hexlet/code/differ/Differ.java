package hexlet.code.differ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Ilya Tankov on 4/18/2023.
 **/
public class Differ {

    public static String generate(String filePathOne, String filePathTwo)
        throws JsonProcessingException {
        String fileOne = parseFile(filePathOne);
        String fileTwo = parseFile(filePathTwo);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeOne = objectMapper.readTree(fileOne);
        JsonNode nodeTwo = objectMapper.readTree(fileTwo);
        Map<String, String> comparedNodes = compare(nodeOne, nodeTwo);
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{\n");

        comparedNodes.forEach((key, status) -> {
            switch (status) {
                case "removed" ->
                    appendKey(stringBuilder, nodeOne.get(key).toPrettyString(), key, "-");
                case "unchanged" ->
                    appendKey(stringBuilder, nodeTwo.get(key).toPrettyString(), key, "");
                case "added" ->
                    appendKey(stringBuilder, nodeTwo.get(key).toPrettyString(), key, "+");
                default -> {
                    appendKey(stringBuilder, nodeOne.get(key).toPrettyString(), key, "-");
                    appendKey(stringBuilder, nodeTwo.get(key).toPrettyString(), key, "+");
                }
            }
        });

        stringBuilder.append("}");
        return stringBuilder.toString().replaceAll("\"", "");
    }

    private static void appendKey(StringBuilder stringBuilder, String data, String key,
        String status) {
        int spacesCount = 2;

        if (status.equals("")) {
            spacesCount = 3;
        }

        stringBuilder.append(" ".repeat(spacesCount))
            .append(status)
            .append(" ")
            .append(key)
            .append(": ")
            .append(data)
            .append("\n");
    }

    private static Map<String, String> compare(JsonNode nodeOne, JsonNode nodeTwo) {
        Map<String, String> comparedFields = new TreeMap<>();

        nodeOne.fields().forEachRemaining(field -> {
            if (nodeTwo.get(field.getKey()) != null) {
                if (nodeTwo.get(field.getKey()).toPrettyString()
                    .equals(field.getValue().toPrettyString())) {
                    comparedFields.put(field.getKey(), "unchanged");
                } else {
                    comparedFields.put(field.getKey(), "changed");
                }
            }

            if (nodeTwo.get(field.getKey()) == null) {
                comparedFields.put(field.getKey(), "removed");
            }
        });

        nodeTwo.fields().forEachRemaining(field -> {
            if (nodeOne.get(field.getKey()) == null) {
                comparedFields.put(field.getKey(), "added");
            }
        });

        return comparedFields;
    }

    private static String parseFile(String filePath) {
        Path path = Paths.get(filePath).toAbsolutePath().normalize();

        try {
            return Files.readString(path);
        } catch (IOException exception) {
            System.out.println("File \"" + filePath + "\" does not exists.");
        }

        return null;
    }
}
