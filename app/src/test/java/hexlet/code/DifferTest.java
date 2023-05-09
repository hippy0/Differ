package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
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
            Differ.generate("src/test/resources/fileDoesntExists.json",
                "src/test/resources/file2.json",
                "stylish"), "error");
    }

    @Test
    @DisplayName("Differ.generate() flat JSON file method test")
    void testDifferFlatJsonFile() throws IOException {
        String actual = Differ.generate(
            "src/test/resources/file1.json",
            "src/test/resources/file2.json",
            "json");

        String expected = """
            {
              - common: {
                    setting1: Value 1
                    setting2: 200
                    setting3: true
                    setting6: {
                        key: value
                        doge: {
                            wow: too much
                        }
                    }
                }
              + common: {
                    follow: false
                    setting1: Value 1
                    setting3: {
                        key: value
                    }
                    setting4: blah blah
                    setting5: {
                        key5: value5
                    }
                    setting6: {
                        key: value
                        ops: vops
                        doge: {
                            wow: so much
                        }
                    }
                }
              - group1: {
                    baz: bas
                    foo: bar
                    nest: {
                        key: value
                    }
                }
              + group1: {
                    foo: bar
                    baz: bars
                    nest: str
                }
              - group2: {
                    abc: 12345
                    deep: {
                        id: 45
                    }
                }
              + group3: {
                    fee: 100500
                    deep: {
                        id: {
                            number: 45
                        }
                    }
                }
                        
            }""";

        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Differ.generate() flat YAML file method test")
    void testDifferFlatYamlFile() throws IOException {
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

    @Test
    @DisplayName("Differ.generate nested YML file method test")
    void testDifferNestedYmlFile() throws IOException {
        String actual = Differ.generate(
            "src/test/resources/file1.yml",
            "src/test/resources/file2.yml",
            "plain");

        String expected = """
            Property 'chars2' was updated. From [complex value] to false
            Property 'checked' was updated. From false to true
            Property 'default' was updated. From null to [complex value]
            Property 'id' was updated. From 45 to null
            Property 'key1' was removed
            Property 'key2' was added with value: 'value2'
            Property 'numbers2' was updated. From [complex value] to [complex value]
            Property 'numbers3' was removed
            Property 'numbers4' was added with value: [complex value]
            Property 'obj1' was added with value: [complex value]
            Property 'setting1' was updated. From 'Some value' to 'Another value'
            Property 'setting2' was updated. From 200 to 300
            Property 'setting3' was updated. From true to 'none'""";

        assertEquals(actual, expected);
    }
}
