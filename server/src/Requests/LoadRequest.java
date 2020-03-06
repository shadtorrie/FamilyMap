package Requests;

import Models.EventModel;
import Models.PersonModel;
import Models.UserModel;

import java.util.ArrayList;

public class LoadRequest extends Requests {
    private ArrayList<UserModel> users;
    private ArrayList<PersonModel> persons;
    private ArrayList<EventModel> events;

    /**
     * default constructor
     * @param users
     * @param persons
     * @param events
     */
    public LoadRequest(ArrayList<UserModel> users, ArrayList<PersonModel> persons, ArrayList<EventModel> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    /**
     *
     * @return
     */
    public ArrayList<UserModel> getUsers() {
        return users;
    }

    /**
     * Sets users
     * @param users
     */
    public void setUsers(ArrayList<UserModel> users) {
        this.users = users;
    }

    /**
     * Gets persons
     * @return
     */
    public ArrayList<PersonModel> getPersons() {
        return persons;
    }

    /**
     * sets persons
     * @param persons
     */
    public void setPersons(ArrayList<PersonModel> persons) {
        this.persons = persons;
    }

    /**
     * gets events
     * @return
     */
    public ArrayList<EventModel> getEvents() {
        return events;
    }

    /**
     * sets events
     * @param events
     */
    public void setEvents(ArrayList<EventModel> events) {
        this.events = events;
    }
}
