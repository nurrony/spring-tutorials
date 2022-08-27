package info.nmrony.spring.tutorials.security_rbac.rest;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.nmrony.spring.tutorials.security_rbac.domain.entities.User;
import info.nmrony.spring.tutorials.security_rbac.domain.utils.ResponseUtils;
import info.nmrony.spring.tutorials.security_rbac.responses.ApiResponse;
import info.nmrony.spring.tutorials.security_rbac.rest.dtos.AuthRequest;
import info.nmrony.spring.tutorials.security_rbac.rest.mappers.AuthMapper;
import info.nmrony.spring.tutorials.security_rbac.services.security.UserService;
import info.nmrony.spring.tutorials.security_rbac.utils.AppUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "Authentication")
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthResource {
    private final UserService userService;
    private final AuthMapper authMapper;

    @PostMapping("authenticate")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid final AuthRequest request) throws Exception {
        try {
            final User user = userService.findWithRolesByUserNameAndPassword(request.getUsername(),
                    request.getPassword());
            if (!AppUtils.isNotNull(user)) {
                throw new BadCredentialsException("Bad credential");
            }

            return ResponseEntity.ok().body(ResponseUtils.buildResourceResponse(authMapper.toAuthResponse(request),
                    "Authentication is successfull", 200));
        } catch (BadCredentialsException | DisabledException exception) {
            throw exception;
        }
    }

}
