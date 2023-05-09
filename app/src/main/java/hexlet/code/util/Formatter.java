package hexlet.code.util;

import com.fasterxml.jackson.databind.JsonNode;
import hexlet.code.formatter.JsonFormatter;
import hexlet.code.formatter.PlainFormatter;
import hexlet.code.formatter.StylishFormatter;
import java.util.Map;

/**
 * Created by Ilya Tankov on 5/8/2023.
 **/
public class Formatter {

    public static String format(String format, Map<String, String> nodes, JsonNode nodeOne, JsonNode nodeTwo) {
        return switch (format) {
            case "stylish" -> StylishFormatter.stylishFormat(nodes, nodeOne, nodeTwo);
            case "plain" -> PlainFormatter.plainFormat(nodes, nodeOne, nodeTwo);
            case "json" -> JsonFormatter.jsonFormat(nodes, nodeOne, nodeTwo);
            default -> "I can't find a format \"" + format
                + "\", try something from this list: \"stylish, plain, json\"";
        };
    }
}
