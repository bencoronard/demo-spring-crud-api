package dev.hireben.demo.resource_rest_api.anOldStruct.core.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateResourceDTO(
    @NotBlank String field1,
    @NotBlank String field2,
    @NotBlank String field3) {
}
