package dev.hireben.demo.crud_api.resource.dto;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

final class ResourceDTOTests {

  private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
  private static final ObjectMapper objectMapper = new ObjectMapper();

  // =============================================================================

  @Test
  void validDTO_shouldPassValidation() {
    ResourceDTO dto = new ResourceDTO(null, null, "Hello, world!", 888, true);
    Set<ConstraintViolation<ResourceDTO>> violations = validator.validate(dto);
    Assertions.assertThat(violations).isEmpty();
  }

  // -----------------------------------------------------------------------------

  @Test
  void blankTextField_shouldFailValidation() {
    ResourceDTO dto = new ResourceDTO(null, null, "", 888, true);
    Set<ConstraintViolation<ResourceDTO>> violations = validator.validate(dto);

    Assertions.assertThat(violations).hasSize(1);
    Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
        .containsExactly("textField cannot be blank");
  }

  // -----------------------------------------------------------------------------

  @Test
  void nullTextField_shouldFailValidation() {
    ResourceDTO dto = new ResourceDTO(null, null, null, 888, true);
    Set<ConstraintViolation<ResourceDTO>> violations = validator.validate(dto);

    Assertions.assertThat(violations).hasSize(1);
    Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
        .containsExactly("textField cannot be blank");
  }

  // -----------------------------------------------------------------------------

  @Test
  void nullNumberField_shouldFailValidation() {
    ResourceDTO dto = new ResourceDTO(null, null, "Hello, world!", null, true);
    Set<ConstraintViolation<ResourceDTO>> violations = validator.validate(dto);

    Assertions.assertThat(violations).hasSize(1);
    Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
        .containsExactly("numberField cannot be null");
  }

  // -----------------------------------------------------------------------------

  @Test
  void nullBooleanField_shouldFailValidation() {
    ResourceDTO dto = new ResourceDTO(null, null, "Hello, world!", 888, null);
    Set<ConstraintViolation<ResourceDTO>> violations = validator.validate(dto);

    Assertions.assertThat(violations).hasSize(1);
    Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
        .containsExactly("booleanField cannot be null");
  }

  // -----------------------------------------------------------------------------

  @Test
  void multipleInvalidFields_shouldFailValidationWithMultipleViolations() {
    ResourceDTO dto = new ResourceDTO(null, null, "", null, null);
    Set<ConstraintViolation<ResourceDTO>> violations = validator.validate(dto);

    Assertions.assertThat(violations).hasSize(3);
    Assertions.assertThat(violations).extracting(ConstraintViolation::getMessage)
        .containsExactlyInAnyOrder(
            "textField cannot be blank",
            "numberField cannot be null",
            "booleanField cannot be null");
  }

  // -----------------------------------------------------------------------------

  @Test
  void serializeDTO_shouldCreateCorrectJson() throws JsonProcessingException {
    ResourceDTO dto = new ResourceDTO(888L, 1L, "Hello, world!", 0, true);
    String json = objectMapper.writeValueAsString(dto);

    Assertions.assertThat(json)
        .contains("\"id\":888")
        .contains("\"version\":1")
        .contains("\"textField\":\"Hello, world!\"")
        .contains("\"numberField\":0")
        .contains("\"booleanField\":true");
  }

  // -----------------------------------------------------------------------------

  @Test
  void deserializeJson_shouldCreateCorrectObject() throws JsonProcessingException {
    String json = """
        {
          "id": 888,
          "version": 1,
          "textField": "Hello, world!",
          "numberField": 0,
          "booleanField": true
        }
        """;
    ResourceDTO object = objectMapper.readValue(json, ResourceDTO.class);

    assertAll("ResourceDTO deserialization",
        () -> assertNotNull(object),
        () -> assertEquals(888L, object.id()),
        () -> assertEquals(1L, object.version()),
        () -> assertEquals("Hello, world!", object.textField()),
        () -> assertEquals(0, object.numberField()),
        () -> assertTrue(object.booleanField()));
  }

  // -----------------------------------------------------------------------------

  @Test
  void deserializeJson_withMissingOptionalFields_shouldCreateObjectWithNulls() throws JsonProcessingException {
    String json = """
        {
          "textField": "Test",
          "numberField": 42,
          "booleanField": false
        }
        """;
    ResourceDTO object = objectMapper.readValue(json, ResourceDTO.class);

    assertAll("ResourceDTO with null optional fields",
        () -> assertNull(object.id()),
        () -> assertNull(object.version()),
        () -> assertEquals("Test", object.textField()),
        () -> assertEquals(42, object.numberField()),
        () -> assertFalse(object.booleanField()));
  }

}
