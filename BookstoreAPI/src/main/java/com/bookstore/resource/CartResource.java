package com.bookstore.resource;

import com.bookstore.exception.CartItemNotFoundException;
import com.bookstore.exception.CartNotFoundException;
import com.bookstore.model.CartItem;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CartRepository;
import com.bookstore.exception.BookNotFoundException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class CartResource {

    @GET
    public Response getCart(@PathParam("customerId") int customerId) {
        List<CartItem> cart = CartRepository.customerCarts.getOrDefault(customerId, new ArrayList<>());
        return Response.ok(cart).build();
    }

    @POST
    public Response addToCart(@PathParam("customerId") int customerId, CartItem item) {
        Book book = BookRepository.books.get(item.getBookId());
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + item.getBookId() + " not found.");
        }

        List<CartItem> cart = CartRepository.customerCarts.computeIfAbsent(customerId, k -> new ArrayList<>());

        boolean updated = false;
        for (CartItem ci : cart) {
            if (ci.getBookId() == item.getBookId()) {
                ci.setQuantity(ci.getQuantity() + item.getQuantity());
                updated = true;
                break;
            }
        }

        if (!updated) {
            cart.add(item);
        }

        return Response.status(Response.Status.CREATED).entity(cart).build();
    }

    @PUT
    public Response updateCartItem(@PathParam("customerId") int customerId, CartItem item) {
        List<CartItem> cart = CartRepository.customerCarts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer " + customerId);
        }

        boolean updated = false;
        for (CartItem ci : cart) {
            if (ci.getBookId() == item.getBookId()) {
                ci.setQuantity(item.getQuantity());
                updated = true;
                break;
            }
        }

        if (!updated) {
            throw new CartItemNotFoundException("Item not found in cart for book ID " + item.getBookId());
        }

        return Response.ok(cart).build();
    }

    @DELETE
    @Path("/{bookId}")
    public Response removeCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        List<CartItem> cart = CartRepository.customerCarts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer " + customerId);
        }

        boolean removed = cart.removeIf(item -> item.getBookId() == bookId);
        if (!removed) {
            throw new CartItemNotFoundException("Item with book ID " + bookId + " not found in cart");
        }

        return Response.noContent().build();
    }
}
