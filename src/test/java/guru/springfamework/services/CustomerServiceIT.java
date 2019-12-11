package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.controllers.v1.AbstractRestControllerTest;
import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class CustomerServiceIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @BeforeEach
    void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void patchCustomer() {
        String customerLastName = "Unmodified";
        Customer customer = new Customer("Bob", customerLastName);
        customerRepository.save(customer);

        CustomerDTO changes = new CustomerDTO();
        changes.setFirstName("Bob2");

        customerService.patchCustomer(customer.getId(), changes);

        customer = customerRepository.findById(customer.getId()).orElse(null);

        assertNotNull(customer);
        assertEquals(changes.getFirstName(), customer.getFirstName());
        assertEquals(customerLastName, customer.getLastName());
    }

    @Test
    void updateCustomerById() {

        Customer customer = new Customer("Bob", "Unmodified");
        customerRepository.save(customer);

        CustomerDTO changes = new CustomerDTO();
        changes.setFirstName("Bob2");
        changes.setLastName("Modified");

        customerService.updateCustomerById(customer.getId(), changes);

        customer = customerRepository.findById(customer.getId()).orElse(null);

        assertNotNull(customer);
        assertEquals(changes.getFirstName(), customer.getFirstName());
        assertEquals(changes.getLastName(), customer.getLastName());
    }

    @Test
    void deleteCustomer() {
        Customer customer = new Customer("Bob", "Unmodified");
        customerRepository.save(customer);

        customerService.deleteCustomer(customer.getId());

        customer = customerRepository.findById(customer.getId()).orElse(null);

        assertNull(customer);
    }
}