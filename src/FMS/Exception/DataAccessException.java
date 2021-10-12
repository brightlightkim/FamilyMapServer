package Exception;

public class DataAccessException extends Exception {
    public DataAccessException(String message)
    {
        super(message);
    }

    public DataAccessException()
    {
        super();
    }
}
