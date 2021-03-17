package info.nmrony.spring.tutorials.springplayground.rest;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.nmrony.spring.tutorials.springplayground.domain.constants.Roles;
import info.nmrony.spring.tutorials.springplayground.domain.responses.ApiResponse;
import info.nmrony.spring.tutorials.springplayground.utils.ResponseUtils;

@RestController
@RequestMapping("/api")
public class AccessTestController {
  @GetMapping("/all")
  public ResponseEntity<ApiResponse> allAccess() {
    return ResponseEntity.ok().body(ResponseUtils.buildResponse("Public Area", 200));
  }

  @GetMapping("/user")
  //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @RolesAllowed({Roles.ADMIN, Roles.MODERATOR, Roles.USER})
  public ResponseEntity<ApiResponse> userAccess() {
    return ResponseEntity.ok().body(ResponseUtils.buildResponse("Admin, User and Moderator can access it.", 200));
  }

  @GetMapping("/mod")
  @RolesAllowed(Roles.MODERATOR)
  public ResponseEntity<ApiResponse> moderatorAccess() {
    return ResponseEntity.ok().body(ResponseUtils.buildResponse("Only Moderator can access it.", 200));
  }

  @GetMapping("/admin")
  @RolesAllowed(Roles.ADMIN)
  public ResponseEntity<ApiResponse> adminAccess() {
    return ResponseEntity.ok().body(ResponseUtils.buildResponse("Only Admin can access it.", 200));
  }
}
