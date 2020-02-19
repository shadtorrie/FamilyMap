package Result;

public class Register {
    private String authToken;
    private String username;
    private String person_id;
    private boolean success;

    public Register(String message, boolean success) {
        this.authToken = message;
        this.success = success;
    }

    public Register(String authToken, String username, String person_id, boolean success) {
        this.authToken = authToken;
        this.username = username;
        this.person_id = person_id;
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
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
