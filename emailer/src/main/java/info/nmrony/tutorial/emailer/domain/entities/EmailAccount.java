package info.nmrony.tutorial.emailer.domain.entities;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import info.nmrony.tutorial.emailer.domain.entities.enums.EmailFetchProtocolType;
import info.nmrony.tutorial.emailer.domain.entities.vos.EmailServerConfiguration;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email_accounts")
@ToString(exclude = { "senderServerConfig", "receiverServerConfig", "fetchProtocol" })
@EqualsAndHashCode(exclude = { "senderServerConfig", "receiverServerConfig", "fetchProtocol" })
public class EmailAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "must not be empty")
    @NotNull(message = "must provide a value")
    private String email;

    @Enumerated(EnumType.STRING)
    private EmailFetchProtocolType fetchProtocol = EmailFetchProtocolType.POP3;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private EmailServerConfiguration senderServerConfig;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private EmailServerConfiguration receiverServerConfig;

    private boolean active = false;
    private boolean dfault = false;
    private boolean verified = false;
    private boolean fetchProtocolEnabled = false;
    private boolean fetchProtocolConnected = false;

}
