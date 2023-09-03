package org.example;
import java.util.Date;
import java.util.Map;

public class Book {
    private int bookID;
    private String title;
    private String author;
    private int totalCopies;
    private static int availableCopies;
    private static Member borrowedBy; // Member who borrowed the book
    private Date dueDate; // Due date for returning the book
    private String status;

    public Book(Map<Integer, Book> books, int bookID, String title, String author, int totalCopies) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.borrowedBy = null; // Initially not borrowed
        this.dueDate = null; // Initially no due date
        this.status = "Available";
        books.put(bookID,this);
    }
    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    // Getter for the status
    public String getStatus() {
        return status;
    }

    public int getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int gettotalCopies() {
        return totalCopies;
    }

    public void increasetotalCopies(int copies) {
         totalCopies = gettotalCopies() + copies;
    }
    public void decreasetotalCopies() {
        totalCopies = gettotalCopies() - 1;
    }
    public Member getBorrowedBy() {
        return borrowedBy;
    }
}
