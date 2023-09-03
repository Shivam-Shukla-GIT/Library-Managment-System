package org.example;
import java.util.Date;
import java.util.Scanner;
import java.util.Map;

public class Member {

    public static void memberMenu(Member member) {
        Scanner scanner = new Scanner(System.in);
        int memberChoice = 0;
        do {
            try {
                System.out.println("1. List Available Books");
                System.out.println("2. List My books");
                System.out.println("3. Issue book");
                System.out.println("4. Return book");
                System.out.println("5. Pay fine");
                System.out.println("6. Back");
                System.out.println("---------------------------------");
                System.out.print("Enter your choice: ");
                memberChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.println("---------------------------------");

                switch (memberChoice) {
                    case 1:
                        displayAvailableBooks();
                        break;
                    case 2:
                        listMyBooks(member);
                        break;
                    case 3:
                        issueBook(scanner, member);
                        break;
                    case 4:
                        returnBook(scanner, member);
                        break;
                    case 5:
                        payFine(member);
                        break;
                    case 6:
                        System.out.println("1. Enter as a librarian");
                        System.out.println("2. Enter as a member");
                        System.out.println("3. Exit");
                        System.out.println("---------------------------------");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        System.out.println("---------------------------------");
                }
            } catch (Exception e) {
                    // Handle exceptions here
                    System.out.println("An error occurred");
                    System.out.println("Please enter a valid input and try again.");
                    System.out.println("---------------------------------");
                    scanner.nextLine();
                }
        } while (memberChoice != 6);
    }

    private int memberID;
    private String name;
    private int age;
    private String phoneNumber;
    private int fineBalance;
    private static Date issueDate;

    // Access books and members in other classes (Library, Member, Book)
    static Map<Integer, Book> books = DataManager.getBooks();
    static Map<Integer, Book> borrowedBooks = DataManager.getBorrowedBooks();


    public Member(Map<String, Member> members,int memberID, String name, int age, String phoneNumber) {
        this.memberID = memberID;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.fineBalance = 0;
        members.put(phoneNumber, this);
    }

    // Getters
    public int getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getFineBalance() {
        return fineBalance;
    }

    public static void displayAvailableBooks() {
        int k=0;
        System.out.println("Available Books:");
        for (Book book : books.values()) {
            k++;
            if (book.gettotalCopies() > 0) {
                    System.out.println("Book ID: " + book.getBookID() + ", Title: " + book.getTitle() + ", Auhor: " + book.getAuthor()+", Status: "+ book.getStatus());
            }
            else {
                System.out.println("No Book is available in Library!");
            }
        }
        if (k==0) {
            System.out.println("No Book is available in Library!");
        }
        System.out.println("---------------------------------");
    }
    public static boolean listMyBooks(Member member) {
        int k = 0;
        boolean found = true;
        System.out.println("Your Books:");
        for (Book book : borrowedBooks.values()) {
                k++;
                System.out.println("Book ID: " + book.getBookID() + ", Title: " + book.getTitle());
        }
        if (k == 0) {
            System.out.println("You haven't borrowed any books.");
            found = false;
        }
        System.out.println("---------------------------------");
        return found;
    }

    public static void issueBook(Scanner scanner, Member member) {
        if (member.getBorrowedBooksCount() == 2) {
            System.out.println("You can only borrow a maximum of two books at a time.");
            System.out.println("---------------------------------");
            return;
        }
        if (member.fineBalance!=0){
            System.out.println("You have to pay your due fine to issue another book");
            System.out.println("Fine: "+ member.fineBalance);
            System.out.println("---------------------------------");
            return;
        }
        // Display available books
        displayAvailableBooks();

        System.out.print("Enter the book ID you want to borrow: ");
        int bookID = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        Book bookToBorrow = books.get(bookID);
        if (bookToBorrow == null) {
            System.out.println("Book is not in the library.");
            System.out.println("---------------------------------");
            return;
        }
        if (bookToBorrow.getBorrowedBy() == null & bookToBorrow.getStatus().equalsIgnoreCase("Available")) {
            // Borrow the book
            member.issueDate = new Date();
            System.out.println("Book '" + bookToBorrow.getTitle() + "' with BookID: '" + bookToBorrow.getBookID() + "' has been issued to you.");
            System.out.println("---------------------------------");
            member.borrowBook(bookToBorrow);
            bookToBorrow.setStatus("Issued");
        } else {
            System.out.println("Book with ID " + bookID + " is not available for borrowing.");
            System.out.println("---------------------------------");
        }
    }

    public static void returnBook(Scanner scanner, Member member) {
        // Display your borrowed books
        boolean found=listMyBooks(member);
        if (!found) {
            return;
        }
        if (member.fineBalance!=0){
            System.out.println("You have to pay your due fine to return another book");
            System.out.println("Fine: "+ member.fineBalance);
            System.out.println("---------------------------------");
            return;
        }

        System.out.print("Enter the book ID you want to return: ");
        int bookID = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (borrowedBooks.containsKey(bookID)) {
            // Return the book
            Book bookToReturn = borrowedBooks.get(bookID);
            Date returnDate = new Date();
            long daysBorrowed = (returnDate.getTime() - issueDate.getTime()) / (1000); // Convert milliseconds to days
            if (daysBorrowed > 10) {
                int fine = (int) ((daysBorrowed - 10) * 3); // Calculate fine for extra days
                member.fineBalance = fine;
                borrowedBooks.remove(bookID);
                bookToReturn.setStatus("Available");
                System.out.println("Book '" + bookToReturn.getTitle() + "' has been returned with a fine of " + fine + " rupees.");
            } else {
                borrowedBooks.remove(bookID);
                bookToReturn.setStatus("Available");
                System.out.println("Book '" + bookToReturn.getTitle() + "' has been returned.");
            }
        } else {
            System.out.println("Book with ID " + bookID + " is not in your borrowed books.");
            System.out.println("---------------------------------");
        }
    }
    public static void payFine(Member member) {
        int amount = member.fineBalance;
        if (amount > 0) {
            System.out.println("You had a total fine of Rs. " + amount + ". It has been paid successfully!");
            System.out.println("---------------------------------");
        } else {
            System.out.println("You don't have any fine due!!");
            System.out.println("---------------------------------");
        }
        member.fineBalance = 0;

    }
    public void borrowBook(Book book) {
        // Add the book to the member's list of borrowed books
        borrowedBooks.put(book.getBookID(), book);
    }
    public int getBorrowedBooksCount() {
        int count = 0;

        // Iterate through the borrowed books and increment the count
        for (Book borrowedBook : borrowedBooks.values()) {
            if (borrowedBook != null) {
                count++;
            }
        }
        return count;
    }
}