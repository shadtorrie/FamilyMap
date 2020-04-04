package Data;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Models.AuthModel;
import Models.EventModel;
import Models.PersonModel;

public class ModelData {
    private static ModelData instance;
    private PersonModel firstPerson;
    private AuthModel authentication=null;
    private HashMap<String,PersonModel> people;
    private HashMap<String,EventModel> events;
    private static ArrayList<Float> colors;
    private HashMap<String, Float> colorByEventType;

    public static HashMap<String,PersonModel> getPeople() {
        return instance.people;
    }

    public static void insertPerson(PersonModel person) {
        instance.people.put(person.getID(),person);
    }
    public static PersonModel getPerson(String key){
        return instance.people.get(key);
    }
    public static HashMap<String,EventModel> getEvents() {
        return instance.events;
    }

    public static void insertEvent(EventModel event) {
        instance.events.put(event.getID(),event);
    }
    public static EventModel getEvent(String key){
        return instance.events.get(key);
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
    public static boolean loggedIn(){
        return instance.authentication!=null;
    }

    static{
        initializer();
    }

    public ModelData() {
        people = new HashMap<>();
        events = new HashMap<>();
    }

    private static void initializer() {
        instance = new ModelData();
        instance.colorByEventType = new HashMap<>();
        colors = new ArrayList<>();
        colors.add(BitmapDescriptorFactory.HUE_AZURE);
        colors.add(BitmapDescriptorFactory.HUE_BLUE);
        colors.add(BitmapDescriptorFactory.HUE_ORANGE);
        colors.add(BitmapDescriptorFactory.HUE_ROSE);
        colors.add(BitmapDescriptorFactory.HUE_CYAN);
        colors.add(BitmapDescriptorFactory.HUE_RED);
        colors.add(BitmapDescriptorFactory.HUE_MAGENTA);
        colors.add(BitmapDescriptorFactory.HUE_GREEN);
        colors.add(BitmapDescriptorFactory.HUE_VIOLET);
        colors.add(BitmapDescriptorFactory.HUE_YELLOW);
    }

    public static float getIconColor(String eventType) {
        if(instance.colorByEventType.containsKey(eventType)){
            return instance.colorByEventType.get(eventType);
        }
        else{
            instance.colorByEventType.put(eventType,colors.get((instance.colorByEventType.size()+1)%colors.size()));
            return instance.colorByEventType.get(eventType);
        }
    }
}
