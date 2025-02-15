package dev.hireben.demo.resource_rest_api.core.controllers;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dev.hireben.demo.resource_rest_api.anOldStruct.common.dtos.GlobalResponseBody;
import dev.hireben.demo.resource_rest_api.context.dtos.User;
import dev.hireben.demo.resource_rest_api.core.dtos.CreateResourceDTO;
import dev.hireben.demo.resource_rest_api.core.dtos.UpdateResourceDTO;
import dev.hireben.demo.resource_rest_api.domain.entity.Resource;
import jakarta.validation.Valid;

public interface ResourceController {

  @GetMapping("/resources")
  ResponseEntity<GlobalResponseBody<Collection<Resource>>> listAllResources(User user);

  @GetMapping("/resources/{id}")
  ResponseEntity<GlobalResponseBody<Resource>> fetchResource(@PathVariable Long id, User user);

  @PostMapping("/resources")
  ResponseEntity<GlobalResponseBody<Long>> createResource(@RequestBody @Valid CreateResourceDTO data, User user);

  @PutMapping("/resources/{id}")
  ResponseEntity<GlobalResponseBody<Long>> replaceResource(@PathVariable Long id,
      @RequestBody @Valid CreateResourceDTO data, User user);

  @PatchMapping("/resources/{id}")
  ResponseEntity<GlobalResponseBody<Long>> updateResource(@PathVariable Long id, @RequestBody UpdateResourceDTO data,
      User user);

  @DeleteMapping("/resources/{id}")
  ResponseEntity<GlobalResponseBody<Long>> deleteResource(@PathVariable Long id, User user);

}
