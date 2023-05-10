package hexlet.code.formatter;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

/**
 * Created by Ilya Tankov on 5/9/2023.
 **/
public class PlainFormatter {

    public static String plainFormat(Map<String, String> comparedNodes, JsonNode nodeOne,
        JsonNode nodeTwo) {
        StringBuilder stringBuilder = new StringBuilder().append("\n");
        String lineSeparator = System.getProperty("line.separator");

        comparedNodes.forEach((key, status) -> {
            switch (status) {
                case "changed" -> {
                    String newValue = nodeTwo.get(key).toString().replace("\"", "'");
                    String oldValue = nodeOne.get(key).toString().replace("\"", "'");

                    if (nodeTwo.get(key).toString().contains("[") || nodeTwo.get(key).toString()
                        .contains("{")) {
                        newValue = "[complex value]";
                    }

                    if (nodeOne.get(key).toString().contains("[") || nodeOne.get(key).toString()
                        .contains("{")) {
                        oldValue = "[complex value]";
                    }

                    stringBuilder.append("Property '").append(key).append("' was updated. From ")
                        .append(oldValue).append(" to ").append(newValue).append(lineSeparator);
                }
                case "removed" ->
                    stringBuilder.append("Property '").append(key).append("' was removed")
                        .append(lineSeparator);
                case "added" -> {
                    String newValue = nodeTwo.get(key).toString().replace("\"", "'");

                    if (nodeTwo.get(key).toString().contains("[") || nodeTwo.get(key).toString()
                        .contains("{")) {
                        newValue = "[complex value]";
                    }

                    stringBuilder.append("Property '").append(key)
                        .append("' was added with value: ")
                        .append(newValue)
                        .append(lineSeparator);
                }
                default -> {

                }
            }
        });

        return stringBuilder.toString().strip();
    }
}
