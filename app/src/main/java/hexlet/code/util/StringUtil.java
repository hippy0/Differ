package hexlet.code.util;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya Tankov on 5/8/2023.
 **/
public class StringUtil {

    public static String makeIndent(int level) {
        return "    ".repeat(level);
    }

    public static String jsonToString(JsonNode node, int nestedLevel) {
        if (!JsonUtility.isPlainObject(node)) {
            return node.toString();
        }

        String indent = makeIndent(nestedLevel + 1);
        String closedBracketIndent = makeIndent(nestedLevel);
        List<String> result = new ArrayList<>();

        node.fieldNames().forEachRemaining(key -> {
            result.add(indent + key + ": " + jsonToString(node.get(key), nestedLevel + 1));
        });

        return "{\n" + String.join("\n", result) + "\n" + closedBracketIndent + "}";
    }

}
