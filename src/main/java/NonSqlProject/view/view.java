package NonSqlProject.view;

import NonSqlProject.DAO.DAO;
import NonSqlProject.exception.MyException;
import NonSqlProject.model.Employee;
import NonSqlProject.model.Enum.EventType;
import NonSqlProject.model.Enum.Type;
import NonSqlProject.model.Incidence;
import NonSqlProject.model.Record;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class view {

    private static Employee user;
    private static DAO dao = new DAO();

    public static void main(String[] args) {
        try {
            int op;
            setUpDB();
            LogIn();
            do {
                showMenu();
                op = InputAsker.askInt("Select an Option: ");
                switch (op) {
                    case 1:
                        createInc();
                        break;
                    case 2:
                        deleteInc();
                        break;
                    case 3:
                        showIncidences();
                        break;
                    case 4:
                        modifyInc();
                        break;
                    case 5:
                        manageEmployees();
                        break;
                    case 6:
                        showRecords();
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

    public static void setUpDB() throws MyException {
        //creamos la base de datos si no esta conectada
        dao.createDatabase();
        //creamos las tablas de la base de datos
        dao.createColletion("employee");
        dao.createColletion("incidence");
        dao.createColletion("record");

        Employee employee = new Employee("admin", "admin", "admin", "admin", "66666666");
        dao.insertEmployee(employee);
    }

    public static void LogIn() throws MyException {
        String username = InputAsker.askString("Introduce your username:");
        String pass = InputAsker.askString("Introduce your password:");
        if (dao.checkLogIn(username, pass)) {
            user = dao.getEmployeeByUsername(username);
            LocalDateTime localDateTime = LocalDateTime.now();
            Record record = new Record(localDateTime, user, EventType.I);
            dao.insertRecord(record);
        } else {
            System.out.println("You introduced wrong credentials");
            LogIn();
        }

    }

    //METODOS DE INCIDENCES

    public static void createInc() throws MyException {
        Type type;
        ArrayList<Employee> employees = showEmployees();

        int recipientIndex = InputAsker.askInt("Select the recipient: ", 1, employees.size());
        Employee recipient = employees.get(recipientIndex - 1);
        String details = InputAsker.askString("Introduce incidence details:");
        System.out.println(" 1. Normal\n" + "2. Urgent");
        int typeIndex = InputAsker.askInt("Select a incidence type: ", 1, 2);
        if (typeIndex == 1) {
            type = Type.NORMAL;
        } else {
            type = Type.URGENT;
            LocalDateTime localDateTime = LocalDateTime.now();
            Record record = new Record(localDateTime, user, EventType.U);
            dao.insertRecord(record);
        }
        Incidence incidence = new Incidence(LocalDateTime.now(), user, recipient, details, type);
        dao.insertIncidence(incidence);
        System.out.println("Incidence successfully created");
    }

    public static boolean deleteInc() throws MyException {
        ArrayList<Incidence> incidences = showIncidences();
        if (!incidences.isEmpty()) {
            int indexIncidence = InputAsker.askInt("Which incidence do you want to delete?");
            for (Incidence i : incidences) {
                if (indexIncidence == i.getId()) {
                    Incidence incidence = dao.getIncidenceById(String.valueOf(indexIncidence));
                    dao.deleteIncidence(incidence);
                    System.out.println("Incidence successfully deleted");
                    return true;
                }
            }
        } else {
            System.out.println("We don't have incidences yet");
        }
        return false;
    }

    public static ArrayList<Incidence> showIncidences() {
        ArrayList<Incidence> incidences = dao.getAllDocumentsIncidence();
        System.out.println("-- INCIDENCES --");
        for (Incidence i : incidences) {
            System.out.println(i);
        }
        return incidences;
    }

    public static void modifyInc() throws MyException {
        showIncidences();
        int indexIncidence = InputAsker.askInt("Which incidence do you want to update?");
        Incidence incidence = dao.getIncidenceById(String.valueOf(indexIncidence));
        System.out.println("== Properties == \n"
                + "1. Details\n"
                + "2. Type");

        int indexProperty = InputAsker.askInt("Which attribute do you want to update?");

        switch (indexProperty) {
            case 1:
                String details = InputAsker.askString("Introduce the new details:");
                incidence.setDetails(details);
                break;
            case 2:
                Type type;
                System.out.println(" 1. Normal\n" + "2. Urgent");
                int typeIndex = InputAsker.askInt("Select a incidence type: ", 1, 2);
                if (typeIndex == 1) {
                    type = Type.NORMAL;
                } else {
                    type = Type.URGENT;
                }
                incidence.setType(type);
                break;
        }
        System.out.println(incidence);
        dao.updateIncidence(incidence);

    }

    //MENU DE EMPLOYEES

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

    //METODOS DE EMPLOYEES

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
        ArrayList<Employee> employees = showEmployees();
        int indexEmployee = InputAsker.askInt("Which employee do you want to update?", 1, employees.size());
        Employee employee = dao.getEmployeeByUsername(employees.get(indexEmployee).getUsername());
        System.out.println("== Properties == \n"
                + "1. First Name\n"
                + "2. Last Name\n"
                + "3. Phone \n"
                + "4. Pass");
        int indexProperty = InputAsker.askInt("Which attribute do you want to update?");

        switch (indexProperty) {
            case 1:
                String firstName = InputAsker.askString("Introduce the new first name:");
                employee.setFirstName(firstName);
                break;
            case 2:
                String lastName = InputAsker.askString("Introduce the new first name:");
                employee.setLastName(lastName);
                break;
            case 3:
                String phone = InputAsker.askString("Introduce your new phone:");
                employee.setPhone(phone);
            case 4:
                String pass = InputAsker.askString("Introduce your new password: ");
                employee.setPass(pass);
        }
        dao.updateEmployee(employee);
    }

    public static void showEmployee() throws MyException {
        ArrayList<Employee> employees = dao.getAllDocumentsEmployee();
        System.out.println("-- EMPLOYEES --");
        for (Employee e : employees) {
            System.out.println(e.toString());
        }
    }

    public static ArrayList<Employee> showEmployees() {
        ArrayList<Employee> employees = dao.getAllDocumentsEmployee();
        int index = 1;
        System.out.println("-- EMPLOYEES --");
        for (Employee e : employees) {
            System.out.println(index + ". " + e.getUsername());
            index++;
        }
        return employees;
    }

    //SOUTS

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

    private static void showRecords() {
        ArrayList<Record> records = dao.getAllDocumentsRecord();
        System.out.println("-- RECORDS --");
        for (Record r : records) {
            System.out.println("id:" + r.getId()
                    + "\n Date:" + r.getDateTime()
                    + "\n Employee:" + r.getEmployee().getUsername()
                    + "\n Type:" + r.getEventType());
        }
    }
}
