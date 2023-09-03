package org.example;
import java.util.Scanner;
import java.util.Map;

public class Library {

    private int currentMemberID;
    private static int bookID;
    // Access books and members in other classes (Library, Member, Book)
    static Map<Integer, Book> books = DataManager.getBooks();
    static Map<String, Member> members = DataManager.getMembers();
    static Map<Integer, Book> borrowedBooks = DataManager.getBorrowedBooks();

    public Library(Map<String, Member> members) {
        this.members = members;
        currentMemberID = 1;
        bookID = 1;
    }
    public static void librarianMenu(Library library, Scanner scanner) {
        int librarianChoice = 0;
        do {
            try {
                System.out.println("1. Register a member");
                System.out.println("2. Remove a member");
                System.out.println("3. Add a book");
                System.out.println("4. Remove a book");
                System.out.println("5. View all members along with their books and fines to be paid");
                System.out.println("6. View all books");
                System.out.println("7. Back");
                System.out.println("---------------------------------");
                System.out.print("Enter your choice: ");
                librarianChoice = scanner.nextInt();
                scanner.nextLine();
                System.out.println("---------------------------------");

                switch (librarianChoice) {
                    case 1:

                        System.out.print("Name: ");
                        String memberName = scanner.nextLine();

                        System.out.print("Age: ");
                        int memberAge = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Phone no: ");
                        String memberPhoneNumber = scanner.nextLine();
                        System.out.println("---------------------------------");

                        library.registerMember(memberName, memberAge, memberPhoneNumber);
                        break;
                    case 2:

                        System.out.print("Name: ");
                        String usernameToRemove = scanner.nextLine();

                        System.out.print("Phone no: ");
                        String phoneNumberToRemove = scanner.nextLine();
                        System.out.println("---------------------------------");

                        library.removeMember(usernameToRemove, phoneNumberToRemove);
                        break;
                    case 3:

                        System.out.print("Book title: ");
                        String titleToAdd = scanner.nextLine();

                        System.out.print("Author: ");
                        String authorToAdd = scanner.nextLine();

                        System.out.print("Copies: ");
                        int totalCopiesToAdd = Integer.parseInt(scanner.nextLine());

                        System.out.println("---------------------------------");

                        addBook(titleToAdd, authorToAdd, totalCopiesToAdd);
                        break;
                    case 4:

                        System.out.println("Book ID: ");
                        int bookIDToRemove = Integer.parseInt(scanner.nextLine());

                        System.out.println("---------------------------------");

                        removeBook(bookIDToRemove);
                        break;
                    case 5:

                        viewMembersWithBooksAndFines();
                        break;
                    case 6:

                        Member.displayAvailableBooks();
                        break;
                    case 7:
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
        } while (librarianChoice != 7);
    }
    public void registerMember(String name, int age, String phoneNumber) {
        boolean found = false;
        for (Member member : members.values()) {
            if (member.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
                System.out.println("A member with this phone number already exists.");
                System.out.println("---------------------------------");
                found = true;
                break; // Stop searching once a matching member is found
            }
        }
        if (!found) {
            Member member = new Member(members ,currentMemberID,name, age, phoneNumber);
            members.put(phoneNumber, member);
            currentMemberID++;
            System.out.println("Member Successfully Registered with Member ID: " + member.getMemberID()+"!");
            System.out.println("---------------------------------");
        }
    }
    public void removeMember(String username, String phoneNumber) {
        boolean found = false;
        for (Member member : members.values()) {
            if (member.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
                members.remove(phoneNumber);
                System.out.println("Member with Phone no: " + member.getPhoneNumber() + " has been removed.");
                System.out.println("---------------------------------");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Member with the provided details does not exist.");
            System.out.println("---------------------------------");
        }
    }
    public static void addBook(String title, String author, int Copies) {
        int totalCopies = Copies;
        for (Book book: books.values()) {
            if (book.getTitle().equalsIgnoreCase(title) & book.getAuthor().equalsIgnoreCase(author)) {
                book.increasetotalCopies(Copies);
                totalCopies = book.gettotalCopies();
                break;
            }
        }
        for (int i=0; i<Copies; i++) {
            Book book = new Book(books, bookID,title, author, totalCopies);
            books.put(bookID, book);
            bookID++;
        }
        System.out.println("Book Added Successfully!");
        System.out.println("---------------------------------");
    }
    public static void removeBook(int bookID) {
        boolean found = false;
        for (Book book : books.values()) {
            if ((book.getBookID()==(bookID))&(book.getStatus().equalsIgnoreCase("Issued"))){
                System.out.println("Book has already been issued to a member.");
                System.out.println("Can't be removed");
                System.out.println("---------------------------------");
                return;
            }
            if (book.getBookID()==(bookID)){
                    System.out.println("Book details: ");
                    System.out.println("BookID: " + book.getBookID());
                    System.out.println("Book title: " + book.getTitle());
                    System.out.println("Author: " + book.getAuthor());
                    System.out.println("Book Removed Successfully!");
                    System.out.println("---------------------------------");
                    books.remove(bookID);
                    book.decreasetotalCopies();
                    found = true;
                    break;
            }
        }
        if (!found) {
            System.out.println("Book with the provided details does not exist.");
            System.out.println("---------------------------------");
        }
    }
    public static void viewMembersWithBooksAndFines() {
        if (members.isEmpty()) {
            System.out.println("No member is registered yet.");
            System.out.println("---------------------------------");
            return;
        }
        System.out.println("Members with Books and Fines:");
        for (Member member : members.values()) {
            System.out.println("Member ID: " + member.getMemberID());
            System.out.println("Name: " + member.getName());
            System.out.println("Phone Number: " + member.getPhoneNumber());

            // Display borrowed books
            System.out.println("Borrowed Books:");
            boolean hasBorrowedBooks = false;
            for (Book book : borrowedBooks.values()) {
                if (book != null) {
                    System.out.println("Book ID: " + book.getBookID() + ", Title: " + book.getTitle());
                    hasBorrowedBooks = true;
                }
            }
            int fineBalance = member.getFineBalance();
            if (fineBalance > 0) {
                if (!hasBorrowedBooks) {
                    System.out.println("No books borrowed.");
                }
                System.out.println("Fine to Be Paid: Rs. " + fineBalance);
            }
            if (!hasBorrowedBooks && fineBalance == 0) {
                System.out.println("No books borrowed, and no fines to be paid.");
            }
        }
        System.out.println("---------------------------------");
    }
}