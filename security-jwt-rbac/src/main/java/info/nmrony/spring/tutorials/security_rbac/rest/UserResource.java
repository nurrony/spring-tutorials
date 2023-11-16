package info.nmrony.spring.tutorials.security_rbac.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.nmrony.spring.tutorials.security_rbac.domain.utils.ResponseUtils;
import info.nmrony.spring.tutorials.security_rbac.responses.ApiResponse;
import info.nmrony.spring.tutorials.security_rbac.rest.dtos.CreateUserRequest;
import info.nmrony.spring.tutorials.security_rbac.services.security.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid final CreateUserRequest request) throws Exception {
        return ResponseEntity.ok()
                .body(ResponseUtils.buildResourceResponse(userService.create(request), "Registration successful"));
    }

}
