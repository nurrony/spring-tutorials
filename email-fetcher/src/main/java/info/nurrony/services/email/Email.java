package info.nurrony.services.email;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Nur Rony
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "emails")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String email;
    private String subject;
    private String content;

}
