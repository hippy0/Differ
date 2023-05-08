package hexlet.code.util;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

/**
 * Created by Ilya Tankov on 5/8/2023.
 **/
public class Formatter {

    public static String jsonFormat(Map<String, String> comparedNodes, JsonNode nodeOne,
        JsonNode nodeTwo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");

        int nestedLevel = 0;

        comparedNodes.forEach((key, status) -> {
            switch (status) {
                case "removed" -> {
                    String indent = StringUtil.makeIndent(nestedLevel);
                    stringBuilder.append(
                        indent + "  - " + key + ": " + StringUtil.jsonToString(nodeOne.get(key),
                            nestedLevel + 1));
                    stringBuilder.append("\n");
                }
                case "unchanged" -> {
                    String indent = StringUtil.makeIndent(nestedLevel);
                    stringBuilder.append(
                        indent + "    " + key + ": " + StringUtil.jsonToString(nodeTwo.get(key),
                            nestedLevel + 1));
                    stringBuilder.append("\n");
                }
                case "added" -> {
                    String indent = StringUtil.makeIndent(nestedLevel);
                    stringBuilder.append(
                        indent + "  + " + key + ": " + StringUtil.jsonToString(nodeTwo.get(key),
                            nestedLevel + 1));
                    stringBuilder.append("\n");
                }
                case "changed" -> {
                    String indentRemoved = StringUtil.makeIndent(nestedLevel);
                    stringBuilder.append(
                        indentRemoved + "  - " + key + ": " + StringUtil.jsonToString(
                            nodeOne.get(key), nestedLevel + 1));
                    stringBuilder.append("\n");

                    String indentAdded = StringUtil.makeIndent(nestedLevel);
                    stringBuilder.append(
                        indentAdded + "  + " + key + ": " + StringUtil.jsonToString(
                            nodeTwo.get(key), nestedLevel + 1));
                    stringBuilder.append("\n");
                }
            }
        });

        stringBuilder.append("\n}");
        return stringBuilder.toString()
            .replaceAll("\"", "")
            .replaceAll(",", ", ");
    }

    public static String stylishFormat(Map<String, String> comparedNodes, JsonNode nodeOne,
        JsonNode nodeTwo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");

        comparedNodes.forEach((key, status) -> {
            switch (status) {
                case "removed" ->
                    appendKey(stringBuilder, nodeOne.get(key).toString().replaceAll(":", "="), key,
                        "-");
                case "unchanged" ->
                    appendKey(stringBuilder, nodeTwo.get(key).toString().replaceAll(":", "="), key,
                        "");
                case "added" ->
                    appendKey(stringBuilder, nodeTwo.get(key).toString().replaceAll(":", "="), key,
                        "+");
                case "changed" -> {
                    appendKey(stringBuilder, nodeOne.get(key).toString().replaceAll(":", "="), key,
                        "-");
                    appendKey(stringBuilder, nodeTwo.get(key).toString().replaceAll(":", "="), key,
                        "+");
                }
            }
        });

        stringBuilder.append("}");
        return stringBuilder.toString()
            .replaceAll("\"", "")
            .replaceAll(",", ", ");
    }

    public static String plainFormat(Map<String, String> comparedNodes, JsonNode nodeOne,
        JsonNode nodeTwo) {
        StringBuilder stringBuilder = new StringBuilder().append("\n");

        comparedNodes.forEach((key, status) -> {
            switch (status) {
                case "unchanged" -> {
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
                        .append(oldValue).append(" to ").append(newValue).append("\n");
                }
                case "removed" ->
                    stringBuilder.append("Property '").append(key).append("' was removed")
                        .append("\n");
                case "added" -> {
                    String newValue = nodeTwo.get(key).toString().replace("\"", "'");

                    if (nodeTwo.get(key).toString().contains("[") || nodeTwo.get(key).toString()
                        .contains("{")) {
                        newValue = "[complex value]";
                    }

                    stringBuilder.append("Property '").append(key)
                        .append("' was added with value: ")
                        .append(newValue)
                        .append("\n");
                }
            }
        });

        return stringBuilder.toString().strip();
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
}
