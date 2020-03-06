package Request;

/**
 *
 */
public class Person extends Requests {
    String personID;
    String authentication;
    public Person(String personID, String authToken) {
        this.personID = personID;
        this.authentication = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public Person(String authToken) {
        this.personID = null;
        this.authentication = authToken;
    }
}
