package Result;

public class Event extends Results {
    private String username;
    private String event_id;
    private String person_id;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;
    private boolean success;

    /**
     *
     * @param message
     * @param success
     */
    public Event(String message, boolean success) {
        this.username = message;
        this.success = success;
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
    public String getEvent_id() {
        return event_id;
    }

    /**
     *
     * @param event_id
     */
    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    /**
     *
     * @return
     */
    public String getPerson_id() {
        return person_id;
    }

    /**
     *
     * @param person_id
     */
    public void setPerson_id(String person_id) {
        this.person_id = person_id;
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

    /**
     *
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     *
     * @param username
     * @param event_id
     * @param person_id
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     * @param success
     */
    public Event(String username, String event_id, String person_id, float latitude, float longitude, String country, String city, String eventType, int year, boolean success) {
        this.username = username;
        this.event_id = event_id;
        this.person_id = person_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
    }
}
