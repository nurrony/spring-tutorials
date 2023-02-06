package info.nmrony.tutorial.emailer.domain.services.emails;

import java.util.List;

import info.nmrony.tutorial.emailer.domain.entities.enums.EmailFolder;
import info.nmrony.tutorial.emailer.domain.entities.vos.Mail;
import info.nmrony.tutorial.emailer.domain.services.emails.connectors.EmailServerConnector;
import jakarta.mail.MessagingException;

public interface EmailReader {
    List<Mail> read(EmailServerConnector connector, EmailFolder folderName) throws MessagingException;
}
