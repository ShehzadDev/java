package softwaresecuritproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SoftwareSecuritProject {

    private static boolean valid = true;
    private static int state;
    private static List<DegreeProgram> programList = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("___Welcome to our Recommendation system___");
        loadPrograms();
        while (valid) {
            System.out.println("Are you an admin or a student?");
            System.out.println("1-Student."
                    + "\n2-Admin."
                    + "\n3-Exit."
            );

            System.out.print("Choose a number:");
            String input = scanner.nextLine();

            //ensure that the user enters only digit
            if (input.matches("[0-9]+")) {
                state = Integer.parseInt(input);
                switch (state) {
                    // If the user is a student
                    case 1:
                        Student student = new Student();
                        student.studentInfo();
                        RecomendedPrograms(student);
                        valid = false;
                        break;
                    // If the user is an admin
                    case 2:
                        Admin admin = new Admin(valid);
                        valid = !admin.getValid();
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println("** Invalid number. **");
                        break;
                }
            } else {
                System.out.println("** Invalid input. Please enter a number. **");
            }
        }
    }

    // this method recommend the sutable program for the student 
    private static void RecomendedPrograms(Student student) {

        //filter user requirment based on the indestry requirment 
        for (int index = 0; index < programList.size(); index++) {
            if (student.getGPA() < programList.get(index).getMinPreGPA()
                    || programList.get(index).getSalary() < student.getSalary()
                    || (programList.get(index).getInterest().charAt(0) - 48) < (student.getInterest().charAt(0) - 48)) {
                programList.remove(index);
                index--;
            }
        }

        System.out.println("________________________________________________");

        for (int index = 0; index < programList.size(); index++) {
            System.out.println("Suggested Program Name: " + programList.get(index).getProName());
            System.out.println("Minimum industry salary: " + programList.get(index).getSalary());
            System.out.println("Minimum required previous GPA: " + programList.get(index).getMinPreGPA());
            System.out.println("Industry job category: " + programList.get(index).getCategory());
            System.out.println("Required acceptable GPA in industry: " + programList.get(index).getAcceptGPA());
            System.out.println("\nBased on the acceptable GPA in industry you have to study for " + calcStudyHours(student.getGPA(), programList.get(index).getAcceptGPA()) + " hours");
            System.out.println("-------------------------------------------------");
            student.setProgramsS(programList.get(index).getProName());

        }
        if (programList.isEmpty()) {
            System.out.println("Sorry, there is no suitable program for you.\n");

        }
        // store student information after givinnig the suggestions 
        student.storeInfo();
    }

    // this method calculate the study hours
    private static String calcStudyHours(double SGPA, double AGPA) {
        double hours;
        hours = Math.ceil((AGPA - SGPA) * 10);
        return (hours < 0 ? "0" : String.valueOf(hours));

    }

    // this method load the programs from a file 
    private static void loadPrograms() {
        try {
            File file = new File("degreePrograms.txt");

            if (!file.exists()) {
                file.createNewFile();
            }
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                // creatting a object of the dgree program to store each program information 
                DegreeProgram dp = new DegreeProgram();

                String nextLine = fileScanner.nextLine();
                dp.setProName(nextLine);

                nextLine = fileScanner.nextLine();
                dp.setSalary(Integer.parseInt(nextLine.substring(nextLine.lastIndexOf(":") + 1).trim()));

                nextLine = fileScanner.nextLine();
                dp.setMinPreGPA(Double.parseDouble(nextLine.substring(nextLine.lastIndexOf(":") + 1).trim()));

                nextLine = fileScanner.nextLine();
                dp.setInterest(nextLine.substring(nextLine.lastIndexOf(":") + 1).trim());

                nextLine = fileScanner.nextLine();
                dp.setCategory(nextLine.substring(nextLine.lastIndexOf(":") + 1).trim());

                nextLine = fileScanner.nextLine();
                dp.setAcceptGPA(Double.parseDouble(nextLine.substring(nextLine.lastIndexOf(":") + 1).trim()));

                fileScanner.nextLine();

                programList.add(dp);
            }
            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the student's Information file.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the student's Information file.");

        }

    }
}
