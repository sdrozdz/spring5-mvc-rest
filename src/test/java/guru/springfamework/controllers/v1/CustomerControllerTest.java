package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends AbstractRestControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getCustomerList() throws Exception {
        List<CustomerDTO> data = Arrays.asList(new CustomerDTO(), new CustomerDTO());

        when(customerService.getAllCustomers()).thenReturn(data);

        mockMvc.perform(get("/api/v1/customer").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception {

        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName("Test");
        dto.setLastName("Last");
        dto.setCustomerUrl("/api/v1/customer/1");

        when(customerService.getCustomerById(eq(1L))).thenReturn(dto);

        mockMvc.perform(get("/api/v1/customer/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(dto.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(dto.getLastName())))
                .andExpect(jsonPath("$.customer_url", equalTo(dto.getCustomerUrl())));
    }

    @Test
    void createCustomer() throws Exception {
        CustomerDTO createDTO = new CustomerDTO();
        createDTO.setFirstName("First");
        createDTO.setLastName("Last");

        CustomerDTO result = new CustomerDTO();
        result.setFirstName(createDTO.getFirstName());
        result.setLastName(createDTO.getLastName());
        result.setCustomerUrl("/api/v1/customer/1");

        when(customerService.createCustomer(createDTO)).thenReturn(result);

        mockMvc.perform(post("/api/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(createDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(createDTO.getLastName())))
                .andExpect(jsonPath("$.customer_url", equalTo(result.getCustomerUrl())));
    }

    @Test
    void updateCustomer() throws Exception {
        CustomerDTO createDTO = new CustomerDTO();
        createDTO.setFirstName("First");
        createDTO.setLastName("Last");

        CustomerDTO result = new CustomerDTO();
        result.setFirstName(createDTO.getFirstName());
        result.setLastName(createDTO.getLastName());
        result.setCustomerUrl("/api/v1/customer/1");

        when(customerService.updateCustomerById(1L, createDTO)).thenReturn(result);

        mockMvc.perform(put("/api/v1/customer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(createDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(createDTO.getLastName())))
                .andExpect(jsonPath("$.customer_url", equalTo(result.getCustomerUrl())));
    }

}