package info.nmrony.spring.tutorials.springplayground.rest;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.nmrony.spring.tutorials.springplayground.domain.responses.ApiResponse;
import info.nmrony.spring.tutorials.springplayground.domain.utils.ResponseUtils;
import info.nmrony.spring.tutorials.springplayground.rest.dtos.CreateUserRequest;
import info.nmrony.spring.tutorials.springplayground.services.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid CreateUserRequest request) throws Exception {
        return ResponseEntity.ok().body(
                ResponseUtils.buildResourceResponse(userService.create(request), "Registration successful", 200));
    }

}
