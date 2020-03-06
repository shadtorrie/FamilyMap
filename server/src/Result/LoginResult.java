package Result;

public class LoginResult extends Results{
    private String authToken;
    private String userName;
    private String personID;
    private boolean success;
    private String message;

    /**
     *
     * @param message
     * @param success
     */
    public LoginResult(String message, boolean success) {
        this.message =  message;
        this.success = success;
    }


    /**
     *
     * @param authToken
     * @param username
     * @param person_id
     * @param success
     */
    public LoginResult(String authToken, String username, String person_id, boolean success) {
        this.authToken = authToken;
        this.userName = username;
        this.personID = person_id;
        this.success = success;
    }

    /**
     *
     * @return
     */
    public String getAuthToken() {
        return authToken;
    }
    public String getMessage() {
        return message;
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
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     */
    public String getPersonID() {
        return personID;
    }

    /**
     *
     * @param personID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
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
