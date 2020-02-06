package NonSqlProject.model;

public class Employee {

    private String username;
    private String pass;
    private String firstName;
    private String lastName;
    private String phone;

    public Employee(String username, String pass, String firstName, String lastName, String phone) {
        this.username = username;
        this.pass = pass;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public Employee() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
