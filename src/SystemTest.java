import model.*;
import service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;

public class SystemTest {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static EquipmentService equipmentInventory = new EquipmentService();
    private static AuthService authService = new AuthService();
    private static BorrowingService borrowingService = new BorrowingService(equipmentInventory);

    public static void main(String[] args) throws IOException {
        setObjects();
        testUsers();
    }


    // log out page
    private static void authenticationPage() throws IOException {
        int choice;
        do {
            System.out.println(
                    """
                    [1] Log in
                    [2] Exit"""
                    // [3] sign in
            );

            choice = Integer.parseInt(readString("> "));

            switch (choice) {
                case 1 -> logIn();
                case 2 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid input");
            }
        } while (choice != 2);
    }

    /*
    entry point of the system
    if the credentials are valid, let the user enter the main menu. otherwise, return them to the log out page
     */
    private static void logIn() throws IOException {
        System.out.println("===== LOGGING IN =====");
        String userEmail = readString("Enter email: ");
        String password = readString("Enter password: ");
        authService.login(userEmail, password);

        if (authService.getCurrentUser() == null) {
            System.out.println("Invalid credentials");
        }
        else {
            mainMenu(authService);
        }
    }

    // tests if current user of authservice changes each login()
    private static void testUsers() {
        String[] emails = {
                "camille.santos@university.edu",
                "liam.mendoza@university.edu",
                "sofia.reyes@university.edu",
                "noah.bautista@university.edu",
                "chloe.villanueva@university.edu",
                "ramon.cruz@university.edu",
                "elena.navarro@university.edu",
                "marco.torres@university.edu",
                "patricia.lim@university.edu",
                "gabriel.ramos@university.edu"
        };

        String[] passwords = {
                "Pass#1001",
                "Pass#1002",
                "Pass#1003",
                "Pass#1004",
                "Pass#1005",
                "FacPass#2001",
                "asdf",
                "FacPass#2002",
                "FacPass#2003",
                "FacPass#2004",
                "FacPass#2005"
        };

        for (int i = 0; i < emails.length; i++) {
            authService.login(emails[i], passwords[i]);

            if (authService.getCurrentUser() == null) {
                System.out.println("Invalid credentials");
            }
            else {
                System.out.println("=========================");

                System.out.println("ID: " + authService.getCurrentUser().getUserID());
                System.out.println("Name: " + authService.getCurrentUser().getUserName());
                System.out.println("Email: " + authService.getCurrentUser().getUserEmail());
                System.out.println("Type: " + authService.getCurrentUser().getUserType());

                if (authService.getCurrentUser() instanceof Student user) {
                    System.out.println("Course: " + user.getCourse());
                    System.out.println("Year Level: " + user.getYearLevel());
                }
                else if (authService.getCurrentUser() instanceof Faculty user) {
                    System.out.println("Department: " + user.getDepartment());
                }
                System.out.println();
            }
        }
    }

    // interface for a student user
    private static void mainMenu(Student user) throws IOException {
        System.out.println("===== MAIN MENU =====");
        System.out.println(
                """
                [1] Dashboard
                [2] Equipments
                [3] Borrow
                [4] Log out"""
        );

        int choice = Integer.parseInt(readString("> "));

        switch (choice) {
            case 1 -> {
                System.out.println("===== DASHBOARD =====");
            }
            case 2 -> {
                System.out.println("===== EQUIPMENTS =====");
                System.out.println("Available equipments:");
                for (Equipment equipment : equipmentInventory.getAvailableEquipment()) {
                    System.out.println("ID: " + equipment.getEquipmentID());
                    System.out.println("Name: " + equipment.getEquipmentName());
                    System.out.println("Type: " + equipment.getEquipmentType());
                    System.out.println("Available Quantity: " + equipment.getAvailableQuantity());
                    System.out.println();
                }
            }
            case 3 -> {
                System.out.println("===== BORROW =====");
                System.out.println("Borrowing functionality is not implemented yet.");
            }
            case 4 -> {
                authService.logout();
                System.out.println("Logged out successfully.");
            }
            default -> System.out.println("Invalid input");
        }
    }


