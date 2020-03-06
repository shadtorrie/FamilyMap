package Request;

public class Event extends Requests {
    String eventID;
    String Authentication;

    public Event(String eventID, String authentication) {
        this.eventID = eventID;
        Authentication = authentication;
    }
    public Event(String authentication){
        Authentication=authentication;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAuthentication() {
        return Authentication;
    }

    public void setAuthentication(String authentication) {
        Authentication = authentication;
    }
}
