package com.bookstore.resource;


import com.bookstore.exception.AuthorNotFoundException;
import com.bookstore.model.Author;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private static Map<Integer, Author> authors = new HashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger();

    @POST
    public Response addAuthor(Author author) {
        int id = idCounter.incrementAndGet();
        author.setId(id);
        authors.put(id, author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @GET
    public Response getAllAuthors(){
        return Response.ok(new ArrayList<>(authors.values())).build();
    }

    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        Author author = authors.get(id);
        if (author == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found.");
        }
        return Response.ok(author).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found.");
        }
        updatedAuthor.setId(id);
        authors.put(id, updatedAuthor);
        return Response.ok(updatedAuthor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        if (authors.remove(id) == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found.");
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthor(@PathParam("id") int id) {
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found.");
        }

        List<Book> result = new ArrayList<>();
        for (Book book : BookRepository.books.values()) {
            if (book.getAuthorId() == id) {
                result.add(book);
            }
        }
        return Response.ok(result).build();
    }
}

