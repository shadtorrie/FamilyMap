package Request;

import Models.Event;
import Models.Person;
import Models.User;

import java.util.ArrayList;

public class Load extends Requests {
    private ArrayList<User> users;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    /**
     * default constructor
     * @param users
     * @param persons
     * @param events
     */
    public Load(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    /**
     *
     * @return
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Sets users
     * @param users
     */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    /**
     * Gets persons
     * @return
     */
    public ArrayList<Person> getPersons() {
        return persons;
    }

    /**
     * sets persons
     * @param persons
     */
    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    /**
     * gets events
     * @return
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * sets events
     * @param events
     */
    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
