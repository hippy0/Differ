package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by Ilya Tankov on 5/7/2023.
 **/
class DifferTest {

    @Test
    @DisplayName("Differ.generate() with unexistence file")
    void testDifferWithException() {
        assertEquals(
            Differ.generate("src/test/resources/fixtures/fileDoesntExists.json",
                "src/test/resources/fixtures/file2.json",
                "stylish"), "error");
    }

    @Test
    @DisplayName("Differ.generate() flat JSON file method test")
    void testDifferFlatJsonFile() throws IOException {
        String expected = Differ.generate(
            "src/test/resources/fixtures/file1.json",
            "src/test/resources/fixtures/file2.json",
            "json");

        String actual = Files.readString(Paths.get("src/test/resources/fixtures/result_json.txt"), StandardCharsets.UTF_8);

        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Differ.generate() flat YAML file method test")
    void testDifferFlatYamlFile() throws IOException {
        String expected = Differ.generate(
            "src/test/resources/fixtures/file1.yml",
            "src/test/resources/fixtures/file2.yml",
            "stylish");

        String actual = Files.readString(Paths.get("src/test/resources/fixtures/result_stylish.txt"), StandardCharsets.UTF_8);

        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Differ.generate nested YML file method test")
    void testDifferNestedYmlFile() throws IOException {
        String expected = Differ.generate(
            "src/test/resources/fixtures/file1.yml",
            "src/test/resources/fixtures/file2.yml",
            "plain");

        String actual = Files.readString(Paths.get("src/test/resources/fixtures/result_plain.txt"), StandardCharsets.UTF_8);

        assertEquals(actual, expected);
    }
}
