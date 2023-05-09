package hexlet.code.formatter;

import com.fasterxml.jackson.databind.JsonNode;
import hexlet.code.util.StringUtil;
import java.util.Map;

/**
 * Created by Ilya Tankov on 5/9/2023.
 **/
public class JsonFormatter {

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
                default -> {

                }
            }
        });

        stringBuilder.append("\n}");
        return stringBuilder.append("\n").toString()
            .replaceAll("\"", "")
            .replaceAll(",", ", ");
    }
}
