package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void getAllCustomers() {
        Customer customer = new Customer("Test", "Test");
        customer.setId(1L);
        Customer customer2 = new Customer("Test2", "Test2");
        customer2.setId(2L);
        List<Customer> data = Arrays.asList(customer, customer2);
        when(customerRepository.findAll()).thenReturn(data);

        List<CustomerDTO> result = customerService.getAllCustomers();

        assertEquals(data.size(), result.size());
    }

    @Test
    void getCustomerById() {
        Customer customer = new Customer("Test", "Test");
        customer.setId(1L);
        when(customerRepository.findById(eq(1L))).thenReturn(Optional.of(customer));

        CustomerDTO result = customerService.getCustomerById(1L);

        assertEquals(customer.getFirstName(), result.getFirstName());
        assertEquals(customer.getLastName(), result.getLastName());
        assertEquals("/api/v1/customer/1", result.getCustomerUrl());
    }
}