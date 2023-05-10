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
        String lineSeparator = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{" + lineSeparator);

        int nestedLevel = 0;

        comparedNodes.forEach((key, status) -> {
            switch (status) {

                case "removed" -> {
                    String indent = StringUtil.makeIndent(nestedLevel);
                    stringBuilder.append(
                        indent + "  - " + key + ": " + StringUtil.jsonToString(nodeOne.get(key),
                            nestedLevel + 1));

                    stringBuilder.append(lineSeparator);
                }
                case "unchanged" -> {
                    String indent = StringUtil.makeIndent(nestedLevel);
                    stringBuilder.append(
                        indent + "    " + key + ": " + StringUtil.jsonToString(nodeTwo.get(key),
                            nestedLevel + 1));
                    stringBuilder.append(lineSeparator);
                }
                case "added" -> {
                    String indent = StringUtil.makeIndent(nestedLevel);
                    stringBuilder.append(
                        indent + "  + " + key + ": " + StringUtil.jsonToString(nodeTwo.get(key),
                            nestedLevel + 1));
                    stringBuilder.append(lineSeparator);
                }
                case "changed" -> {
                    String indentRemoved = StringUtil.makeIndent(nestedLevel);
                    stringBuilder.append(
                        indentRemoved + "  - " + key + ": " + StringUtil.jsonToString(
                            nodeOne.get(key), nestedLevel + 1));
                    stringBuilder.append(lineSeparator);

                    String indentAdded = StringUtil.makeIndent(nestedLevel);
                    stringBuilder.append(
                        indentAdded + "  + " + key + ": " + StringUtil.jsonToString(
                            nodeTwo.get(key), nestedLevel + 1));
                    stringBuilder.append(lineSeparator);
                }
                default -> {

                }
            }
        });

        stringBuilder.append(lineSeparator).append("}");
        return stringBuilder.toString()
            .replaceAll("\"", "")
            .replaceAll(",", ", ")
            .strip();
    }
}
