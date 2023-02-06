package info.nmrony.tutorial.emailer.domain.entities.vos;

import info.nmrony.tutorial.emailer.domain.entities.enums.EmailFetchProtocolType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmailServerConfiguration {

    @NotBlank(message = "must not be empty")
    @NotNull(message = "must provide a value")
    private String host;

    @NotBlank(message = "must not be empty")
    @NotNull(message = "must provide a value")
    private String username;

    @NotBlank(message = "must not be empty")
    @NotNull(message = "must provide a value")
    private String password;

    @NotNull(message = "must provide a value")
    private Long port;

    @Enumerated(EnumType.STRING)
    private EmailFetchProtocolType storeProtocol = EmailFetchProtocolType.POP3;

    private String sslProtocol = "TLSv1.2";

    private boolean requireSsl = true;

    private boolean requireAuthentication = true;

    private boolean dfault = false;

}
