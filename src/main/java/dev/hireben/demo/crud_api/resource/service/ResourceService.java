package dev.hireben.demo.crud_api.resource.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import dev.hireben.demo.crud_api.resource.entity.Resource;
import io.jsonwebtoken.Claims;

public interface ResourceService {

  Slice<Resource> listResources(Pageable pageable, Claims authClaims);

  Resource retrieveResource(Long id, Claims authClaims);

  Long createResource(Resource dto, Claims authClaims);

  void updateResource(Long id, Resource dto, Claims authClaims);

  void deleteResource(Long id, Claims authClaims);

}
