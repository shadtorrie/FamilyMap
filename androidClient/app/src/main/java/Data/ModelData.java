package Data;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
    private boolean lifeLines = true;
    private boolean familyLines = true;
    private boolean spouseLines = true;
    private boolean fathersSide = true;
    private boolean mothersSide = true;
    private boolean maleEvents = true;
    private boolean femaleEvents = true;

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

    public static PersonModel getPersonChild(String id) {
        for(HashMap.Entry<String,PersonModel> i: getPeople().entrySet()) {
            if(i.getValue().getFatherID()!=null&&i.getValue().getFatherID().equals(id) ){
                return i.getValue();
            }
            if(i.getValue().getMotherID()!=null&&i.getValue().getMotherID().equals(id)){
                return i.getValue();
            }
        }
        return null;
    }

    public static String getEventString(String id) {
        EventModel currentEvent = getEvent(id);
        if(currentEvent==null){
            return null;
        }
        return currentEvent.getEventType()+": "+currentEvent.getCity()+", "+currentEvent.getCountry()+" ("+currentEvent.getYear()+")";
    }
    public static String getPersonFullName(String id){
        PersonModel currentPerson = getPerson(id);
        if(currentPerson==null){
            return null;
        }
        return currentPerson.getFirstName()+" "+currentPerson.getLastName();
    }

    public static TreeMap<Integer,String> getPersonsEvents(String id) {
        TreeMap<Integer,String> events = new TreeMap<>();
        for(HashMap.Entry<String,EventModel> i: getEvents().entrySet()) {
            if(i.getValue().getPersonID().equals(id)){
                int year = i.getValue().getYear();
                if(i.getValue().getEventType().toLowerCase().equals("birth")){
                    year=0;
                }
                else if(i.getValue().getEventType().toLowerCase().equals("death")){
                    year=99999999;
                }
                else if(events.containsKey(year)){
                    String previousEvent = events.get(year);
                    EventModel previousEventModel = getEvent(previousEvent);
                    int compare =i.getValue().getEventType().compareToIgnoreCase(previousEventModel.getEventType());
                    if(compare>0){
                        year++;
                    }
                }
                events.put(year,i.getValue().getID());
            }
        }
        return events;
    }

    public static HashMap<String, String> getRelatives(PersonModel person) {
        HashMap<String,String> relatives= new HashMap<>();
        PersonModel father= getPerson(person.getFatherID());
        PersonModel mother = getPerson(person.getMotherID());
        PersonModel spouse = getPerson(person.getSpouseID());
        PersonModel child = getPersonChild(person.getID());
        if(father!=null){
            relatives.put("Father",father.getID());
        }
        if(mother!=null){
            relatives.put("Mother",mother.getID());
        }
        if(spouse!=null){
            relatives.put("Spouse",spouse.getID());
        }
        if(child!=null){
            relatives.put("Child",child.getID());
        }
        return relatives;
    }
}
