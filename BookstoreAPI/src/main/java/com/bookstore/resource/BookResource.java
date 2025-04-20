package com.bookstore.resource;

import com.bookstore.model.Book;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.repository.BookRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.bookstore.repository.BookRepository.idCounter;

@Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public class BookResource {



        @POST
        public Response addBook(Book book) {
            int id = idCounter.incrementAndGet();
            book.setId(id);
            BookRepository.books.put(id, book);
            return Response.status(Response.Status.CREATED).entity(book).build();
        }

        @GET
        public Response getAllBooks() {
            return Response.ok(new ArrayList<>(BookRepository.books.values())).build();
        }

        @GET
        @Path("/{id}")
        public Response getBookById(@PathParam("id") int id) {
            Book book = BookRepository.books.get(id);
            if (book == null) {
                throw new BookNotFoundException("Book with ID " + id + " not found.");
            }
            return Response.ok(book).build();
        }

        @PUT
        @Path("/{id}")
        public Response updateBook(@PathParam("id") int id, Book updatedBook) {
            if (!BookRepository.books.containsKey(id)) {
                throw new BookNotFoundException("Book with ID " + id + " not found.");
            }
            updatedBook.setId(id);
            BookRepository.books.put(id, updatedBook);
            return Response.ok(updatedBook).build();
        }

        @DELETE
        @Path("/{id}")
        public Response deleteBook(@PathParam("id") int id) {
            if (BookRepository.books.remove(id) == null) {
                throw new BookNotFoundException("Book with ID " + id + " not found.");
            }
            return Response.noContent().build();
        }
}
