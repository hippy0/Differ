package hexlet.code.differ;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.util.Formatter;
import hexlet.code.util.Parser;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Ilya Tankov on 4/18/2023.
 **/
public class Differ {

    public static String generate(String filePathOne, String filePathTwo, String format)
        throws IOException {
        String fileOne = Parser.parseFile(filePathOne);
        String fileTwo = Parser.parseFile(filePathTwo);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeOne = objectMapper.readTree(fileOne);
        JsonNode nodeTwo = objectMapper.readTree(fileTwo);
        Map<String, String> comparedNodes = compare(nodeOne, nodeTwo);

        switch (format) {
            case "stylish" -> {
                return Formatter.stylishFormat(comparedNodes, nodeOne, nodeTwo);
            }
            case "plain" -> {
                return Formatter.plainFormat(comparedNodes, nodeOne, nodeTwo);
            }
            default -> {
                return "I can't find a format \"" + format + "\", try something from this list: \"stylish, plain\"";
            }
        }
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
}
