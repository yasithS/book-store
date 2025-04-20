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
        }

        error.put("error", errorMsg);
        error.put("message", ex.getMessage());

        return Response.status(status).entity(error).build();
    }



}
