package NonSqlProject.exception;

public class MyException extends Exception {
    //Declaro las final y el codigo de error
    private int errorCode;
    public static final int databaseNotCreated = 1;
    public static final int collectionNotCreated = 2;
    public static final int documentoNotCreated = 3;
    public static final int wrongUsername = 4;
    public static final int wrongPass = 5;
    public static final int documentDoesntExists = 6;
    public static final int documentHaventBeenUpdated = 7;
    
    public MyException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    //Hacemos override del get message para devolver nuestros mensajes segun la final que nos llegue
    @Override
    public String getMessage() {

        String message = "";

        switch (errorCode) {
            case databaseNotCreated:
                message = "Database didn't created succesfully";
                break;
            case collectionNotCreated:
                message = "Collection didn't created succesfully";
                break;
            case documentoNotCreated:
                message = "Document didn't created succesfully";
                break;
            case wrongUsername:
                message = "This username doesn't exists";
                break;
            case wrongPass:
                message = "You introduced a wrong password";
                break;
            case documentDoesntExists:
                message = "Document doesn't exists";
                break;
            case documentHaventBeenUpdated:
                message = "Document haven't been updated";
                break;
            default:
                message = "Error";
                break;
        }

        return message;

    }
}
