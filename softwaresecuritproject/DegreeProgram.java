package softwaresecuritproject;

public class DegreeProgram {
    private String name;
    private int salary;
    private double minPreGPA;
    private String interest;
    private String category;
    private double acceptGPA;
    
    //Setters
    public void setProName(String name) {
        this.name = name;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setMinPreGPA(double minPreGPA) {
        this.minPreGPA = minPreGPA;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAcceptGPA(double acceptGPA) {
        this.acceptGPA = acceptGPA;
    }
    
    
    //getters
    public int getSalary() {
        return salary;
    }

    public double getMinPreGPA() {
        return minPreGPA;
    }

    public String getInterest() {
        return interest;
    }
    public double getAcceptGPA() {
        return acceptGPA;
    }

    public String getCategory() {
        return category;
    }
    public String getProName(){
        return name;
    }
}