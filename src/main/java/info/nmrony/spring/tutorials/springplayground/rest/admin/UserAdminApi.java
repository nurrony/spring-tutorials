package info.nmrony.spring.tutorials.springplayground.rest.admin;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.nmrony.spring.tutorials.springplayground.domain.constants.Roles;
import info.nmrony.spring.tutorials.springplayground.domain.responses.ApiResponse;
import info.nmrony.spring.tutorials.springplayground.rest.dtos.CreateUserRequest;
import info.nmrony.spring.tutorials.springplayground.rest.dtos.UpdateUserRequest;
import info.nmrony.spring.tutorials.springplayground.rest.dtos.UserView;
import info.nmrony.spring.tutorials.springplayground.services.UserService;
import info.nmrony.spring.tutorials.springplayground.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/admin/users")
@RolesAllowed(Roles.ADMIN)
@RequiredArgsConstructor
public class UserAdminApi {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid CreateUserRequest request) throws Exception {
        return ResponseEntity.ok().body(
                ResponseUtils.buildResourceResponse(userService.create(request), "User created successfully", 200));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok().body(
                ResponseUtils.buildResourceResponse(userService.update(id, request), "data updated successfully", 200));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(ResponseUtils.buildResourceResponse(userService.getUser(id), "data fetched successfully", 200));
    }


    @GetMapping
    public ResponseEntity<ApiResponse> getAll(Pageable pageable) {
        return ResponseEntity.ok().body(
                ResponseUtils.<UserView>buildPaginatedResponse(userService.list(pageable), "Users fetched successfully", 200));
    }
}