    // interface for a faculty user
    private static void mainMenu(Faculty user) {
        System.out.println("===== MAIN MENU =====");
        System.out.println(
                """
                [1] Dashboard
                [2] Equipments
                [3] Borrow
                [4] Log out"""
        );
    }

    // instantiate objects for the system to use
    private static void setObjects() {

        // equipments
        Equipment[] equipments = {
                new Laptop("EQ-1001", "Work Laptop", 20, 15, "macOS Sonoma", "MacBook Air 13\" M3"),
                new Laptop("EQ-1002", "Developer Laptop", 12, 8, "Ubuntu 24.04 LTS", "ThinkPad X1 Carbon Gen 12"),
                new Laptop("EQ-1003", "Office Laptop", 30, 22, "Windows 11 Pro", "Dell Latitude 5440"),
                new Laptop("EQ-1004", "Design Workstation", 6, 4, "macOS Sequoia", "MacBook Pro 16\" M3 Max"),
                new Laptop("EQ-1005", "Field Laptop", 10, 7, "Windows 11 Pro", "HP EliteBook 840 G11"),
                new Projector("EQ-2001", "Conference Room Projector", 8, 5, "Epson", "EB-FH52"),
                new Projector("EQ-2002", "Classroom Projector", 15, 11, "BenQ", "MW560"),
                new Projector("EQ-2003", "Portable Presentation Projector", 6, 4, "ViewSonic", "M1 Pro"),
                new Projector("EQ-2004", "Auditorium Laser Projector", 3, 2, "Sony", "VPL-FHZ85"),
                new Projector("EQ-2005", "Short-Throw Meeting Projector", 5, 3, "Optoma", "ZW350ST")
        };

        // temporary pass to add equipments to the inventory
        Faculty user = new Faculty("F001", "Faculty member 1", "facultymember1@example.com", "fm1-001", "CEIT");
        for (Equipment e : equipments) {
            equipmentInventory.addEquipment(e, user);
        }

        // users
        User[] users = {
                new Student("STU-1001", "Camille Santos", "camille.santos@university.edu", "Pass#1001", "BS Computer Science", "3rd Year"),
                new Student("STU-1002", "Liam Mendoza", "liam.mendoza@university.edu", "Pass#1002", "BS Information Technology", "2nd Year"),
                new Student("STU-1003", "Sofia Reyes", "sofia.reyes@university.edu", "Pass#1003", "BS Civil Engineering", "4th Year"),
                new Student("STU-1004", "Noah Bautista", "noah.bautista@university.edu", "Pass#1004", "BS Accountancy", "1st Year"),
                new Student("STU-1005", "Chloe Villanueva", "chloe.villanueva@university.edu", "Pass#1005", "BS Psychology", "3rd Year"),
                new Faculty("FAC-2001", "Dr. Ramon Cruz", "ramon.cruz@university.edu", "FacPass#2001", "Computer Science"),
                new Faculty("FAC-2002", "Prof. Elena Navarro", "elena.navarro@university.edu", "FacPass#2002", "Information Systems"),
                new Faculty("FAC-2003", "Engr. Marco Torres", "marco.torres@university.edu", "FacPass#2003", "Civil Engineering"),
                new Faculty("FAC-2004", "Prof. Patricia Lim", "patricia.lim@university.edu", "FacPass#2004", "Accountancy and Finance"),
                new Faculty("FAC-2005", "Dr. Gabriel Ramos", "gabriel.ramos@university.edu", "FacPass#2005", "Psychology")
        };

        for (User u : users) {
            authService.registerUser(u);
        }

    }

    private static String readString(String prompt) throws IOException {
        System.out.print(prompt);
        return reader.readLine();
    }
}
