package Data;

import java.util.ArrayList;

import Models.AuthModel;
import Models.EventModel;
import Models.PersonModel;

public class ModelData {
    private static ModelData instance;
    private PersonModel firstPerson;
    private AuthModel authentication;
    private ArrayList<PersonModel> people;
    private ArrayList<EventModel> events;

    public static ArrayList<PersonModel> getPeople() {
        return instance.people;
    }

    public static void insertPerson(PersonModel person) {
        instance.people.add(person);
    }
    public static PersonModel getPerson(int index){
        return instance.people.get(index);
    }
    public static ArrayList<EventModel> getEvents() {
        return instance.events;
    }

    public static void insertEvent(EventModel event) {
        instance.events.add(event);
    }
    public static EventModel getEvent(int index){
        return instance.events.get(index);
    }
    public static PersonModel getFirstPerson() {
        return instance.firstPerson;
    }

    public static void setFirstPerson(PersonModel firstPerson) {
        instance.firstPerson = firstPerson;
    }

    public static AuthModel getAuthentication() {
        return instance.authentication;
    }

    public static void setAuthentication(AuthModel authentication) {
        instance.authentication = authentication;
    }

    static{
        initializer();
    }

    public ModelData() {
        people = new ArrayList<>();
        events = new ArrayList<>();
    }

    private static void initializer() {
        instance = new ModelData();
    }
}
