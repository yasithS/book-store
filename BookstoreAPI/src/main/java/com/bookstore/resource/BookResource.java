package com.bookstore.resource;

import com.bookstore.model.Book;
import com.bookstore.exception.BookNotFoundException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public class BookResource {

        private static Map<Integer, Book> books = new HashMap<>();
        private static AtomicInteger idCounter = new AtomicInteger();

        @POST
        public Response addBook(Book book) {
            int id = idCounter.incrementAndGet();
            book.setId(id);
            books.put(id, book);
            return Response.status(Response.Status.CREATED).entity(book).build();
        }

        @GET
        public Response getAllBooks() {
            return Response.ok(new ArrayList<>(books.values())).build();
        }

        @GET
        @Path("/{id}")
        public Response getBookById(@PathParam("id") int id) {
            Book book = books.get(id);
            if (book == null) {
                throw new BookNotFoundException("Book with ID " + id + " not found.");
            }
            return Response.ok(book).build();
        }

        @PUT
        @Path("/{id}")
        public Response updateBook(@PathParam("id") int id, Book updatedBook) {
            if (!books.containsKey(id)) {
                throw new BookNotFoundException("Book with ID " + id + " not found.");
            }
            updatedBook.setId(id);
            books.put(id, updatedBook);
            return Response.ok(updatedBook).build();
        }

        @DELETE
        @Path("/{id}")
        public Response deleteBook(@PathParam("id") int id) {
            if (books.remove(id) == null) {
                throw new BookNotFoundException("Book with ID " + id + " not found.");
            }
            return Response.noContent().build();
        }
}
