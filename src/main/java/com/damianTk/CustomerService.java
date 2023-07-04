package com.damianTk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerService {
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomer(String customerId) {

        if (customerId == null) {
            logger.info("CustomerId is null");
            return Optional.empty();
        }
        Optional<UUID> customerUUID = getUUIDFromString(customerId);

        if (customerUUID.isEmpty()) {
            logger.info("Invalid CustomerId");
            return Optional.empty();
        }

        Optional<Customer> currentCustomer = customerRepository.findById(customerUUID.get());

        if (currentCustomer.isEmpty()) {
            logger.info("Customer cannot be found: {}", customerId);
            return Optional.empty();
        }
        return currentCustomer;

    }

    private Optional<UUID> getUUIDFromString(String uuidString) {
        try {
            return Optional.of(UUID.fromString(uuidString));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public void addCustomer(Customer customer) {
        customer.setId(UUID.randomUUID());
        customerRepository.save(customer);
    }

    public void deleteCustomer(String customerId) {
        Optional<UUID> customerUUID = getUUIDFromString(customerId);

        if (customerUUID.isEmpty()) {
            logger.info("Invalid CustomerId");
        } else {
            customerRepository.deleteById(customerUUID.get());

        }
    }
}
