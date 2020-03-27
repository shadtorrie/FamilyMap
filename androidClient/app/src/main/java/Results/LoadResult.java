package Results;

public class LoadResult extends Results {
    private boolean success;
    private String message;

    /**
     * load constructor
     * @param message
     * @param success
     */
    public LoadResult(String message, boolean success) {
        this.message =  message;
        this.success = success;
    }

    /**
     * get Message
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * set message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * get success
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * set success
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
