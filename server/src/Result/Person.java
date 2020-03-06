package Result;

public class Person extends Results {
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;
    private boolean success;
    private String message;

    public Person(String username, String person_id, String firstName, String lastName, String gender, boolean success) {
        this.associatedUsername = username;
        this.personID = person_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.success = success;

    }

    public Person(String message, boolean success) {
        this.message =  message;
        this.success = success;
    }
    public String getMessage() {
        return message;
    }

    /**
     * get username
     * @return
     */
    public String getAssociatedUsername() {
        return associatedUsername;
    }

    /**
     * set username
     * @param associatedUsername
     */
    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    /**
     * get person id
     * @return
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * set person ID
     * @param personID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * get first Name
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * set first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * get last name
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * set last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * get gender
     * @return
     */
    public String getGender() {
        return gender;
    }

    /**
     * set gender
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * get father id
     * @return
     */
    public String getFatherID() {
        return fatherID;
    }

    /**
     * set father id
     * @param fatherID
     */
    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    /**
     * get mother id
     * @return
     */
    public String getMotherID() {
        return motherID;
    }

    /**
     * set mother id
     * @param motherID
     */
    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    /**
     * get spouse id
     * @return
     */
    public String getSpouseID() {
        return spouseID;
    }

    /**
     * set spouse id
     * @param spouseID
     */
    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    /**
     * is success
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * set success
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * constructor with unnsesseary feilds.
     * @param username
     * @param person_id
     * @param firstName
     * @param lastName
     * @param gender
     * @param father_id
     * @param mother_id
     * @param spouse_id
     * @param success
     */
    public Person(String username, String person_id, String firstName, String lastName, String gender, String father_id, String mother_id, String spouse_id, boolean success) {
        this.associatedUsername = username;
        this.personID = person_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = father_id;
        this.motherID = mother_id;
        this.spouseID = spouse_id;
        this.success = success;
    }
}
