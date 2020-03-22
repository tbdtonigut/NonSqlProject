package NonSqlProject.view;

import NonSqlProject.DAO.DAO;
import NonSqlProject.exception.MyException;
import NonSqlProject.model.Employee;
import NonSqlProject.model.Enum.Type;
import NonSqlProject.model.Incidence;
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class view {

    private static Employee user;
    private static DAO dao = new DAO();

    public static void main(String[] args) {
        try {
            int op;
            do {
                LogIn();
                op = InputAsker.askInt("Select an Option: ");
                switch (op) {
                    case 1:
                        createInc();
                        break;
                    case 2:
                        deleteInc();
                        break;
                    case 3:
                        showInc();
                        break;
                    case 4:
                        
                        break;
                    case 5:
                        manageEmployees();
                        break;
                    case 0:
                        System.out.println("Closing...");
                        break;
                    default:
                        System.out.println("-- Wrong arguments --");
                }
            } while (op != 0);
        } catch (MyException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void createInc() throws MyException {
        Type type;
        ArrayList<Employee> employees = dao.getAllDocumentsEmployee();
        int index = 1;
        System.out.println("-- EMPLOYEES --");
        for (Employee e : employees) {
            System.out.println(index + ". " + e.getUsername());
        }
        int recipientIndex = InputAsker.askInt("Select the recipient: ", 1, employees.size());
        Employee recipient = employees.get(recipientIndex - 1);
        String details = InputAsker.askString("Introduce incidence details:");
        System.out.println(" 1. Normal\n" + "2. Urgent");
        int typeIndex = InputAsker.askInt("Select a incidence type: ", 1, 2);
        if (typeIndex == 1) {
            type = Type.NORMAL;
        } else {
            type = Type.URGENT;
        }
        Incidence incidence = new Incidence(LocalDateTime.now(), user, recipient, details, type);
        dao.insertIncidence(incidence);
        System.out.println("Incidence successfully created");
    }

    public static void deleteInc() throws MyException {
        ArrayList<Incidence> incidences = dao.getAllDocumentsIncidence();
        System.out.println("-- INCIDENCES --");
        for (Incidence i : incidences) {
            System.out.println(i);
        }
        int indexIncidence = InputAsker.askInt("Which incidence do you want to delete?");
        Incidence incidence = dao.getIncidenceById(String.valueOf(indexIncidence));
        dao.deleteIncidence(incidence);
        System.out.println("Incidence successfully deleted");
    }

    public static void showInc() {
        ArrayList<Incidence> incidences = dao.getAllDocumentsIncidence();
        System.out.println("-- INCIDENCES --");
        for (Incidence i : incidences) {
            System.out.println(i);
        }
    }

    public static void modifyInc(Incidence i) {

    }

    public static void showMenu() {
        System.out.println("== Main Menu ==\n"
                + "1. Create Incident\n"
                + "2. Delete Incident\n"
                + "3. Show Incidents\n"
                + "4. Modify Incident\n"
                + "5. Manage Employees\n"
                + "0. Exit");
    }

    public static void showMenuEmoloyee() {
        System.out.println("== Employee Main Menu ==\n"
                + "1. Create Employee\n"
                + "2. Delete Employee\n"
                + "3. Show Employee\n"
                + "4. Modify Employee\n"
                + "0. Exit");
    }

    public static void LogIn() throws MyException {
        String username = InputAsker.askString("Introduce your username:");
        String pass = InputAsker.askString("Introduce your password:");
        if (dao.checkLogIn(username, pass)) {
            user = dao.getEmployeeByUsername(username);
            showMenu();
        } else {
            System.out.println("You introduced wrong credentials");
            LogIn();
        }

    }

    public static void setUpDB() throws MyException {
        //creamos la base de datos si no esta conectada
        dao.createDatabase();
        //creamos las tablas de la base de datos
        dao.createColletion("employee");
        dao.createColletion("incidence");
        dao.createColletion("record");

        //Employee empleado = new Employee("pepe", "12345", "Pepito", "Jull", "5645454545");
        //dao.insertEmpleado(empleado);
    }

    public static void manageEmployees() {
        try {

            int op;
            do {

                showMenuEmoloyee();
                op = InputAsker.askInt("Select an Option: ");
                switch (op) {
                    case 1:
                        createEmployee();
                        break;
                    case 2:
                        deleteEmployee();
                        break;
                    case 3:
                        showEmployee();
                        break;
                    case 4:
                        updateEmployee();
                        break;
                    case 0:
                        System.out.println("Closing...");
                        break;
                    default:
                        System.out.println("-- Wrong arguments --");
                }
            } while (op != 0);
        } catch (MyException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void createEmployee() throws MyException {
        String userActual = InputAsker.askString("Introduce the username you want: ");
        String pass = InputAsker.askString("Introduce the password you want: ");
        String firstName = InputAsker.askString("Introduce your firstName: ");
        String lastName = InputAsker.askString("Introduce your lastName: ");
        String phone = InputAsker.askString("Introduce your phone number: ");
        Employee e = new Employee(userActual, pass, firstName, lastName, phone);
        dao.insertEmployee(e);
        System.out.println("User was created successfully");
    }

    public static void deleteEmployee() throws MyException {
        ArrayList<Employee> employees = dao.getAllDocumentsEmployee();
        ArrayList<String> usernames = new ArrayList<String>();
        System.out.println("-- EMPLOYEES --");
        for (Employee e : employees) {
            usernames.add(e.getUsername());

        }
        String userBorrar = InputAsker.askString("Which employee do you want to delete?", usernames);
        Employee e = dao.getEmployeeByUsername(userBorrar);
        dao.deleteEmployee(e);
        System.out.println("Employee successfully deleted");
    }

    public static void updateEmployee() throws MyException {

    }

    public static void showEmployee() throws MyException {
        ArrayList<Employee> employees = dao.getAllDocumentsEmployee();
        System.out.println("-- EMPLOYEES --");
        for (Employee e : employees) {
            System.out.println(e.toString());
        }
    }
}
