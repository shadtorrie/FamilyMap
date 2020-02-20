package Models;

public class User extends Model {
    private String password;
    private String email;

    /**
     *
     * @param username
     * @param password
     * @param email
     */
    public User(String username, String password, String email) {
        super(username);
        this.password = password;
        this.email = email;
    }

    /**
     *
     * @param ID
     */
    public User(String ID) {
        super(ID);
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

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
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
        User objUsers = (User)obj;
        if(!objUsers.email.equals(this.email) || !objUsers.ID.equals(this.ID) || !objUsers.password.equals(this.password)){
            return false;
        }

        return true;
    }
}
