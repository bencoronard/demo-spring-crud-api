package dev.hireben.demo.rest.resource.presentation.request;

import org.springframework.lang.Nullable;

public record UpdateResourceRequest(
    @Nullable String field1,
    @Nullable String field2,
    @Nullable String field3) {
}
