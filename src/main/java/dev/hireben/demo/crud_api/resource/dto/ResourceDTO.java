package dev.hireben.demo.crud_api.resource.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResourceDTO(
		Long id,
		Long version,
		@NotBlank(message = "textField cannot be blank") String textField,
		@NotNull(message = "numberField cannot be null") Integer numberField,
		@NotNull(message = "booleanField cannot be null") Boolean booleanField) {
}
