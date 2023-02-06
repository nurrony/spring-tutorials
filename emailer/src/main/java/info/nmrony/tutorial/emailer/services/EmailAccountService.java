package info.nmrony.tutorial.emailer.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.nmrony.tutorial.emailer.domain.entities.EmailAccount;
import info.nmrony.tutorial.emailer.domain.exceptions.ResourceNotFoundException;
import info.nmrony.tutorial.emailer.repositories.EmailAccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailAccountService {
    final private EmailAccountRepository emailAccountRepository;

    public List<EmailAccount> list() {
        return emailAccountRepository.findAll();
    }

    public EmailAccount getById(Long id) {
        return emailAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EmailAccount.class, id));
    }

    public void delete(Long id) {
        EmailAccount emailAccount = getById(id);
        emailAccountRepository.delete(emailAccount);
    }

    public EmailAccount create(EmailAccount payload) {
        return emailAccountRepository.save(payload);
    }
}
