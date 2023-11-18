package info.nurrony.tutorials.spring.eventrestapi.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.nurrony.tutorials.spring.eventrestapi.rest.dtos.AccountDto;
import info.nurrony.tutorials.spring.eventrestapi.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthResource {

    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto request)
            throws Exception {
        log.info("registering user {}", request.getNickname());
        AccountDto registerAccount = accountService.registerAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerAccount);
    }
}
