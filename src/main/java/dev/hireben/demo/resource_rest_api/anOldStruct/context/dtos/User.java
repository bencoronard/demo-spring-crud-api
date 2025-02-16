package dev.hireben.demo.resource_rest_api.anOldStruct.context.dtos;

import dev.hireben.demo.resource_rest_api.domain.entity.Resource;

public record User(String id, Resource.Tenant tenant) {
}
