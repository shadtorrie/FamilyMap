package Requests;

public class EventRequest extends Requests {
    String eventID;
    String Authentication;

    public EventRequest(String eventID, String authentication) {
        this.eventID = eventID;
        Authentication = authentication;
    }
    public EventRequest(String authentication){
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
