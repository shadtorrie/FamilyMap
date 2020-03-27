package Models;

public class PersonModel extends Model {
    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    /**
     *
     * @param
     */
    public PersonModel(String personID) {
        this.personID= personID;
    }

    /**
     *
     * @param
     * @param username
     * @param first_name
     * @param last_name
     * @param gender
     */
    public PersonModel(String personID, String username, String first_name, String last_name, String gender) {
        this.personID= personID;
        this.associatedUsername = username;
        this.firstName = first_name;
        this.lastName = last_name;
        this.gender = gender;
    }

    /**
     *
     * @param
     * @param username
     * @param first_name
     * @param last_name
     * @param gender
     * @param father_id
     * @param mother_id
     * @param spouse_id
     */
    public PersonModel(String personID, String username, String first_name, String last_name, String gender, String father_id, String mother_id, String spouse_id) {
        this.personID= personID;
        this.associatedUsername = username;
        this.firstName = first_name;
        this.lastName = last_name;
        this.gender = gender;
        this.fatherID = father_id;
        this.motherID = mother_id;
        this.spouseID = spouse_id;
    }

    /**
     *
     * @return
     */
    public String getAssociatedUsername() {
        return associatedUsername;
    }

    /**
     *
     * @param associatedUsername
     */
    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    /**
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     */
    public String getFatherID() {
        return fatherID;
    }

    /**
     *
     * @param fatherID
     */
    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    /**
     *
     * @return
     */
    public String getMotherID() {
        return motherID;
    }

    /**
     *
     * @param motherID
     */
    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    /**
     *
     * @return
     */
    public String getSpouseID() {
        return spouseID;
    }

    /**
     *
     * @param spouseID
     */
    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
    @Override
    public String getID(){
        return personID;
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
        PersonModel objPerson = (PersonModel)obj;
        if(!objPerson.associatedUsername.equals(this.associatedUsername) || !objPerson.personID.equals(this.personID)
                ||!objPerson.firstName.equals(this.firstName)|| !objPerson.lastName.equals(this.lastName)
                || !objPerson.gender.equals(this.gender)){
            return false;
        }
        if(this.fatherID !=null){
            if(objPerson.fatherID !=null){
                if(!objPerson.fatherID.equals(this.fatherID)){
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else if(objPerson.fatherID !=null){
            return false;
        }
        if(this.motherID !=null){
            if(objPerson.motherID !=null){
                if(!objPerson.motherID.equals(this.motherID)){
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else if(objPerson.motherID !=null){
            return false;
        }
        if(this.spouseID !=null){
            if(objPerson.spouseID !=null){
                if(!objPerson.spouseID.equals(this.spouseID)){
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else if(objPerson.spouseID !=null){
            return false;
        }

        return true;
    }
}
