package Models;

/**
 *
 */
public class AuthModel extends Model {
    private String authToken;
    private String userName;

    /**
     *
     * @param authToken
     * @param username
     */
    public AuthModel(String authToken, String username) {
        this.authToken=authToken;
        this.userName = username;
    }

    /**
     *
     * @param authToken
     */
    public AuthModel(String authToken) {
        this.authToken= authToken;
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

    @Override
    public String getID() {
        return authToken;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj.getClass()!=this.getClass()){
            return false;
        }
        else if(obj==this){
            return true;
        }
        AuthModel objAuth = (AuthModel)obj;
        if(!objAuth.authToken.equals(this.authToken)||!objAuth.userName.equals(this.userName)){
            return false;
        }
        return true;
    }
}
