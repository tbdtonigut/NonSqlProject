package NonSqlProject.exception;

public class MyException extends Exception {
    //Declaro las final y el codigo de error
    private int errorCode;
    public static final int databaseNotCreated = 1;
    public static final int collectionNotCreated = 2;
    public static final int documentoNotCreated = 3;
    
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
            default:
                message = "Error";
                break;
        }

        return message;

    }
}
