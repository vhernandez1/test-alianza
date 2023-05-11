package com.test.alianza.exceptions;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientException extends RuntimeException {



    public ClientException(String message,Throwable cause ) {
        super(message,cause);
    }

}
