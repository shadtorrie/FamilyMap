package Request;

public class LoginRequest extends Requests {
    private String userName;
    private String password;

    /**
     *
     * @param username
     * @param password
     */
    public LoginRequest(String username, String password) {
        this.userName = username;
        this.password = password;
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
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
