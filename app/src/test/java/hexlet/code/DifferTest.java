package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThrows(Exception.class, () -> Differ.generate("src/test/resources/fixtures/fileDoesntExists.json",
            "src/test/resources/fixtures/file2.json",
            "stylish"));
    }

    @Test
    @DisplayName("Differ.generate() flat JSON file method test")
    void testDifferFlatJsonFile() throws Exception {
        String expected = Differ.generate(
            "src/test/resources/fixtures/file1.json",
            "src/test/resources/fixtures/file2.json",
            "json");

        String actual = Files.readString(Paths.get("src/test/resources/fixtures/result_json.txt"), StandardCharsets.UTF_8);

        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Differ.generate() flat YAML file method test")
    void testDifferFlatYamlFile() throws Exception {
        String expected = Differ.generate(
            "src/test/resources/fixtures/file1.yml",
            "src/test/resources/fixtures/file2.yml",
            "stylish");

        String actual = Files.readString(Paths.get("src/test/resources/fixtures/result_stylish.txt"), StandardCharsets.UTF_8);

        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Differ.generate nested YML file method test")
    void testDifferNestedYmlFile() throws Exception {
        String expected = Differ.generate(
            "src/test/resources/fixtures/file1.yml",
            "src/test/resources/fixtures/file2.yml",
            "plain");

        String actual = Files.readString(Paths.get("src/test/resources/fixtures/result_plain.txt"), StandardCharsets.UTF_8);

        assertEquals(actual, expected);
    }
}
