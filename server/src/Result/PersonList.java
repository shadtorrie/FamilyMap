package Result;

import java.util.ArrayList;

public class PersonList extends Results {
    ArrayList<PersonResult> data;
    boolean success;

    public PersonList(ArrayList<PersonResult> people, boolean success) {
        this.data = people;
        this.success = success;
    }

    public ArrayList<PersonResult> getData() {
        return data;
    }
    public boolean isSuccess(){
        return success;
    }

    public void setData(ArrayList<PersonResult> data) {
        this.data = data;
    }
}
