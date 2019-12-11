package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    @Test
    void customerToCustomerDTO() {
        Customer customer = new Customer("First", "Last");
        customer.setId(1L);
        String url = "/api/v1/customer/1";

        CustomerDTO dto = CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
        assertEquals(customer.getFirstName(), dto.getFirstName());
        assertEquals(customer.getLastName(), dto.getLastName());
        assertEquals(url, dto.getCustomerUrl());
    }
}