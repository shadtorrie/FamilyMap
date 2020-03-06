package Results;

public class ClearResult extends Results {
    private boolean success;
    private String message;

    public ClearResult(String message, boolean success) {
        this.message =  message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
