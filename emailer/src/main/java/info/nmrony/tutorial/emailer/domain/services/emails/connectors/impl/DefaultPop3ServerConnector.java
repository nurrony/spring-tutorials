package info.nmrony.tutorial.emailer.domain.services.emails.connectors.impl;

import java.util.Properties;

import info.nmrony.tutorial.emailer.domain.entities.EmailAccount;
import info.nmrony.tutorial.emailer.domain.entities.vos.EmailServerConfiguration;
import info.nmrony.tutorial.emailer.domain.services.emails.connectors.EmailServerConnector;
import jakarta.mail.MessagingException;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultPop3ServerConnector implements EmailServerConnector {

    final private EmailAccount account;

    private Store store;

    private Properties properties;

    public DefaultPop3ServerConnector(EmailAccount account) throws NoSuchProviderException {
        this.account = account;
        configure();
    }

    public EmailServerConfiguration getReceiverConfiguration() {
        return account.getReceiverServerConfig();
    }

    public Store getStore() {
        return store;
    }

    @Override
    public void configure() throws NoSuchProviderException {
        _defaultPop3Configuration();
    }

    public void _defaultPop3Configuration() throws NoSuchProviderException {
        EmailServerConfiguration configuration = getReceiverConfiguration();

        if (configuration.isRequireSsl()) {
            properties.put("mail.pop3.starttls.enable", "true");
            properties.put("mail.pop3.ssl.protocols", configuration.getSslProtocol());
        }

        properties.put("mail.pop3.host", configuration.getHost());
        properties.put("mail.pop3.port", configuration.getPort());

        Session emailSession = Session.getDefaultInstance(properties);
        store = emailSession.getStore(configuration.getStoreProtocol().getLabel());

    }

    @Override
    public void connect() throws MessagingException {
        var configuration = getReceiverConfiguration();
        store.connect(configuration.getHost(), configuration.getUsername(), configuration.getPassword());
    }

}
