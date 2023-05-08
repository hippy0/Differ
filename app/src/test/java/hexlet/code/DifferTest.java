package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.differ.Differ;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by Ilya Tankov on 5/7/2023.
 **/
class DifferTest {

    @Test
    @DisplayName("Differ.generate() with unexistence file")
    void testDifferWithException() throws JsonProcessingException {
        assertThrows(
            IOException.class,
            () -> Differ.generate(
                "src/test/resources/fileDoesntExists.json",
                "src/test/resources/file2.json",
                "stylish"),
            "Expected generate() to throw, but it didnt"
        );
    }

    @Test
    @DisplayName("Differ.generate() JSON method test")
    void testDifferJSON() throws IOException {
        String actual = Differ.generate(
            "src/test/resources/file1.json",
            "src/test/resources/file2.json",
            "stylish");

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

    @Test
    @DisplayName("Differ.generate() YAML method test")
    void testDifferYAML() throws IOException {
        String actual = Differ.generate(
            "src/test/resources/file1.yaml",
            "src/test/resources/file2.yaml",
            "stylish");

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
