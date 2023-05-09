package hexlet.code.formatter;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

/**
 * Created by Ilya Tankov on 5/9/2023.
 **/
public class StylishFormatter {

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
                default -> {

                }
            }
        });

        stringBuilder.append("}");
        return stringBuilder.toString()
            .replaceAll("\"", "")
            .replaceAll(",", ", ")
            .strip();
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
