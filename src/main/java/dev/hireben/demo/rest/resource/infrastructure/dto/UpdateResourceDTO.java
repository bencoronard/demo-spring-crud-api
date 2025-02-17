package dev.hireben.demo.rest.resource.infrastructure.dto;

import org.springframework.lang.Nullable;

public record UpdateResourceDTO(
    @Nullable String field1,
    @Nullable String field2,
    @Nullable String field3) {
}
