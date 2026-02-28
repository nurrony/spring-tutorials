package info.nurrony.services.email;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Nur Rony
 */
public interface EmailRepository extends MongoRepository<Email, String> {
}
