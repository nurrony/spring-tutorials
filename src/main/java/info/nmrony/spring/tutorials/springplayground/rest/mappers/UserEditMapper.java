package info.nmrony.spring.tutorials.springplayground.rest.mappers;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import info.nmrony.spring.tutorials.springplayground.domain.entities.Role;
import info.nmrony.spring.tutorials.springplayground.domain.entities.User;
import info.nmrony.spring.tutorials.springplayground.domain.exceptions.ResourceNotFoundException;
import info.nmrony.spring.tutorials.springplayground.repositories.RoleRepository;
import info.nmrony.spring.tutorials.springplayground.rest.dtos.CreateUserRequest;
import info.nmrony.spring.tutorials.springplayground.rest.dtos.UpdateUserRequest;

@Mapper(componentModel = "spring")
public abstract class UserEditMapper {
  private RoleRepository roleRepository;

  public void setRoleRepository(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Mapping(target = "roles", ignore = true)
  public abstract User create(CreateUserRequest request);

  @Mapping(target = "roles", ignore = true)
  @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
  public abstract void update(UpdateUserRequest request, @MappingTarget User user);

  @AfterMapping
  protected void afterCreate(CreateUserRequest request, @MappingTarget User user) {
    if (request.getRoles() != null) {
      user.setRoles(request.getRoles().stream()
          .map(role -> roleRepository.findById(role.getName())
              .orElseThrow(() -> new ResourceNotFoundException(Role.class, role.getName())))
          .collect(Collectors.toSet()));
    }
  }

  @AfterMapping
  protected void afterUpdate(UpdateUserRequest request, @MappingTarget User user) {
    if (request.getRoles() != null) {
      user.setRoles(request.getRoles().stream()
          .map(role -> roleRepository.findById(role.getName())
              .orElseThrow(() -> new ResourceNotFoundException(Role.class, role.getName())))
          .collect(Collectors.toSet()));
    }

  }
}
