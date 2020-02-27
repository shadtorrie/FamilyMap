package Models;

public class Person extends Model {
    private String username;
    private String first_name;
    private String last_name;
    private char gender;
    private String father_id;
    private String mother_id;
    private String spouse_id;

    /**
     *
     * @param ID
     */
    public Person(String ID) {
        super(ID);
    }

    /**
     *
     * @param ID
     * @param username
     * @param first_name
     * @param last_name
     * @param gender
     */
    public Person(String ID, String username, String first_name, String last_name, char gender) {
        super(ID);
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
    }

    /**
     *
     * @param ID
     * @param username
     * @param first_name
     * @param last_name
     * @param gender
     * @param father_id
     * @param mother_id
     * @param spouse_id
     */
    public Person(String ID, String username, String first_name, String last_name, char gender, String father_id, String mother_id, String spouse_id) {
        super(ID);
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.father_id = father_id;
        this.mother_id = mother_id;
        this.spouse_id = spouse_id;
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
     * @return
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     *
     * @param first_name
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     *
     * @return
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     *
     * @param last_name
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     *
     * @return
     */
    public char getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     */
    public String getFather_id() {
        return father_id;
    }

    /**
     *
     * @param father_id
     */
    public void setFather_id(String father_id) {
        this.father_id = father_id;
    }

    /**
     *
     * @return
     */
    public String getMother_id() {
        return mother_id;
    }

    /**
     *
     * @param mother_id
     */
    public void setMother_id(String mother_id) {
        this.mother_id = mother_id;
    }

    /**
     *
     * @return
     */
    public String getSpouse_id() {
        return spouse_id;
    }

    /**
     *
     * @param spouse_id
     */
    public void setSpouse_id(String spouse_id) {
        this.spouse_id = spouse_id;
    }

    /**
     *
     * @param obj
     * @return
     */
    /*
    private String username;
    private String first_name;
    private String last_name;
    private char gender;
    private String father_id;
    private String mother_id;
    private String spouse_id;
     */
    @Override
    public boolean equals(Object obj) {
        if(obj.getClass()!=this.getClass()){
            return false;
        }
        else if(obj==this){
            return true;
        }
        Person objPerson = (Person)obj;
        if(!objPerson.username.equals(this.username) || !objPerson.ID.equals(this.ID)
                ||!objPerson.first_name.equals(this.first_name)|| !objPerson.last_name.equals(this.last_name)
                || objPerson.gender!=this.gender || !objPerson.father_id.equals(this.father_id)
                ||!objPerson.mother_id.equals(this.mother_id) ||!objPerson.spouse_id.equals(this.spouse_id)){
            return false;
        }

        return true;
    }
}
