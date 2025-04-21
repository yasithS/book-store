// File: resource/OrderResource.java
package com.bookstore.resource;

import com.bookstore.model.CartItem;
import com.bookstore.model.Order;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CartRepository;
import com.bookstore.repository.OrderRepository;
import com.bookstore.exception.EmptyCartException;
import com.bookstore.exception.OrderNotFoundException;
import com.bookstore.exception.BookNotFoundException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @POST
    public Response placeOrder(@PathParam("customerId") int customerId) {
        List<CartItem> cart = CartRepository.customerCarts.get(customerId);
        if (cart == null || cart.isEmpty()) {
            throw new EmptyCartException("Cart is empty or not found for customer " + customerId);
        }

        double total = 0.0;
        for (CartItem item : cart) {
            Book book = BookRepository.books.get(item.getBookId());
            if (book == null) {
                throw new BookNotFoundException("Book with ID " + item.getBookId() + " not found.");
            }
            total += book.getPrice() * item.getQuantity();
        }

        int orderId = OrderRepository.idCounter.incrementAndGet();
        Order order = new Order(orderId, customerId, new ArrayList<>(cart), total);

        List<Order> orders = OrderRepository.customerOrders.computeIfAbsent(customerId, k -> new ArrayList<>());
        orders.add(order);

        cart.clear(); // Empty cart after placing order
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @GET
    public Response getOrders(@PathParam("customerId") int customerId) {
        List<Order> orders = OrderRepository.customerOrders.getOrDefault(customerId, new ArrayList<>());
        return Response.ok(orders).build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrderById(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId) {
        List<Order> orders = OrderRepository.customerOrders.get(customerId);
        if (orders != null) {
            for (Order o : orders) {
                if (o.getId() == orderId) {
                    return Response.ok(o).build();
                }
            }
        }
        throw new OrderNotFoundException("Order with ID " + orderId + " not found for customer " + customerId);
    }
}