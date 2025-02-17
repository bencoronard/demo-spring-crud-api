package dev.hireben.demo.rest.resource.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateResourceDTO(
    @NotBlank String field1,
    @NotBlank String field2,
    @NotBlank String field3) {
}
