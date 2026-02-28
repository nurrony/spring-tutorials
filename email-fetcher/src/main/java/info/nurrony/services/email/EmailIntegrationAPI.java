package info.nurrony.services.email;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author Nur Rony
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/emails", produces = "application/json")
public class EmailIntegrationAPI {

    final private EmailRepository emailRepository;

    @GetMapping
    public List<Email> getPaymenets() {
        return emailRepository.findAll();
    }
}
