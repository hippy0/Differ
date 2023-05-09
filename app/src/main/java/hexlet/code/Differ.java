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

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String generate(String filePathOne, String filePathTwo) {
        JsonNode nodeOne = createNode(filePathOne);
        JsonNode nodeTwo = createNode(filePathTwo);

        if (nodeOne == null || nodeTwo == null) {
            return "error";
        }

        Map<String, String> comparedNodes = compare(nodeOne, nodeTwo);

        return Formatter.format("stylish", comparedNodes, nodeOne, nodeTwo);
    }

    public static String generate(String filePathOne, String filePathTwo, String format) {
        JsonNode nodeOne = createNode(filePathOne);
        JsonNode nodeTwo = createNode(filePathTwo);

        if (nodeOne == null || nodeTwo == null) {
            return "error";
        }

        Map<String, String> comparedNodes = compare(nodeOne, nodeTwo);

        return Formatter.format(format, comparedNodes, nodeOne, nodeTwo);
    }

    private static JsonNode createNode(String filePath) {
        try {
            String parsedFile = Parser.parseFile(filePath);
            return MAPPER.readTree(parsedFile);
        } catch (IOException exception) {
            return null;
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
