package com.test.alianza.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class Client {

    @Id
    private String sharedKey;
    private String businessId;
    private String email;
    private String phone;
    private String dateAdded;

}
