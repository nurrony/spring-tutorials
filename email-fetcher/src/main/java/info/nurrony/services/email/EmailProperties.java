package info.nurrony.services.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author Nur Rony
 */

@Data
@Component
@ConfigurationProperties(prefix = "itc.services.email")
public class EmailProperties {

    private String username;
    private String password;
    private String host;
    private String port;
    private String mailbox;
    private String subjectFilterKeyword;
    private long pollRate = 30000;

    public String getImapUrl() {
        return String.format("imaps://%s:%s@%s:%s/%s", this.username, this.password, this.host, this.port,
                this.mailbox);
    }

}
