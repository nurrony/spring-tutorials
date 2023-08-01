package info.nmrony.tutorials.spring.batch.processors;

import info.nmrony.tutorials.spring.batch.entities.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer customer) throws Exception {
        return null;
    }
}
