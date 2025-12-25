package dev.hireben.demo.crud_api.resource.controller;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import dev.hireben.demo.common_libs.http.annotation.HttpAuthorizationHeader;
import dev.hireben.demo.crud_api.resource.dto.ResourceDTO;
import dev.hireben.demo.crud_api.resource.entity.Resource;
import dev.hireben.demo.crud_api.resource.service.ResourceService;
import dev.hireben.demo.crud_api.resource.utility.ResourceMapper;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
final class ResourceController {

  private final ResourceService resourceService;

  // =============================================================================

  @GetMapping
  ResponseEntity<Slice<ResourceDTO>> listResources(
      @HttpAuthorizationHeader Claims authClaims,
      @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

    Slice<Resource> resources = resourceService.listResources(pageable, authClaims);

    return ResponseEntity.ok(resources.map(ResourceMapper::toDTO));
  }

  // -----------------------------------------------------------------------------

  @GetMapping("/{id}")
  ResponseEntity<ResourceDTO> retrieveResource(
      @HttpAuthorizationHeader Claims authClaims,
      @PathVariable Long id) {

    Resource resource = resourceService.retrieveResource(id, authClaims);

    return ResponseEntity.ok(ResourceMapper.toDTO(resource));
  }

  // -----------------------------------------------------------------------------

  @PostMapping
  ResponseEntity<Void> createResource(
      @HttpAuthorizationHeader Claims authClaims,
      @Valid @RequestBody ResourceDTO body) {

    Long id = resourceService.createResource(ResourceMapper.toEntity(body), authClaims);

    URI location = URI.create(String.format("/api/resources/%d", id));

    return ResponseEntity.created(location).build();
  }

  // -----------------------------------------------------------------------------

  @PutMapping("/{id}")
  ResponseEntity<Void> updateResource(
      @HttpAuthorizationHeader Claims authClaims,
      @PathVariable Long id,
      @Valid @RequestBody ResourceDTO body) {

    resourceService.updateResource(id, ResourceMapper.toEntity(body), authClaims);

    return ResponseEntity.noContent().build();
  }

  // -----------------------------------------------------------------------------

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteResource(
      @HttpAuthorizationHeader Claims authClaims,
      @PathVariable Long id) {

    resourceService.deleteResource(id, authClaims);

    return ResponseEntity.noContent().build();
  }

}
