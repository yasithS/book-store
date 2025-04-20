package com.bookstore.repository;

import com.bookstore.model.Book;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class BookRepository {
    public static Map<Integer, Book> books = new HashMap<>();
    public static AtomicInteger idCounter = new AtomicInteger();
}
