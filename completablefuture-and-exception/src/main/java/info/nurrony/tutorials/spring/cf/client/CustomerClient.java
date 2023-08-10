package info.nurrony.tutorials.spring.cf.client;

import org.springframework.stereotype.Service;

import info.nurrony.tutorials.spring.cf.configuration.DataLoader;
import info.nurrony.tutorials.spring.cf.dto.Customer;
import info.nurrony.tutorials.spring.cf.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerClient {

    private final DataLoader dataLoader;

    public Customer getCustomerById(Integer customerId) {
        log.info("Getting customer by id {}", customerId);
        Utils.loadingSimulator(2);
        return dataLoader.getCustomers().get(customerId);
    }
}
