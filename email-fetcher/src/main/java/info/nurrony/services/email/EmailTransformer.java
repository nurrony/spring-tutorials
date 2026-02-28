package info.nurrony.services.email;

import java.io.IOException;

import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.ContentType;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nur Rony
 */
@Slf4j
@Component
public class EmailTransformer extends AbstractMailMessageTransformer<Email> {

    // private boolean textIsHtml = false;

    @SuppressWarnings("null")
    @Override
    protected AbstractIntegrationMessageBuilder<Email> doTransform(Message mailMessage) {
        return MessageBuilder.withPayload(processPayload(mailMessage));
    }

    private Email processPayload(Message mailMessage) {
        try {
            String subject = mailMessage.getSubject();
            String email = ((InternetAddress) mailMessage.getFrom()[0]).getAddress();
            String content = getTextFromMessage(mailMessage);

            return parseEmail(email, subject, content);
        } catch (MessagingException e) {
            log.error("MessagingException: {}", e);
        } catch (Exception e) {
            log.error("IOException: {}", e);
        }

        return null;
    }

    private String getTextFromMessage(Message message) throws IOException, MessagingException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws IOException, MessagingException {
        int count = mimeMultipart.getCount();
        if (count == 0)
            throw new MessagingException("Multipart with no body parts not supported.");

        boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
        if (multipartAlt) {
            return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
        }

        String result = "";
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            result += getTextFromBodyPart(bodyPart);
        }
        return result;
    }

    private String getTextFromBodyPart(BodyPart bodyPart) throws IOException, MessagingException {
        String result = "";
        if (bodyPart.isMimeType("text/plain")) {
            result = (String) bodyPart.getContent();
        } else if (bodyPart.isMimeType("text/html")) {
            String html = (String) bodyPart.getContent();
            result = org.jsoup.Jsoup.parse(html).text();
        } else if (bodyPart.getContent() instanceof MimeMultipart) {
            result = getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
        }

        return result;
    }

    private Email parseEmail(String senderEmailAddress, String subject, String content) {
        return Email.builder().email(senderEmailAddress).subject(subject).content(content).build();
    }
}
