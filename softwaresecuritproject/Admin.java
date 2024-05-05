package softwaresecuritproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin {

    // allowed usernames and the common password
    private boolean returnToMenuFlag = false;
    // 4 allowed usernames
    
    private final String[] allowedUsernames = {"Sabreen", "Maya", "Manar", "Bushra"};
    //hashed password
    private final String commonPassword = "cbf36716337d7ba7ad893f97138fe09b";
    private boolean valid;
    private List<Student> studentsList = new ArrayList<>();
    
    public Admin(boolean valid) {
        this.valid = valid;
        AtunticateAdmin();
    }
    
    private void AtunticateAdmin() {
        Scanner scanner = new Scanner(System.in);
        
        while (!returnToMenuFlag) {
            System.out.println("\nDo you want to:");
            
            System.out.print("1-Login."
                    + "\n2-return."
                    + "\nChoose a number:");

            // read user provided input
            String choiceInput = scanner.nextLine();

            //ensure that the user enters only digits
            if (choiceInput.matches("\\d+")) {
                int choice = Integer.parseInt(choiceInput);

                /* below flag becomes true if logged in successfully.
              it doesn't change when choice = 2 is pressed to return to the main menu.*/
                valid = false;
                // below if's body runs when choice is 1
                if (choice == 1) {
                    // for login
                    // 2. prompt for username and password
                    // read user provided username
                    String username = Validation.getValidName();
                    System.out.print("Enter password: ");
                    // read user provided password, convert to hash, and store in a variable.
                    String hashedPassword = hashPassword(scanner.nextLine());

                    //check if username and hashed password are valid
                    for (String allowedUsername : allowedUsernames) {
                        if (username.equals(allowedUsername) && hashedPassword.equals(commonPassword)) {
                            // correct username and password entered
                            valid = true;
                            break;
                        }
                    }
                    if (valid) {
                        // when logged in, below flag used to exit loop which prompts to enter username and password
                        returnToMenuFlag = true;
                        // display message when logged in
                        System.out.println("You have been logged in successfully.");
                        loadData();
                        adminMenu();
                    } else {
                        System.out.println("Invalid username or password. Try again.");
                        // returnToMenuFlag is not changed so current loop's condition passes
                        // and again prompts for 1-login and 2-return to menu
                    }
                } else if (choice == 2) {
                    System.out.println("Returning to the previous menu.\n");
                    // while loop exits when 2 is entered
                    // (as ''!returnToMenuFlag' condition becomes
                    // false) due to the below flag
                    returnToMenuFlag = true;
                }  else {
                    // neither 1 nor 2 entered
                    System.out.println("Invalid input. Please enter a valid choice.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid choice.");
            }
        }
    }

//the method that conver the enterd password into a hashed value to achieve integrity
    private String hashPassword(String password) {
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Add password bytes to digest
            md.update(password.getBytes());
            // Get the hash's bytes
            byte[] hashedBytes = md.digest();
            // This bytes[] has bytes in decimal format;
            // Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                sb.append(Integer.toString((hashedByte & 0xff) + 0x100, 16).substring(1));
            }
            // Return the hashed password
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public boolean getValid() {
        return this.valid;
    }
    
    private void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("\nDo you want to:");
            
            System.out.print("1-View All Students.\n2-Search for Student. \n3-Exit.\nChoose a number:");

            // read user provided input
            String choiceInput = scanner.nextLine();

            //ensure that the user enters only digits
            if (choiceInput.matches("\\d+")) {
                choice = Integer.parseInt(choiceInput);

                /*based on the user input the new list will appear choice one will display all students records
                choice 2 will let the admin to search for spicific student 
                choice 3 will end the program.*/
                if (choice == 1) {
                    ViewStudentData();
                    
                } else if (choice == 2) {
                    SearchForStudent();
                    
                } else if (choice == 3) {
                    System.exit(0);
                    
                } else {
                    // neither 1, 2, 3 entered
                    System.out.println("Invalid input. Please enter a valid choice.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid choice.");
            }
        } while (choice != 3);
    }
    
    private void loadData() {
        try {
            File file = new File("student's_Information.txt");
            
            if (!file.exists()) {
                file.createNewFile();
            }
            
            Scanner fileScanner = new Scanner(file);
            boolean studentFound = false;
            
            while (fileScanner.hasNextLine()) {
                //create an object of each student and store their information reading it from the file 
                Student student = new Student();
                String nextLine = fileScanner.nextLine();
                student.setId(nextLine.substring(nextLine.lastIndexOf(":") + 1).trim());
                nextLine = fileScanner.nextLine();
                student.setName(nextLine.substring(nextLine.lastIndexOf(":") + 1).trim());
                nextLine = fileScanner.nextLine();
                student.setSalary(Integer.parseInt(nextLine.substring(nextLine.lastIndexOf(":") + 1).trim()));
                nextLine = fileScanner.nextLine();
                student.setGPA(Double.parseDouble(nextLine.substring(nextLine.lastIndexOf(":") + 1).trim()));
                nextLine = fileScanner.nextLine();
                student.setInterest(nextLine.substring(nextLine.lastIndexOf(":") + 1).trim());
                nextLine = fileScanner.nextLine();
                student.setPrograms(nextLine.substring(nextLine.lastIndexOf(":") + 1).trim());
                
                fileScanner.nextLine();
                
                studentsList.add(student);
                
            }
            
            fileScanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the student's Information file.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the student's Information file.");
            
        }
        
    }

    // this function display all student information to the admin
    private void ViewStudentData() {
        System.out.println("\n_______________Students Data________________ ");
        if (studentsList.isEmpty()) {
            System.out.println("** No data exist. **");
            
        } else {
            for (Student student : studentsList) {
                System.out.println("student ID: " + student.getId());
                System.out.println("student Name: " + student.getName());
                System.out.println("student GPA: " + student.getGPA());
                System.out.println("Salary: " + student.getSalary());
                System.out.println("Computer programming interest: " + student.getInterest().substring(1));
                System.out.println("The suggested program for student: " + student.getPrograms());
                System.out.println("-------------------------------------------------");
                
            }
        }
        
    }

    // this function enable th admin to search for a student by his/her ID
    private void SearchForStudent() {
        
        System.out.println("\nTo view a student information: ");
        
        String studentID = Validation.getValidID();
        if (studentsList.isEmpty()) {
            System.out.println("** No data exist. **");
            
        } else {
            boolean userFound = false;
            for (Student student : studentsList) {
                if (student.getId().equals(studentID)) {
                    System.out.println("\n_______________Student Data________________ ");
                    
                    userFound = true;
                    System.out.println("student ID: " + student.getId());
                    System.out.println("student Name: " + student.getName());
                    System.out.println("student GPA: " + student.getGPA());
                    System.out.println("Salary: " + student.getSalary());
                    System.out.println("Computer programming interest: " + student.getInterest());
                    System.out.println("The suggested program for student: " + student.getPrograms());
                    System.out.println("-------------------------------------------------");
                    
                    break;
                }
            }
            if (!userFound) {
                System.out.println("** User data does not exist. **");
            }
            
        }
        
    }
    
}
