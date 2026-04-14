package com.psn_cloud.psiconotes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RecursoDuplicadoexception extends RuntimeException {
    public RecursoDuplicadoexception(String message){
        super(message);
    }
}