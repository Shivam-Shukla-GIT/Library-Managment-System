# Library Management System

## Overview

The Library Management System is a Java-based console application that simulates the operations of a library. It allows the librarian to manage books, members, and their interactions, including book issuance, returns, and fine payments. Members can browse available books, borrow and return books, and pay fines if applicable.

## Project Structure

The project consists of several Java classes that work together to create a functional library management system:

1.  **DataManager.java**
    
    -   This class manages data using static maps to store information about books, members, and borrowed books.
    -   Functions:
        -   `getBooks()`: Returns the map of books.
        -   `getMembers()`: Returns the map of members.
        -   `getBorrowedBooks()`: Returns the map of borrowed books.
        
2.  **Main.java**
    
    -   This is the application's entry point, where users can choose to enter as a librarian or a member.
    -   Functions:
        -   `main()`: The main method that presents a menu to users, handles user input and directs them to librarian or member actions.
3.  **Library.java**
    
    -   This class represents the library and contains methods for librarian actions.
    -   Functions:
        -   `librarianMenu()`: Displays a menu for librarian actions.
        -   `registerMember()`: Registers a new member.
                                It takes input as Name(string), Age(int) and Phone number(string).
                                Each member is uniquely identified by phone number.
                                Hence multiple members can't have same phone number.
                                Each member is provided an Member ID(int) which is also unique.
                                Each member is mapped to its unique phone number in members hashmap.
        -   `removeMember()`: Removes a member.
                              It takes input as Name(string) and Phone Number(string).
                              Removes the member if correct phone number is entered and the phone number is registered.
        -   `addBook()`: Adds a book to the library.
                        It takes input as Book title(string), Author(string) and copies(int).
                        Each book is uniquely identified by bookID(int).
                        Hence multiple book/book copies can't have same bookID.
                        Each book is mapped to its unique bookID in books hashmap.
        -   `removeBook()`: Removes a book from the library.
                            It takes input as bookID(int).
                            Removes the book if correct bookID is entered and the bookID status is available.
                            If any member has issued the book then bookID status will be set as issued and hence librarian can't remove the book until it's returned by the member.
        -   `viewMembersWithBooksAndFines()`: Displays member information along with their borrowed books and fines.
        -   `displayAvailableBooks()`: Displays all books along with their status (Available or Issued).
4.  **Member.java**
    
    -   This class represents library members and their interactions with the library system.
    -   Functions:
        -   `memberMenu()`: Displays a menu for member actions.
        -   `displayAvailableBooks()`: Lists available books in the library along with their status (Available or Issued).
        -   `listMyBooks()`: Lists the book details(bookID and book title) of all the borrowed books by a member.
        -   `issueBook()`: Allows a member to borrow a book if the book is avaiable in the library.
                           A member can borrow at max 2 books.
                           A member has to pay the due fine first inorder to issue another book.
        -   `returnBook()`: Allows a member to return a book.
                            If the member wishes to return both books and have fine on both books then he/she needs to return a book and pay its fine and then return another book and pay its fine.
                            So, the member has to pay the due fine first inorder to return another book.
        -   `payFine()`: Allows a member to pay fines.
                          Fine is calculated as 3rs per day after 10 days of issueDate, 1 sec = 1 day, java.util.Date has been used to note issueDate and returnDate.
        -   Various getters and setters for member attributes and book interactions.
5.  **Book.java**
    
    -   This class represents books in the library.
    -   Functions:
        -   `setStatus()`: Sets the status of the book (Available or Issued).
        -   `getStatus()`: Returns the status of the book.
        -   Various getters and setters for book attributes.
6.  **pom.xml**
    
    -   The `pom.xml` file is the project object model file for Maven. It defines project information, dependencies, and build configurations.
    -   It includes a dependency for JUnit for testing purposes.

## Maven Connectivity

This project is configured to use Apache Maven for dependency management and building. The `pom.xml` file defines the project's dependencies, including JUnit for testing.

## Usage

To use the Library Management System, follow these steps:

1.  Run the application.
2.  Choose whether to enter as a librarian or a member.
3.  Follow the on-screen instructions to perform actions such as registering members, adding/removing books, borrowing/returning books, and paying fines.

## Assumptions and Notes

1.  Each member can borrow a maximum of two books at a time.
2.  A fine of Rs. 3 per day is charged for books returned after the 10 days of issue date.
3.  Fines must be paid before borrowing/returning more books.
4.  The librarian can add multiple copies of the same book with different book IDs.
5.  Book IDs are unique identifiers for books.
6.  Members are identified by their phone numbers, which must be unique.
7.  The application does not persist data; all data is stored in memory during runtime.

## Credits

This Library Management System was created by Shivam Shukla.
