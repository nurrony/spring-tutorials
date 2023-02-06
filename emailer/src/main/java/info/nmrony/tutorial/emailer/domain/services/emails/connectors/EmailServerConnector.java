package info.nmrony.tutorial.emailer.domain.services.emails.connectors;

import info.nmrony.tutorial.emailer.domain.entities.vos.EmailServerConfiguration;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Store;

public interface EmailServerConnector {

    Store getStore();

    void connect() throws Exception;

    void configure() throws NoSuchProviderException;

    EmailServerConfiguration getReceiverConfiguration();

}
