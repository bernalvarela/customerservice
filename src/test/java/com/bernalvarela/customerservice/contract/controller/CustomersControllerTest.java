package com.bernalvarela.customerservice.contract.controller;

import com.bernalvarela.customerservice.application.service.CustomerService;
import com.bernalvarela.customerservice.domain.exception.ElementNotFoundException;
import com.bernalvarela.customerservice.infrastructure.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    private static final String NAME = "name";

    private static final String SURNAME = "surname";

    private static final String PHOTO = "photo";

    private static final Long ID = 1L;

    private static final Customer CUSTOMER = Customer.builder().name(NAME).surname(SURNAME).photo(PHOTO).build();

    private static final com.bernalvarela.customerservice.domain.model.Customer CUSTOMERMODEL =
            com.bernalvarela.customerservice.domain.model.Customer.builder()
                    .id(ID)
                    .name(NAME)
                    .surname(SURNAME)
                    .photo(PHOTO)
                    .build();

    @Test
    void shouldReturnCreatedCustomerWhenAddCustomer() throws Exception {
        when(service.createCustomer(any())).thenReturn(CUSTOMERMODEL);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/customers")
                        .content(asJsonString(CUSTOMER))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    void shouldReturnCustomerWhenGetExistingCustomer() throws Exception {
        when(service.getCustomer(ID)).thenReturn(CUSTOMERMODEL);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/customers/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    void shouldReturnNotFoundWhenGetNonExistingCustomer() throws Exception {
        when(service.getCustomer(ID)).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/customers/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnCustomersListWhenGetCustomers() throws Exception {
        when(service.getCustomers()).thenReturn(List.of(CUSTOMERMODEL));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldReturnNoContentWhenDeleteCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/customers/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenNoExistDeleteCustomer() throws Exception {
        doThrow(new ElementNotFoundException()).when(service).deleteCustomer(any());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/customers/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnOkWhenUpdateCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/customers/{id}", ID)
                        .content(asJsonString(CUSTOMER))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenNotExistingUpdateCustomer() throws Exception {
        doThrow(new ElementNotFoundException()).when(service).updateCustomer(any(), any());
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/customers/{id}", ID)
                        .content(asJsonString(CUSTOMER))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}