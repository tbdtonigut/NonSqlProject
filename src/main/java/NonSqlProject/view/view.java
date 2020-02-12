package NonSqlProject.view;

import NonSqlProject.model.Incidence;

public class view {

    public static void main(String[] args) {
        // TODO code application logic here
        int op = 0;
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
