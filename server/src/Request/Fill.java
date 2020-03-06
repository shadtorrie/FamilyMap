package Request;

public class Fill extends Requests {
    String userName;
    int genCount;
    char gender = 0;
    String firstName = null;
    String lastName = null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
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

    public Fill(String username, int genCount, char gender, String firstName, String lastName) {
        this.userName = username;
        this.genCount = genCount;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Fill(String username, int genCount) {
        this.userName = username;
        this.genCount = genCount;
    }
}
