package Services;

import Models.Event;
import Models.Person;
import Models.User;
import Request.Requests;
import Result.Results;

import java.util.ArrayList;

public class LoadS extends Service{
    private ArrayList<User> users;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    public LoadS(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    /**
     *Load service constructor.
     */
    public LoadS() {
        super();
    }


    /**
     * Requests the Load Service
     * @param request
     * @return
     */
    @Override
    public Results requestService(Requests request) {
        return null;
    }

}
