package com.bookstore.repository;

import com.bookstore.model.CartItem;
import java.util.*;

public class CartRepository {
    public static Map<Integer, List<CartItem>> customerCarts = new HashMap<>();
}
