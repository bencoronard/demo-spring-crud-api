package dev.hireben.demo.crud_api.resource.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResourceDTO(
		Long id,
		Long version,
		@NotBlank String textField,
		@NotNull Integer numberField,
		@NotNull Boolean booleanField) {
}
