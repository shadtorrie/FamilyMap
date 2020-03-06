package ModelsServer;

public class Event extends Model {
    private String eventID;
    private String personID;
    private String associatedUsername;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    /**
     *
     * @param eventID
     * @param person
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param event_type
     * @param year
     */
    public Event(String eventID, String person,String userName, float latitude, float longitude, String country, String city, String event_type, int year) {
        this.eventID = eventID;
        this.personID = person;
        this.associatedUsername = userName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = event_type;
        this.year = year;
    }

    /**
     *
     * @param eventID
     */
    public Event(String eventID) {
        this.eventID = eventID;
    }

    /**
     *
     * @return
     */
    public String getPersonID() {
        return personID;
    }

    /**
     *
     * @param personID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     *
     * @return
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     */
    public String getEventType() {
        return eventType;
    }

    /**
     *
     * @param eventType
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     *
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     *
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String getID() {
        return eventID;
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
        Event objEvent = (Event)obj;
        if(!objEvent.personID.equals(this.personID) || !objEvent.eventID.equals(this.eventID) || objEvent.latitude!=this.latitude
                || objEvent.longitude!=this.longitude|| !objEvent.country.equals(this.country)|| !objEvent.city.equals(this.city)
                || !objEvent.eventType.equals(this.eventType)|| objEvent.year!=this.year){
            return false;
        }

        return true;
    }
}
