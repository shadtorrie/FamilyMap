package Result;

public class Person extends Results {
    private String username;
    private String person_id;
    private String firstName;
    private String lastName;
    private char gender;
    private String father_id;
    private String mother_id;
    private String spouse_id;
    private boolean success;

    /**
     * constructor without unnecessary feilds
     * @param username
     * @param person_id
     * @param firstName
     * @param lastName
     * @param gender
     * @param success
     */
    public Person(String username, String person_id, String firstName, String lastName, char gender, boolean success) {
        this.username = username;
        this.person_id = person_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.success = success;
    }

    /**
     * get username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * set username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get person id
     * @return
     */
    public String getPerson_id() {
        return person_id;
    }

    /**
     * set person ID
     * @param person_id
     */
    public void setPerson_id(String person_id) {
        this.person_id = person_id;
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
    public char getGender() {
        return gender;
    }

    /**
     * set gender
     * @param gender
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * get father id
     * @return
     */
    public String getFather_id() {
        return father_id;
    }

    /**
     * set father id
     * @param father_id
     */
    public void setFather_id(String father_id) {
        this.father_id = father_id;
    }

    /**
     * get mother id
     * @return
     */
    public String getMother_id() {
        return mother_id;
    }

    /**
     * set mother id
     * @param mother_id
     */
    public void setMother_id(String mother_id) {
        this.mother_id = mother_id;
    }

    /**
     * get spouse id
     * @return
     */
    public String getSpouse_id() {
        return spouse_id;
    }

    /**
     * set spouse id
     * @param spouse_id
     */
    public void setSpouse_id(String spouse_id) {
        this.spouse_id = spouse_id;
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
    public Person(String username, String person_id, String firstName, String lastName, char gender, String father_id, String mother_id, String spouse_id, boolean success) {
        this.username = username;
        this.person_id = person_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father_id = father_id;
        this.mother_id = mother_id;
        this.spouse_id = spouse_id;
        this.success = success;
    }
}
