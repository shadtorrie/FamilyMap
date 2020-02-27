package Models;

public class Event extends Model {
    private String person;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String event_type;
    private int year;

    /**
     *
     * @param ID
     * @param person
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param event_type
     * @param year
     */
    public Event(String ID, String person, float latitude, float longitude, String country, String city, String event_type, int year) {
        super(ID);
        this.person = person;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.event_type = event_type;
        this.year = year;
    }

    /**
     *
     * @param ID
     */
    public Event(String ID) {
        super(ID);
    }

    /**
     *
     * @return
     */
    public String getPerson() {
        return person;
    }

    /**
     *
     * @param person
     */
    public void setPerson(String person) {
        this.person = person;
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
    public String getEvent_type() {
        return event_type;
    }

    /**
     *
     * @param event_type
     */
    public void setEvent_type(String event_type) {
        this.event_type = event_type;
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
        if(!objEvent.person.equals(this.person) || !objEvent.ID.equals(this.ID) || objEvent.latitude!=this.latitude
                || objEvent.longitude!=this.longitude|| !objEvent.country.equals(this.country)|| !objEvent.city.equals(this.city)
                || !objEvent.event_type.equals(this.event_type)|| objEvent.year!=this.year){
            return false;
        }

        return true;
    }
}
