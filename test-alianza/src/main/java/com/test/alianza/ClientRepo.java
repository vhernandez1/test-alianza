package com.test.alianza;

import com.test.alianza.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepo extends CrudRepository<Client,String> {

    List<Client> findBySharedKeyContaining(String sharedKey);


}