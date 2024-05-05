package softwaresecuritproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Student {

    private String name;
    private String id;
    private int salary;
    private String interest;
    private double GPA;
    private String programs = "";

    // methid to setup user information 
    public void studentInfo() {
        setNameS();
        setIdS();
        setSalaryS();
        setGPAS();
        setInterestS();

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSalary(int salary) {
        this.salary = salary;

    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setPrograms(String programs) {
        this.programs = programs;
    }

    public void setProgramsS(String programs) {
        this.programs += (" -" + programs);
    }

    private void setNameS() {
        name = Validation.getValidName();
    }

    private void setIdS() {
        id = Validation.getValidID();
    }

    private void setSalaryS() {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;

        do {
            System.out.print("Please enter the minimum acceptable industry salary (4 digits): ");
            String input = scanner.nextLine();

            if (input.matches("\\d{4}")) {
                salary = Integer.parseInt(input);
                valid = true;
            } else {
                System.out.println("Invalid input. Please enter a 4-digit salary.");
            }
        } while (!valid);

    }

    private void setGPAS() {
        Scanner scanner = new Scanner(System.in);
        String pattern = "\\d(\\.\\d{0,2})?"; // Regular expression pattern for the desired GPA format
        boolean valid = false;

        do {
            System.out.print("Enter your previous GPA: ");
            String input = scanner.nextLine();

            if (input.matches(pattern)) {
                double gpa = Double.parseDouble(input);
                if (gpa >= 0.1 && gpa <= 5.0) {
                    GPA = gpa;
                    valid = true;
                } else {
                    System.out.println("Invalid input. Please enter a GPA between 0.1 and 5.0");
                }
            } else {
                System.out.println("Invalid input. Please enter a GPA in the format X.X or X.XX");
            }
        } while (!valid);

    }

    private void setInterestS() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose your computer programming interest:");
        System.out.println("1-Very High.\n2-High.\n3-Medium.\n4-Low.");
        interest = "";
        boolean valid = false;

        // Validate the interest input
        do {
            System.out.print("Choose a number (1-4):");
            if (scanner.hasNextInt()) {
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        interest = "1Very High";
                        valid = true;
                        break;
                    case 2:
                        interest = "2High";
                        valid = true;
                        break;
                    case 3:
                        interest = "3Medium";
                        valid = true;
                        break;
                    case 4:
                        interest = "4Low";
                        valid = true;
                        break;
                    default:
                        System.out.println("Invalid number. ");
                        break;
                }
            } else {
                String input = scanner.next();
                System.out.println("Invalid input. ");
            }
        } while (!valid);

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getSalary() {
        return salary;
    }

    public double getGPA() {
        return GPA;
    }

    public String getInterest() {
        return interest;
    }

    public String getPrograms() {
        return programs;
    }

    // store info method for storing the student information in the file 
    public void storeInfo() {
        String content = "ID: " + id + "\nStuden Name: " + name
                + "\nMinimum industry salary: " + salary + "\nGPA: " + GPA
                + "\nComputer programming interest: " + interest
                + "\nThe suggested program for student: " + programs
                + "\n----------------------------";
        try {
            File file2 = new File("student's_Information.txt");
            if (!file2.exists()) {
                file2.createNewFile();
                System.out.println("New file created.");
            }
            // Check if the ID already exists in the file 
            boolean idExists = Files.lines(Paths.get("student's_Information.txt")).anyMatch(line -> line.contains("ID: " + id));
            if (idExists) {
                System.out.println("\nStudent information already exists with ID: " + id);
            } else {
                FileWriter fileWriter = new FileWriter(file2, true);
                BufferedWriter writer = new BufferedWriter(fileWriter);
                writer.write(content);
                writer.newLine();
                writer.close();
                System.out.println("Content written to the file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating or writing the file.");
            e.printStackTrace();
        }
    }
}
