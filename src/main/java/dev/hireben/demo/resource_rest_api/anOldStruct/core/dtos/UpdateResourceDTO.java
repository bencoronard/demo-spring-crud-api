package dev.hireben.demo.resource_rest_api.core.dtos;

import org.springframework.lang.Nullable;

public record UpdateResourceDTO(
    @Nullable String field1,
    @Nullable String field2,
    @Nullable String field3) {
}
