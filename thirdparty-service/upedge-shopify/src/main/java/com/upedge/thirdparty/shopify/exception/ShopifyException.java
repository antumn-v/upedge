package com.upedge.thirdparty.shopify.exception;

import org.springframework.http.HttpStatus;

public class ShopifyException extends RuntimeException {
    private HttpStatus status ;

    public ShopifyException(String msg){
        super(msg);
        status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getStatus(){
        return status;
    }
}
