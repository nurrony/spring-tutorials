package info.nmrony.tutorial.emailer.domain.services.emails.impl;

import java.util.ArrayList;
import java.util.List;

import info.nmrony.tutorial.emailer.domain.entities.enums.EmailFolder;
import info.nmrony.tutorial.emailer.domain.entities.vos.Mail;
import info.nmrony.tutorial.emailer.domain.services.emails.EmailMapper;
import info.nmrony.tutorial.emailer.domain.services.emails.EmailReader;
import info.nmrony.tutorial.emailer.domain.services.emails.connectors.EmailServerConnector;
import jakarta.mail.Flags;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Store;

public class DefaultPop3EmailReader implements EmailReader {

    @Override
    public List<Mail> read(EmailServerConnector connector, EmailFolder folder) throws MessagingException {
        Store store = connector.getStore();
        Folder emailFolder = null;
        try {

            emailFolder = store.getFolder(folder.getLabel().toUpperCase());
            emailFolder.open(Folder.READ_WRITE);

            return _getNewMails(emailFolder, connector.getReceiverConfiguration().getUsername());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (emailFolder != null && emailFolder.isOpen()) {
                    emailFolder.close(false);
                }

                if (store != null && store.isConnected()) {
                    store.close();
                }
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<Mail> _getNewMails(Folder emailFolder, String username) throws MessagingException {
        List<Mail> mails = new ArrayList<>();
        for (Message message : emailFolder.getMessages()) {
            if (!message.getFlags().contains(Flags.Flag.SEEN)) {
                message.setFlags(new Flags(Flags.Flag.SEEN), true);
                Mail mail = EmailMapper.map(message, username);
                mails.add(mail);
            }
        }
        return mails;
    }

}
