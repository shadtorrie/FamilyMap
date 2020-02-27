package Models;

/**
 *
 */
public class Auth extends Model {
    private String username;

    /**
     *
     * @param authToken
     * @param username
     */
    public Auth(String authToken, String username) {
        super(authToken);
        this.username = username;
    }

    /**
     *
     * @param ID
     */
    public Auth(String ID) {
        super(ID);
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
        Auth objAuth = (Auth)obj;
        if(!objAuth.ID.equals(this.ID)||!objAuth.username.equals(this.username)){
            return false;
        }
        return true;
    }
}
