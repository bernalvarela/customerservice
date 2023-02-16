package com.bernalvarela.customerservice.contract.controller;

import com.bernalvarela.customerservice.application.service.CustomerService;
import com.bernalvarela.customerservice.domain.exception.ElementNotFoundException;
import com.bernalvarela.customerservice.infrastructure.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.bernalvarela.customerservice.util.JsonUtil.asJsonString;
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

    private static final String IMAGE_NAME = "an_image";

    private static final Customer CUSTOMER = Customer.builder().name(NAME).surname(SURNAME).photo(PHOTO).build();

    private static final com.bernalvarela.customerservice.domain.model.Customer CUSTOMERMODEL =
            com.bernalvarela.customerservice.domain.model.Customer.builder()
                    .id(ID)
                    .name(NAME)
                    .surname(SURNAME)
                    .photo(PHOTO)
                    .build();

    private static final MockMultipartFile IMAGE = new MockMultipartFile(
            "file",
            "foo.jpg",
            "image/jpg", "foo".getBytes());

    @Test
    void shouldReturnCreatedCustomerWhenAddCustomer() throws Exception {
        when(service.createCustomer(any(), any())).thenReturn(CUSTOMERMODEL);

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/api/v1/customers")
                        .file(IMAGE)
                        .param("customer", asJsonString(CUSTOMER))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    void shouldReturnBadRequestWhenJsonNotValidAndAddCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/api/v1/customers")
                        .file(IMAGE)
                        .param("customer", "invalid json")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnCustomerWhenGetExistingCustomer() throws Exception {
        when(service.getCustomer(ID)).thenReturn(CUSTOMERMODEL);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/customers/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    void shouldReturnNotFoundWhenGetNonExistingCustomer() throws Exception {
        when(service.getCustomer(ID)).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/customers/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnCustomersListWhenGetCustomers() throws Exception {
        when(service.getCustomers()).thenReturn(List.of(CUSTOMERMODEL));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/customers")
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
                        .multipart(HttpMethod.PATCH, "/api/v1/customers/{id}", ID)
                        .file(IMAGE)
                        .param("customer", asJsonString(CUSTOMER))
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnOkWhenNullCustomerUpdateCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.PATCH, "/api/v1/customers/{id}", ID)
                        .file(IMAGE)
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnOkWhenNullImageUpdateCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.PATCH, "/api/v1/customers/{id}", ID)
                        .param("customer", asJsonString(CUSTOMER))
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenNotExistingUpdateCustomer() throws Exception {
        doThrow(new ElementNotFoundException()).when(service).updateCustomer(any(), any(), any());

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.PATCH, "/api/v1/customers/{id}", ID)
                        .file(IMAGE)
                        .param("customer", asJsonString(CUSTOMER))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenJsonNotValidAndUpdateCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.PATCH, "/api/v1/customers/{id}", ID)
                        .file(IMAGE)
                        .param("customer", "invalid json")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNoContentWhenDeleteCustomerImage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/customers/{id}/images", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenNoExistDeleteCustomerImage() throws Exception {
        doThrow(new ElementNotFoundException()).when(service).deleteCustomerImage(any());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/customers/{id}/images", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}