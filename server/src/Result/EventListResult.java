package Result;

import java.util.ArrayList;

public class EventListResult extends Results {
    ArrayList<EventResult> data;
    boolean success;

    public EventListResult(ArrayList<EventResult> events, boolean success) {
        this.data = events;
        this.success = success;
    }

    public ArrayList<EventResult> getData() {
        return data;
    }

    public void setData(ArrayList<EventResult> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }
}
