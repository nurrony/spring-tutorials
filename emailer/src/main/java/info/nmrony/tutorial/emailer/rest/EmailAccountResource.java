package info.nmrony.tutorial.emailer.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.nmrony.tutorial.emailer.domain.entities.EmailAccount;
import info.nmrony.tutorial.emailer.services.EmailAccountService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email-accounts")
public class EmailAccountResource {

    final private EmailAccountService emailAccountService;

    @GetMapping
    public ResponseEntity<List<EmailAccount>> list() {
        return ResponseEntity.ok(emailAccountService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<EmailAccount> get(@PathVariable Long id) {
        return ResponseEntity.ok(emailAccountService.getById(id));
    }

    @PostMapping
    public ResponseEntity<EmailAccount> create(@RequestBody EmailAccount payload) {
        return ResponseEntity.ok(emailAccountService.create(payload));
    }
}
