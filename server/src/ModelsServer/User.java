package ModelsServer;

public class User extends Model {
    private String password;
    private String email;
    private String userName;

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    private String personID;

    public User(String userName,String password, String email,  String personID) {
        this.password = password;
        this.email = email;
        this.userName = userName;
        this.personID = personID;
    }

    @Override
    public String getID(){
        return userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @param username
     * @param password
     * @param email
     */
    public User(String username, String password, String email) {
        this.userName = username;
        this.password = password;
        this.email = email;
    }

    /**
     *
     * @param
     */
    public User(String username) {
        this.userName = username;
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
        if(!objUsers.email.equals(this.email) || !objUsers.userName.equals(this.userName) || !objUsers.password.equals(this.password)){
            return false;
        }

        return true;
    }
}
