package NonSqlProject.view;

import NonSqlProject.DAO.DAO;
import NonSqlProject.exception.MyException;
import NonSqlProject.model.Employee;
import NonSqlProject.model.Incidence;
import com.arangodb.ArangoDB;
import NonSqlProject.model.Record;
import com.arangodb.ArangoCollection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class view {

    static String usernameLogeado = "";
    private static DAO dao = new DAO();

    public static void main(String[] args) {
        try {
            final ArangoDB arangoDB = new ArangoDB.Builder()
                    .user("root")
                    .password("admin")
                    .build();
            dao.getAllDocumentsEmployee();
            int op = 0;
            ArangoCollection collection = arangoDB.db("mydb").collection("employee");
            System.out.println(collection.getIndexes().size());
            do {
                LogIn();
                op = InputAsker.askInt("Select an Option: ");
                switch (op) {
                    case 1:
                        createInc();
                        break;
                    case 2:

                        break;
                    case 3:
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

    public static void createInc() {
        Incidence i = new Incidence();
        i.setDateTime(LocalDateTime.now());
        Employee e = dao.getEmployeeByUsername(usernameLogeado);
        i.setOrigin(e);
        String destinatario = InputAsker.askString("Introduce el destinatario: ");
        Employee ed = dao.getEmployeeByUsername(destinatario);
        String details = InputAsker.askString("Introduce los detalles de la incidencia: ");

    }

    public static void deleteInc(Incidence i) {

    }

    public static void showInc(Incidence i) {

    }

    public static void modifyInc(Incidence i) {

    }

    public static void showMenu() {
        System.out.println("== Main Menu ==\n"
                + "1. Create Incident\n"
                + "2. Delete Incident\n"
                + "3. Show Incidents\n"
                + "4. Modify Incident\n"
                + "0. Exit");
    }

    public static void LogIn() throws MyException {
        String username = InputAsker.askString("Introduce your username:");
        String pass = InputAsker.askString("Introduce your password:");
        if (dao.checkLogIn(username, pass)) {
            usernameLogeado = username;
            showMenu();
        } else {
            System.out.println("You introduced the login params wrong.");
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
}
