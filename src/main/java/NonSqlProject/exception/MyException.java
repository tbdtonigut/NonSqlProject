package NonSqlProject.exception;

public class MyException extends Exception {
    //Declaro las final y el codigo de error
    private int errorCode;
    public static final int databaseNotCreated = 1;


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
            default:
                message = "Error";
                break;
        }

        return message;

    }
}
