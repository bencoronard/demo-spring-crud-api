package dev.hireben.demo.rest.resource.infrastructure.web;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hireben.demo.rest.resource.application.dto.ResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UserDTO;
import dev.hireben.demo.rest.resource.application.usecase.CreateResourceUseCase;
import dev.hireben.demo.rest.resource.application.usecase.DeleteResourceUseCase;
import dev.hireben.demo.rest.resource.application.usecase.RetrieveResourceUseCase;
import dev.hireben.demo.rest.resource.application.usecase.UpdateResourceUseCase;
import dev.hireben.demo.rest.resource.domain.dto.Paginable;
import dev.hireben.demo.rest.resource.domain.dto.Paginated;
import dev.hireben.demo.rest.resource.infrastructure.annotation.UserInfo;
import dev.hireben.demo.rest.resource.infrastructure.constant.DefaultValue;
import dev.hireben.demo.rest.resource.infrastructure.dto.GlobalResponseBody;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
public class ResourceControllerV1 {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final CreateResourceUseCase createResourceUseCase;
  private final RetrieveResourceUseCase readResourceUseCase;
  private final UpdateResourceUseCase updateResourceUseCase;
  private final DeleteResourceUseCase deleteResourceUseCase;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @GetMapping
  public ResponseEntity<GlobalResponseBody<Paginated<ResourceDTO>>> fetchAllResources(
      @RequestParam(name = "page", defaultValue = "0") int pageNumber,
      @RequestParam(name = "size", defaultValue = "10") int pageSize,
      @RequestParam(name = "sort", defaultValue = "id:asc") Collection<String> sortParams,
      @UserInfo UserDTO user) {

    Map<String, Boolean> sortFields = sortParams.stream()
        .map(param -> param.split(":"))
        .filter(parts -> parts.length == 2)
        .collect(Collectors.toMap(
            parts -> parts[0],
            parts -> "desc".equalsIgnoreCase(parts[1])));

    Paginable paginable = Paginable.builder()
        .pageNumber(pageNumber)
        .pageSize(pageSize)
        .sortFieldsDesc(sortFields)
        .build();

    Paginated<ResourceDTO> payload = readResourceUseCase.findAllResources(paginable, user);

    GlobalResponseBody<Paginated<ResourceDTO>> body = GlobalResponseBody.<Paginated<ResourceDTO>>builder()
        .code(DefaultValue.RESP_CODE_SUCCESS)
        .message("resources")
        .payload(payload)
        .build();

    return ResponseEntity.ok(body);
  }

  // ---------------------------------------------------------------------------//

  @GetMapping("/{id}")
  public ResponseEntity<GlobalResponseBody<ResourceDTO>> fetchResource(
      @PathVariable Long id,
      @UserInfo UserDTO user) {

    ResourceDTO payload = readResourceUseCase.findResource(id, user);

    GlobalResponseBody<ResourceDTO> body = GlobalResponseBody.<ResourceDTO>builder()
        .code(DefaultValue.RESP_CODE_SUCCESS)
        .message("resource " + id)
        .payload(payload)
        .build();

    return ResponseEntity.ok(body);
  }

}
