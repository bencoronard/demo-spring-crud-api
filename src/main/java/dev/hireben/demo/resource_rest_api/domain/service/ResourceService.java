package dev.hireben.demo.resource_rest_api.domain.service;

import java.util.Collection;

import dev.hireben.demo.resource_rest_api.domain.entity.Resource;

public interface ResourceService {

  Collection<Resource> listAllResources();

  Resource fetchResource();

  Long createResource();

  Long replaceResource();

  Long updateResource();

  Long deleteResource();

}
