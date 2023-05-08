package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.util.Formatter;
import hexlet.code.util.JsonUtility;
import hexlet.code.util.Parser;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Ilya Tankov on 4/18/2023.
 **/
public class Differ {

    public static String generate(String filePathOne, String filePathTwo)
        throws IOException {
        String fileOne = Parser.parseFile(filePathOne);
        String fileTwo = Parser.parseFile(filePathTwo);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeOne = objectMapper.readTree(fileOne);
        JsonNode nodeTwo = objectMapper.readTree(fileTwo);

        Map<String, String> comparedNodes = compare(nodeOne, nodeTwo);

        return Formatter.stylishFormat(comparedNodes, nodeOne, nodeTwo);
    }

    public static String generate(String filePathOne, String filePathTwo, String format)
        throws IOException {
        String fileOne = Parser.parseFile(filePathOne);
        String fileTwo = Parser.parseFile(filePathTwo);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeOne = objectMapper.readTree(fileOne);
        JsonNode nodeTwo = objectMapper.readTree(fileTwo);

        Map<String, String> comparedNodes = compare(nodeOne, nodeTwo);

        switch (format) {
            case "json" -> {
                return Formatter.jsonFormat(comparedNodes, nodeOne, nodeTwo);
            }
            case "stylish" -> {
                return Formatter.stylishFormat(comparedNodes, nodeOne, nodeTwo);
            }
            case "plain" -> {
                return Formatter.plainFormat(comparedNodes, nodeOne, nodeTwo);
            }
            default -> {
                return "I can't find a format \"" + format
                    + "\", try something from this list: \"stylish, plain\"";
            }
        }
    }

    private static Map<String, String> compare(JsonNode nodeOne, JsonNode nodeTwo) {
        Map<String, String> comparedFields = new TreeMap<>();
        Set<String> keys = new TreeSet<>();

        nodeOne.fields().forEachRemaining(field -> keys.add(field.getKey()));
        nodeTwo.fields().forEachRemaining(field -> keys.add(field.getKey()));

        for (String key : keys) {
            JsonNode oldValue = nodeOne.get(key);
            JsonNode newValue = nodeTwo.get(key);

            if (JsonUtility.isPlainObject(oldValue) && JsonUtility.isPlainObject(newValue)) {
                comparedFields.put(key, "nested");
            }

            if (oldValue != null && newValue != null) {
                if (oldValue.toPrettyString().equals(newValue.toPrettyString())) {
                    comparedFields.put(key, "unchanged");
                } else {
                    comparedFields.put(key, "changed");
                }
            }

            if (oldValue == null && newValue != null) {
                comparedFields.put(key, "added");
            }

            if (oldValue != null && newValue == null) {
                comparedFields.put(key, "removed");
            }
        }

        return comparedFields;
    }
}
