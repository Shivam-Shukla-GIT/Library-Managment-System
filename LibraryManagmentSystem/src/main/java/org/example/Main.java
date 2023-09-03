package org.example;
import java.util.Scanner;
import java.util.Map;

public class Main
{
    public static void main( String[] args )
    {
        Map<String, Member> members = DataManager.getMembers();

        Library library = new Library(members);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Library Portal Initialized....");
        System.out.println("---------------------------------");
        System.out.println("1. Enter as a librarian");
        System.out.println("2. Enter as a member");
        System.out.println("3. Exit");
        System.out.println("---------------------------------");

        int choice = 0;

        do {
            try {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println("---------------------------------");

            switch (choice) {
                case 1:
                    // Librarian actions
                    library.librarianMenu(library, scanner);
                    break;
                case 2:
                    // Member actions
                    System.out.print("Name: ");
                    String memberName = scanner.nextLine();

                    System.out.print("Phone No: ");
                    String phoneNumber = scanner.nextLine();

                    for (Member member : members.values()) {
                    if (member.getPhoneNumber().equalsIgnoreCase(phoneNumber) & member.getName().equalsIgnoreCase(memberName)) {
                        System.out.println("Welcome " + member.getName() + ". Member ID: " + member.getMemberID());
                        System.out.println("---------------------------------");
                        Member.memberMenu(member); // Call the memberMenu method
                    } else {
                        System.out.println("Member with Name: "+ memberName +" and Phone No: "+ phoneNumber +" doesn't exist.");
                        System.out.println("---------------------------------");
                        System.out.println("1. Enter as a librarian");
                        System.out.println("2. Enter as a member");
                        System.out.println("3. Exit");
                        System.out.println("---------------------------------");
                        }
                    }
                    break;

                case 3:
                    System.out.println("Thanks for visiting!");
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
        } while (choice != 3);
        scanner.close();
    }
}