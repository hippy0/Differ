package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.differ.Differ;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by Ilya Tankov on 5/7/2023
 **/
class DifferTest {

    @Test
    @DisplayName("Differ.generate() method test")
    void testDiffer() throws JsonProcessingException {
        String actual = Differ.generate("src/test/resources/file1.json", "src/test/resources/file2.json");
        String expected = """
            {
              - follow: false
                host: hexlet.io
              - proxy: 123.234.53.22
              - timeout: 50
              + timeout: 20
              + verbose: true
            }""";

        assertEquals(actual, expected);
    }
}
