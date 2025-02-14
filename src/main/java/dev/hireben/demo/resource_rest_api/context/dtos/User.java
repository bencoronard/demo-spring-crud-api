package dev.hireben.demo.resource_rest_api.context.dtos;

import dev.hireben.demo.resource_rest_api.core.entities.Resource;

public record User(String id, Resource.Tenant tenant) {
}
