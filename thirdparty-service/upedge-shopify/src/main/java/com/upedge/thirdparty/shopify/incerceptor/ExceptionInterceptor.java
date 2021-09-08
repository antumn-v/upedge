package com.upedge.thirdparty.shopify.incerceptor;


import com.upedge.thirdparty.shopify.entity.Response;
import com.upedge.thirdparty.shopify.exception.ShopifyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionInterceptor {

    @ExceptionHandler(value = ShopifyException.class)
    @ResponseBody
    public Response shopifyExceptionHandler(ShopifyException e){
        return null;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response exceptionHandler(Exception e){
        Response response = new Response();
        return response.failed(e.getMessage());
    }
}
