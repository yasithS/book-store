package com.bookstore.exception;

import jakarta.ws.rs.ext.Provider;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        int status = 500;
        String errorMsg = "Internal Server Error";

        if (ex instanceof BookNotFoundException) {
            errorMsg = "Book Not Found";
            status = 404;
        } else if (ex instanceof AuthorNotFoundException){
            errorMsg = "Author Not Found";
            status = 404;
        } else if (ex instanceof CartNotFoundException) {
            errorMsg = "Cart Not Found";
            status = 404;
        } else if (ex instanceof CartItemNotFoundException) {
            errorMsg = "Cart Item Not Found";
            status = 404;
        } else if (ex instanceof OrderNotFoundException) {
            errorMsg = "Order Not Found";
            status = 404;
        } else if (ex instanceof EmptyCartException){
            errorMsg = "Cart is Empty";
            status = 404;
        }

        error.put("error", errorMsg);
        error.put("message", ex.getMessage());

        return Response.status(status).entity(error).build();
    }



}
