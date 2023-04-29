/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.example.record;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.skyscreamer.jsonassert.JSONCompareMode.NON_EXTENSIBLE;

/**
 * @see org.example.record.RecordExample
 */
class RecordExampleTest {
    @Test
    void recordConstructorTest() {
        final UUID id = UUID.randomUUID();
        final String name = "TestName1";

        final RecordExample record = new RecordExample(id, name, null);

        assertRecordExample(id, name, null, record);
    }

    @Test
    void recordBuilderTest() {
        final UUID id = UUID.randomUUID();
        final String name = "TestName2";

        final RecordExample record = RecordExample.builder().id(id).name(name).build();

        assertRecordExample(id, name, null, record);
    }

    @ParameterizedTest
    @CsvSource(value = {"TestDescription", "null"}, nullValues = "null")
    void recordJsonSerializationDeserializationTest(String descriptionText) throws Exception {
        final UUID id = UUID.randomUUID();
        final String name = "TestName3";
        final Optional<String> description = Optional.ofNullable(descriptionText);
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module()); // for serializing and deserializing Optional and other java 8 and higher features

        final String recordJson = objectMapper.writeValueAsString(new RecordExample(id, name, description));

        assertRecordExampleJson(id, name, description.orElse(null), recordJson);

        final RecordExample recordObject = objectMapper.readValue(recordJson, RecordExample.class);

        assertRecordExample(id, name, description.orElse(null), recordObject);
    }

    @Test
    void recordCreationValidationTest() {
        assertThrows(IllegalArgumentException.class, () -> new RecordExample(null, "", empty()));
        assertThrows(IllegalArgumentException.class, () -> RecordExample.builder().name("").description(empty()).build());
    }

    private static void assertRecordExampleJson(UUID expectedId,
                                                String expectedName,
                                                String expectedDescription,
                                                String recordJson) throws JSONException {
        final String expectedJson = format("""
                        {
                            "id": %s,
                            "name": %s,
                            "description": %s
                        }
                        """,
                wrapWithQuotes(expectedId),
                wrapWithQuotes(expectedName),
                wrapWithQuotes(expectedDescription));
        JSONAssert.assertEquals(expectedJson, recordJson, NON_EXTENSIBLE);
    }

    private static String wrapWithQuotes(Object str) {
        return str != null ? "\"" + str + "\"" : null;
    }

    private static void assertRecordExample(UUID expectedId,
                                            String expectedName,
                                            String expectedDescription,
                                            RecordExample actual) {
        assertEquals(expectedId, actual.id());
        assertEquals(expectedName, actual.name());
        assertEquals(expectedDescription, actual.description().orElse(null));
        assertEquals(format("RecordExample[id=%s]", expectedId), actual.toString());
        assertEquals(expectedId.hashCode(), actual.hashCode());
        assertEquals(new RecordExample(expectedId, null, null), actual);
    }
}
