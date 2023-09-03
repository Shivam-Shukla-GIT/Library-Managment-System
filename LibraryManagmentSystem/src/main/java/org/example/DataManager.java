package org.example;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private static Map<Integer, Book> books = new HashMap<>();
    private static Map<String, Member> members = new HashMap<>();
    private static Map<Integer, Book> borrowedBooks = new HashMap<>();

    public static Map<Integer, Book> getBooks() {
        return books;
    }
    public static Map<String, Member> getMembers() {
        return members;
    }
    public static Map<Integer, Book> getBorrowedBooks(){
        return borrowedBooks;
    }
}