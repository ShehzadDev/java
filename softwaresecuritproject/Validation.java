package softwaresecuritproject;

import java.util.Scanner;
// used for common validation used in the program
public class Validation {
    //method for validating the enterd Name

    public static String getValidName() {
        Scanner scanner = new Scanner(System.in);
        String name;
        // Validate the name (assuming it should only contain alphabetic characters)
        System.out.print("Enter your name: ");
        name = scanner.nextLine();
        while (!name.matches("[a-zA-Z]+") || name.length() < 2 || name.length() > 15) {
            System.out.println("Invalid name. Please enter a name with 2 to 15 alphabetic characters.");
            System.out.print("Enter your name: ");
            name = scanner.nextLine();
        }
        return name;
    }
    //method for validating the enterd ID
    public static String getValidID() {
        Scanner scanner = new Scanner(System.in);
        String id;
        System.out.print("Enter your ID: ");
        id = scanner.nextLine();
        // Validate the ID (assuming it should only contain numeric characters)
        while (id.length() != 7 || !id.matches("\\d+")) {
            System.out.println("Invalid ID. Please enter only a 7-digit numeric characters.");
            System.out.print("Enter your ID: ");
            id = scanner.nextLine();
        }
        return id;
    }

}
