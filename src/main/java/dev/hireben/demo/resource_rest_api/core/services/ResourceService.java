package dev.hireben.demo.resource_rest_api.core.services;

import java.util.Collection;

import dev.hireben.demo.resource_rest_api.core.dtos.CreateResourceDTO;
import dev.hireben.demo.resource_rest_api.core.dtos.UpdateResourceDTO;
import dev.hireben.demo.resource_rest_api.core.entities.Resource;

public interface ResourceService {

  Collection<Resource> listAllResources();

  Collection<Resource> listAllResourcesByTenant(Resource.Tenant tenant);

  Resource fetchResource(Long id);

  Resource fetchResourceByTenant(Long id, Resource.Tenant tenant);

  Long createResource(CreateResourceDTO data, String userId, Resource.Tenant tenant);

  Long replaceResource(Long id, CreateResourceDTO data);

  Long updateResource(Long id, UpdateResourceDTO data);

  Long deleteResource(Long id);

}
