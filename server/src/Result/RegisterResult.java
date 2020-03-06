package Result;

public class RegisterResult extends Results{
    private String authToken;
    private String userName;
    private String personID;
    private boolean success;
    private String message;

    public RegisterResult(String message, boolean success) {
        this.message =  message;
        this.success = success;
    }

    public RegisterResult(String authToken, String username, String person_id, boolean success) {
        this.authToken = authToken;
        this.userName = username;
        this.personID = person_id;
        this.success = success;
    }
    public String getMessage(){
        return message;
    }
    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
