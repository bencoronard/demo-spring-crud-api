package dev.hireben.demo.crud_api.resource.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import dev.hireben.demo.common_libs.exception.InsufficientPermissionException;
import dev.hireben.demo.crud_api.resource.entity.Resource;
import dev.hireben.demo.crud_api.resource.exception.ResourceNotFoundException;
import dev.hireben.demo.crud_api.resource.repository.ResourceRepository;
import dev.hireben.demo.crud_api.resource.utility.ResourcePermission;
import io.jsonwebtoken.Claims;

@ExtendWith(MockitoExtension.class)
final class ResourceServiceImplTests {

  @Mock
  private ResourceRepository resourceRepository;

  @InjectMocks
  private ResourceServiceImpl resourceService;

  // =============================================================================

  private static final String MOCK_USER_ID = "1";

  // =============================================================================

  @Test
  void listResources_withoutPermission_shouldThrowException() {
    Claims claims = mock(Claims.class);
    when(claims.get(ResourcePermission.LIST_RESOURCES, Integer.class)).thenReturn(null);

    assertThrows(InsufficientPermissionException.class,
        () -> resourceService.listResources(null, claims));
  }

  // -----------------------------------------------------------------------------

  @Test
  void listResources_shouldReturnResourceSlice() {
    Claims claims = mock(Claims.class);
    when(claims.get(ResourcePermission.LIST_RESOURCES, Integer.class)).thenReturn(1);
    when(claims.getSubject()).thenReturn(MOCK_USER_ID);

    List<Resource> expected = List.of(
        Resource.builder().build());
    Slice<Resource> mockSlice = new SliceImpl<>(expected);
    when(resourceRepository.findAllByCreatedBy(any(), any())).thenReturn(mockSlice);

    Slice<Resource> actual = resourceService.listResources(null, claims);

    assertEquals(expected, actual.getContent());
  }

  // -----------------------------------------------------------------------------

  @Test
  void retrieveResource_withoutPermission_shouldThrowException() {
    Claims claims = mock(Claims.class);
    when(claims.get(ResourcePermission.READ_RESOURCE, Integer.class)).thenReturn(null);

    assertThrows(InsufficientPermissionException.class,
        () -> resourceService.retrieveResource(null, claims));
  }

  // -----------------------------------------------------------------------------

  @Test
  void retrieveResource_whenResourceNotExist_shouldThrowException() {
    Claims claims = mock(Claims.class);
    when(claims.get(ResourcePermission.READ_RESOURCE, Integer.class)).thenReturn(1);
    when(claims.getSubject()).thenReturn(MOCK_USER_ID);
    when(resourceRepository.findByIdAndCreatedBy(any(), any())).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class,
        () -> resourceService.retrieveResource(null, claims));
  }

  // -----------------------------------------------------------------------------

  @Test
  void retrieveResource_shouldReturnResource() {
    Claims claims = mock(Claims.class);
    when(claims.get(ResourcePermission.READ_RESOURCE, Integer.class)).thenReturn(1);
    when(claims.getSubject()).thenReturn(MOCK_USER_ID);

    Resource expected = Resource.builder().build();
    when(resourceRepository.findByIdAndCreatedBy(any(), any())).thenReturn(Optional.of(expected));
    Resource actual = resourceService.retrieveResource(null, claims);
    assertEquals(expected, actual);
  }

  // -----------------------------------------------------------------------------

  @Test
  void createResource_withoutPermission_shouldThrowException() {
    Claims claims = mock(Claims.class);
    when(claims.get(ResourcePermission.CREATE_RESOURCE, Integer.class)).thenReturn(null);

    assertThrows(InsufficientPermissionException.class,
        () -> resourceService.createResource(null, claims));
  }

  // -----------------------------------------------------------------------------

  @Test
  void createResource_shouldReturnId() {
    Claims claims = mock(Claims.class);
    when(claims.get(ResourcePermission.CREATE_RESOURCE, Integer.class)).thenReturn(1);
    when(claims.getSubject()).thenReturn(MOCK_USER_ID);

    Long expected = 1L;
    Resource entity = Resource.builder().id(expected).build();
    when(resourceRepository.save(any())).thenReturn(entity);

    Long actual = resourceService.createResource(Resource.builder().build(), claims);

    assertEquals(expected, actual);
  }

  // -----------------------------------------------------------------------------

  @Test
  void updateResource_withoutPermission_shouldThrowException() {
    Claims claims = mock(Claims.class);
    when(claims.get(ResourcePermission.UPDATE_RESOURCE, Integer.class)).thenReturn(null);

    assertThrows(InsufficientPermissionException.class,
        () -> resourceService.updateResource(null, null, claims));
  }

  // -----------------------------------------------------------------------------

  @Test
  void updateResource_whenResourceNotExist_shouldThrowException() {
    Claims claims = mock(Claims.class);
    when(claims.get(ResourcePermission.UPDATE_RESOURCE, Integer.class)).thenReturn(1);
    when(claims.getSubject()).thenReturn(MOCK_USER_ID);

    when(resourceRepository.findByIdAndCreatedBy(any(), any())).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class,
        () -> resourceService.updateResource(null, null, claims));
  }

  // -----------------------------------------------------------------------------

  @Test
  void updateResource_shouldCallRepositoryOnce() {
    Claims claims = mock(Claims.class);
    when(claims.get(ResourcePermission.UPDATE_RESOURCE, Integer.class)).thenReturn(1);
    when(claims.getSubject()).thenReturn(MOCK_USER_ID);

    Resource entity = Resource.builder().build();
    when(resourceRepository.findByIdAndCreatedBy(any(), any())).thenReturn(Optional.of(entity));

    resourceService.updateResource(null, entity, claims);

    verify(resourceRepository).save(entity);
  }

  // -----------------------------------------------------------------------------

  @Test
  void deleteResource_withoutPermission_shouldThrowException() {
    Claims claims = mock(Claims.class);
    when(claims.get(ResourcePermission.DELETE_RESOURCE, Integer.class)).thenReturn(null);

    assertThrows(InsufficientPermissionException.class,
        () -> resourceService.deleteResource(null, claims));
  }

  // -----------------------------------------------------------------------------

  @Test
  void deleteResource_whenResourceNotExist_shouldThrowException() {
    Claims claims = mock(Claims.class);
    when(claims.get(ResourcePermission.DELETE_RESOURCE, Integer.class)).thenReturn(1);
    when(claims.getSubject()).thenReturn(MOCK_USER_ID);

    when(resourceRepository.findByIdAndCreatedBy(any(), any())).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class,
        () -> resourceService.deleteResource(null, claims));
  }

  // -----------------------------------------------------------------------------

  @Test
  void deleteResource_shouldCallRepositoryOnce() {
    Claims claims = mock(Claims.class);
    when(claims.get(ResourcePermission.DELETE_RESOURCE, Integer.class)).thenReturn(1);
    when(claims.getSubject()).thenReturn(MOCK_USER_ID);

    Resource entity = Resource.builder().build();
    when(resourceRepository.findByIdAndCreatedBy(any(), any())).thenReturn(Optional.of(entity));

    resourceService.deleteResource(null, claims);

    verify(resourceRepository).delete(entity);
  }

}
