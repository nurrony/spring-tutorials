package info.nmrony.tutorial.emailer.domain.services.emails;

import info.nmrony.tutorial.emailer.domain.entities.EmailAccount;

public interface EmailSender {
    void setup(EmailAccount account);

    boolean send();
}
