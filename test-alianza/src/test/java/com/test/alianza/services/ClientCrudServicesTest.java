package com.test.alianza.services;

import com.test.alianza.ClientRepo;
import com.test.alianza.exceptions.ClientException;
import com.test.alianza.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientCrudServicesTest {

    @Mock
    private ClientRepo mockClientRepo;

    private ClientCrudServices clientCrudServicesUnderTest;

    @BeforeEach
    void setUp() {
        clientCrudServicesUnderTest = new ClientCrudServices(mockClientRepo);
    }

    @Test
    void testCreate() {
        // Setup
        final Client client = new Client();
        client.setSharedKey("sharedKey");
        client.setBusinessId("businessId");
        client.setEmail("email");
        client.setPhone("phone");
        client.setDateAdded("dateAdded");

        // Run the test
        clientCrudServicesUnderTest.Create(client);

        // Verify the results
        // Confirm ClientRepo.save(...).
        final Client entity = new Client();
        entity.setSharedKey("sharedKey");
        entity.setBusinessId("businessId");
        entity.setEmail("email");
        entity.setPhone("phone");
        entity.setDateAdded("dateAdded");
        verify(mockClientRepo).save(entity);
    }

    @Test
    void testCreate_ClientRepoThrowsOptimisticLockingFailureException() {
        // Setup
        final Client client = new Client();
        client.setSharedKey("sharedKey");
        client.setBusinessId("businessId");
        client.setEmail("email");
        client.setPhone("phone");
        client.setDateAdded("dateAdded");

        // Configure ClientRepo.save(...).
        final Client entity = new Client();
        entity.setSharedKey("sharedKey");
        entity.setBusinessId("businessId");
        entity.setEmail("email");
        entity.setPhone("phone");
        entity.setDateAdded("dateAdded");
        when(mockClientRepo.save(entity)).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> clientCrudServicesUnderTest.Create(client)).isInstanceOf(ClientException.class);
    }

    @Test
    void testGetByShareKey() {
        // Setup
        final Client client = new Client();
        client.setSharedKey("sharedKey");
        client.setBusinessId("businessId");
        client.setEmail("email");
        client.setPhone("phone");
        client.setDateAdded("dateAdded");
        final List<Client> expectedResult = List.of(client);

        // Configure ClientRepo.findBySharedKeyContaining(...).
        final Client client1 = new Client();
        client1.setSharedKey("sharedKey");
        client1.setBusinessId("businessId");
        client1.setEmail("email");
        client1.setPhone("phone");
        client1.setDateAdded("dateAdded");
        final List<Client> clients = List.of(client1);
        when(mockClientRepo.findBySharedKeyContaining("sharedKey")).thenReturn(clients);

        // Run the test
        final List<Client> result = clientCrudServicesUnderTest.getByShareKey("sharedKey");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetByShareKey_ClientRepoReturnsNoItems() {
        // Setup
        when(mockClientRepo.findBySharedKeyContaining("sharedKey")).thenReturn(Collections.emptyList());

        // Run the test
        final List<Client> result = clientCrudServicesUnderTest.getByShareKey("sharedKey");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
