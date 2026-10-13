package persistence;

/*Vores egen exception. Mapperne fanger SQLException og kaster denne i stedet,
så resten af programmet ikke behøver kende til SQL.*/
public class DatabaseException  extends Exception{
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
