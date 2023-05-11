package com.test.alianza.services;

import com.test.alianza.model.Client;
import com.test.alianza.ClientRepo;
import com.test.alianza.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ClientCrudServices {

    private final ClientRepo clientRepo;

    public ClientCrudServices(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }


    public Client Create(Client client) {
        try {
            clientRepo.save(client);
            return client;
        } catch (Exception exception) {
            log.error("Error trying when to create client => {}",client);
            throw new ClientException("Error trying when to create client with shared key @ "+client.getSharedKey(),exception);
        }
    }

    public List<Client> getByShareKey(String sharedKey){
        try{
            return clientRepo.findBySharedKeyContaining(sharedKey);
        }catch (Exception exception) {
            log.error("Error trying get client by shared => {}",sharedKey);
            throw new ClientException("Error trying get client by shared => "+sharedKey,exception);
        }
    }

    public List<Client> getAllClients(){
        List<Client> clients = new ArrayList<>();
        try{
             clientRepo.findAll().iterator().forEachRemaining(clients::add);
             return clients;
        }catch (Exception exception) {
            log.error("Error trying get  all clients ");
            throw new ClientException("Error trying get  all clients ",exception);
        }
    }

}
