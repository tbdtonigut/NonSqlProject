package NonSqlProject.view;

import NonSqlProject.DAO.DAO;
import NonSqlProject.exception.MyException;
import NonSqlProject.model.Incidence;
import java.util.logging.Level;
import java.util.logging.Logger;

public class view {
    private static DAO dao = new DAO();
    public static void main(String[] args) {
        // TODO code application logic here
        int op = 0;
        try {
            dao.createDatabase();
        } catch (MyException ex) {
            System.out.println(ex.getMessage());
        }
        do {
            showMenu();
            op = InputAsker.askInt("Select an Option: ");
            switch (op) {
                case 1:
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
    }

    public static void createInc(Incidence i){
        i = new Incidence();

    }

    public static void deleteInc(Incidence i){

    }

    public static void showInc(Incidence i){

    }

    public static void modifyInc(Incidence i){

    }

    public static void showMenu() {
        System.out.println("== Main Menu ==\n" +
                "1. Create Incident\n" +
                "2. Delete Incident\n" +
                "3. Show Incidents\n" +
                "4. Modify Incident\n" +
                "0. Exit");
    }

}
