package dev.hireben.demo.crud_api.resource.utility;

import dev.hireben.demo.crud_api.resource.dto.ResourceDTO;
import dev.hireben.demo.crud_api.resource.entity.Resource;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceMapper {

  public ResourceDTO toDTO(Resource entity) {
    return new ResourceDTO(
        entity.getId(),
        entity.getVersion(),
        entity.getTextField(),
        entity.getNumberField(),
        entity.getBooleanField());
  }

  // -----------------------------------------------------------------------------

  public Resource toEntity(ResourceDTO dto) {
    return Resource.builder()
        .id(dto.id())
        .version(dto.version())
        .textField(dto.textField())
        .numberField(dto.numberField())
        .booleanField(dto.booleanField())
        .build();
  }

}
