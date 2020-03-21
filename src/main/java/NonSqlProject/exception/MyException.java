package NonSqlProject.exception;

public class MyException extends Exception {
    //Declaro las final y el codigo de error
    private int errorCode;
    public static final int wrongLength = 1;
    public static final int roomAlreadyExists = 2;
    public static final int wrongDataType = 3;
    public static final int wrongService = 4;
    public static final int workerAlreadyExists = 5;
    public static final int invalidDni = 6;
    public static final int wrongSkill = 7;
    public static final int roomNotFound = 8;
    public static final int wrongNumberPersons = 9;
    public static final int noRoomAvailible = 10;
    public static final int requestAlreadyDone = 11;

    public MyException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    //Hacemos override del get message para devolver nuestros mensajes segun la final que nos llegue
    @Override
    public String getMessage() {

        String message = "";

        switch (errorCode) {
            default:
                message = "Error";
                break;
        }

        return message;

    }
}
