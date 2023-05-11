package com.test.alianza.Controller;


import com.test.alianza.exceptions.ClientException;
import com.test.alianza.model.Client;
import com.test.alianza.services.ClientCrudServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/clients")
@Slf4j
public class ClientController {

    private final ClientCrudServices clientCrudServices;

    public ClientController(ClientCrudServices clientCrudServices) {
        this.clientCrudServices = clientCrudServices;
    }


    @CrossOrigin
    @PostMapping()
    public ResponseEntity<Client> PostClient(@RequestBody  Client client){
        log.info("Client to create => {}",client);
        try{
            return new ResponseEntity<>(clientCrudServices.Create(client),HttpStatus.OK);
        }catch (ClientException clientException){
            log.error(clientException.getMessage());
            throw clientException;
        }
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Client>> getClients(){
        try{
            return new ResponseEntity<>(clientCrudServices.getAllClients(),HttpStatus.OK);
        }catch (ClientException clientException){
            log.error(clientException.getMessage());
            throw clientException;
        }
    }
    @CrossOrigin
    @GetMapping("/{sharedKey}")
    public ResponseEntity<List<Client>> getClientBySharedKey(@PathVariable("sharedKey") String sharedKey){
        try{
            log.info("sharedKey ={}",sharedKey);
            if("all".equals(sharedKey)){
                return new ResponseEntity<>(clientCrudServices.getAllClients(),HttpStatus.OK);
            }
            return new ResponseEntity<>(clientCrudServices.getByShareKey(sharedKey),HttpStatus.OK);
        }catch (ClientException clientException){
            log.error(clientException.getMessage());
            throw clientException;
        }
    }
}
