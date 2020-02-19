package Result;

public class Login extends Results{
    private String authToken;
    private String username;
    private String person_id;
    private boolean success;

    /**
     *
     * @param message
     * @param success
     */
    public Login(String message, boolean success) {
        this.authToken = message;
        this.success = success;
    }

    /**
     *
     * @param authToken
     * @param username
     * @param person_id
     * @param success
     */
    public Login(String authToken, String username, String person_id, boolean success) {
        this.authToken = authToken;
        this.username = username;
        this.person_id = person_id;
        this.success = success;
    }

    /**
     *
     * @return
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     *
     * @param authToken
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getPerson_id() {
        return person_id;
    }

    /**
     *
     * @param person_id
     */
    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    /**
     *
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
