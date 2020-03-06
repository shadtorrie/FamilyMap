package Result;

import java.util.ArrayList;

public class PersonList extends Results {
    ArrayList<Person> data;
    boolean success;

    public PersonList(ArrayList<Person> people,boolean success) {
        this.data = people;
        this.success = success;
    }

    public ArrayList<Person> getData() {
        return data;
    }
    public boolean isSuccess(){
        return success;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }
}
