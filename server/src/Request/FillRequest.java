package Request;

public class FillRequest extends Requests {
    String userName;
    int genCount;
    String gender;
    String firstName = null;
    String lastName = null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGenCount() {
        return genCount;
    }

    public void setGenCount(int genCount) {
        this.genCount = genCount;
    }

    public FillRequest(String username, int genCount, String gender, String firstName, String lastName) {
        this.userName = username;
        this.genCount = genCount;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public FillRequest(String username, int genCount) {
        this.userName = username;
        this.genCount = genCount;
    }
}
