package Data.Exceptions;

public class RequestFailedException extends Exception {
    public RequestFailedException(String message){
        super(message);
    }
}
