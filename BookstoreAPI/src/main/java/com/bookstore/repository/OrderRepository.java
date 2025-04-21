package com.bookstore.repository;

import com.bookstore.model.Order;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderRepository {
    public static Map<Integer, List<Order>> customerOrders = new HashMap<>();
    public static AtomicInteger idCounter = new AtomicInteger();
}
