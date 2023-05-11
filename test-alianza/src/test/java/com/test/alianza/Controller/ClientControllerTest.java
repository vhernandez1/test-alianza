package com.test.alianza.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.alianza.model.Client;
import com.test.alianza.services.ClientCrudServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientCrudServices mockClientCrudServices;

    @Test
    void testPostClient() throws Exception {
        // Setup
        // Configure ClientCrudServices.Create(...).
        final Client client = new Client();
        client.setSharedKey("sharedKey");
        client.setBusinessId("businessId");
        client.setEmail("email");
        client.setPhone("phone");
        client.setDateAdded("dateAdded");
        final Client client1 = new Client();
        client1.setSharedKey("sharedKey");
        client1.setBusinessId("businessId");
        client1.setEmail("email");
        client1.setPhone("phone");
        client1.setDateAdded("dateAdded");
        when(mockClientCrudServices.Create(client1)).thenReturn(client);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/clients")
                        .content(asJsonString(client1)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"sharedKey\":\"sharedKey\",\"businessId\":\"businessId\",\"email\":\"email\",\"phone\":\"phone\",\"dateAdded\":\"dateAdded\"}");
    }

    @Test
    void testGetClients1() throws Exception {
        // Setup
        // Configure ClientCrudServices.getAllClients(...).
        final Client client = new Client();
        client.setSharedKey("sharedKey");
        client.setBusinessId("businessId");
        client.setEmail("email");
        client.setPhone("phone");
        client.setDateAdded("dateAdded");
        final List<Client> clients = List.of(client);
        when(mockClientCrudServices.getAllClients()).thenReturn(clients);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/clients")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[{\"sharedKey\":\"sharedKey\",\"businessId\":\"businessId\",\"email\":\"email\",\"phone\":\"phone\",\"dateAdded\":\"dateAdded\"}]");
    }

    @Test
    void testGetClients1_ClientCrudServicesReturnsNoItems() throws Exception {
        // Setup
        when(mockClientCrudServices.getAllClients()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/clients")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetClients2() throws Exception {
        // Setup
        // Configure ClientCrudServices.getByShareKey(...).
        final Client client = new Client();
        client.setSharedKey("sharedKey");
        client.setBusinessId("businessId");
        client.setEmail("email");
        client.setPhone("phone");
        client.setDateAdded("dateAdded");
        final List<Client> clients = List.of(client);
        when(mockClientCrudServices.getByShareKey("sharedKey")).thenReturn(clients);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/clients/{sharedKey}", "sharedKey")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[{\"sharedKey\":\"sharedKey\",\"businessId\":\"businessId\",\"email\":\"email\",\"phone\":\"phone\",\"dateAdded\":\"dateAdded\"}]");
    }

    @Test
    void testGetClients2_ClientCrudServicesReturnsNoItems() throws Exception {
        // Setup
        when(mockClientCrudServices.getByShareKey("sharedKey")).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/clients/{sharedKey}", "sharedKey")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
