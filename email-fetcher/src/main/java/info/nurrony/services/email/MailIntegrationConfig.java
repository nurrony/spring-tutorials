package info.nurrony.services.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.dsl.Mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;

/**
 * @author Nur Rony
 */
@Configuration
public class MailIntegrationConfig {

    @SuppressWarnings("null")
    @Bean
    public IntegrationFlow mainIntegration(
            EmailProperties props,
            EmailTransformer emailTransformer,
            EmailRepository emailRepository) {
        return IntegrationFlow
                .from(
                        Mail.imapInboundAdapter(props.getImapUrl())
                                .shouldDeleteMessages(false)
                                .simpleContent(true)
                                .autoCloseFolder(false),
                        e -> e.poller(
                                Pollers.fixedDelay(props.getPollRate())))
                .<Message>filter((Message) -> {
                    boolean containsKeyword = false;
                    try {
                        containsKeyword = Message.getSubject().toUpperCase().contains(props.getSubjectFilterKeyword());
                    } catch (MessagingException e1) {
                        e1.printStackTrace();
                    }

                    return containsKeyword;
                })
                .transform(emailTransformer)
                .handle(message -> {
                    Email email = (Email) message.getPayload();
                    System.out.println("New Email received from: " + email.getEmail());
                    emailRepository.save(email);
                })
                .get();
    }
}
