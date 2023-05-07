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

    public static String generate(String filePathOne, String filePathTwo) {
        String fileOne = parseFile(filePathOne);
        String fileTwo = parseFile(filePathTwo);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode nodeOne = objectMapper.readTree(fileOne);
            JsonNode nodeTwo = objectMapper.readTree(fileTwo);

            Map<String, String> comparedNodes = compare(nodeOne, nodeTwo);

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("{\n");

            comparedNodes.forEach((key, status) -> {
                if (status.equals("removed")) {
                    stringBuilder.append("  - ")
                        .append(key)
                        .append(": ")
                        .append(nodeOne.get(key).toString())
                        .append("\n");
                }

                if (status.equals("unchanged")) {
                    stringBuilder.append("    ")
                        .append(key)
                        .append(": ")
                        .append(nodeOne.get(key).toString())
                        .append("\n");
                }

                if (status.equals("added")) {
                    stringBuilder.append("  + ")
                        .append(key)
                        .append(": ")
                        .append(nodeTwo.get(key).toString())
                        .append("\n");
                }

                if (status.equals("changed")) {
                    stringBuilder.append("  - ")
                        .append(key)
                        .append(": ")
                        .append(nodeOne.get(key).toString())
                        .append("\n");

                    stringBuilder.append("  + ")
                        .append(key)
                        .append(": ")
                        .append(nodeTwo.get(key).toString())
                        .append("\n");
                }
            });

            stringBuilder.append("}");

            return stringBuilder.toString().replaceAll("\"", "");
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    private static Map<String, String> compare(JsonNode nodeOne, JsonNode nodeTwo) {
        Map<String, String> comparedFields = new TreeMap<>();

        nodeOne.fields().forEachRemaining(field -> {
            if (nodeTwo.get(field.getKey()) != null) {
                if (nodeTwo.get(field.getKey()).toPrettyString().equals(field.getValue().toPrettyString())) {
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
