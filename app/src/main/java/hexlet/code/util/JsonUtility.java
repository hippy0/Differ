package hexlet.code.util;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by Ilya Tankov on 5/8/2023.
 **/
public class JsonUtility {

    public static boolean isPlainObject(JsonNode node) {
        if (node == null) {
            return false;
        }
        return node.toPrettyString().contains("{");
    }
}
