package Result;

public class Load extends Results {
    private String message;
    private boolean success;

    /**
     * load constructor
     * @param message
     * @param success
     */
    public Load(String message, boolean success) {
        this.message = message;
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
