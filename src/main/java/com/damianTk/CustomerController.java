package com.damianTk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("customers/")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("{customerId}")
    public Optional<Customer> getCustomer(@PathVariable("customerId") String customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);
        customerService.addCustomer(customer);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") String customerId, @RequestBody NewCustomerRequest request) {
        Customer customerToUpdate = new Customer();
        customerToUpdate.setName(request.name);
        customerToUpdate.setEmail(request.email);
        customerToUpdate.setAge(request.age);
        customerService.updateCustomer(customerToUpdate, customerId);
    }


    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") String customerId) {
        customerService.deleteCustomer(customerId);
    }

    record NewCustomerRequest(
            String name, String email, Integer age
    ) {
    }


}
