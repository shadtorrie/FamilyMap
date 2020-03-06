package Result;

import java.util.ArrayList;

public class EventList extends Results {
    ArrayList<Event> data;
    boolean success;

    public EventList(ArrayList<Event> events,boolean success) {
        this.data = events;
        this.success = success;
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }
}
