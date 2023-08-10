package info.nurrony.tutorials.spring.cf.client;

import java.util.Set;

import org.springframework.stereotype.Service;

import info.nurrony.tutorials.spring.cf.configuration.DataLoader;
import info.nurrony.tutorials.spring.cf.dto.PurchaseTransaction;
import info.nurrony.tutorials.spring.cf.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseTransactionClient {

    private final DataLoader dataLoader;

    public Set<PurchaseTransaction> getPurchaseTransactionsByCustomerId(Integer customerId, boolean isException) {
        log.info("Getting purchase transactions by customerId {}", customerId);
        Utils.loadingSimulator(4);
        if (isException) {
            log.error("The error occurred while trying to retrieve purchase transactions!");
            throw new RuntimeException();
        }
        return dataLoader.getPurchaseTransactionResponses().get(customerId);
    }
}
