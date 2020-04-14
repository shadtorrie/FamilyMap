package Data;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import Models.AuthModel;
import Models.EventModel;
import Models.Model;
import Models.PersonModel;

public class ModelData {
    private static ModelData instance;
    private PersonModel firstPerson;
    private AuthModel authentication=null;
    private HashMap<String,PersonModel> people;
    private HashMap<String,EventModel> events;
    private static ArrayList<Float> colors;
    private static ArrayList<Integer> lineColors;
    private HashMap<String, Float> colorByEventType;
    private boolean lifeLines = true;
    private boolean familyLines = true;
    private boolean spouseLines = true;
    private boolean fathersSide = true;
    private boolean mothersSide = true;
    private boolean maleEvents = true;
    private boolean femaleEvents = true;
    private HashMap<String, Integer> colorByLineType;
    private String currentEvent;


    public static boolean isLifeLines() {
        return instance.lifeLines;
    }

    public static  void setLifeLines(boolean lifeLines) {
        instance.lifeLines = lifeLines;
    }

    public static boolean isFamilyLines() {
        return instance.familyLines;
    }

    public static void setFamilyLines(boolean familyLines) {
        instance.familyLines = familyLines;
    }

    public static boolean isSpouseLines() {
        return instance.spouseLines;
    }

    public static void setSpouseLines(boolean spouseLines) {
        instance.spouseLines = spouseLines;
    }

    public static boolean isFathersSide() {
        return instance.fathersSide;
    }

    public static void setFathersSide(boolean fathersSide) {
        instance.fathersSide = fathersSide;
    }

    public static boolean isMothersSide() {
        return instance.mothersSide;
    }

    public static void setMothersSide(boolean mothersSide) {
        instance.mothersSide = mothersSide;
    }

    public static boolean isMaleEvents() {
        return instance.maleEvents;
    }

    public static void setMaleEvents(boolean maleEvents) {
        instance.maleEvents = maleEvents;
    }

    public static boolean isFemaleEvents() {
        return instance.femaleEvents;
    }

    public static void setFemaleEvents(boolean femaleEvents) {
        instance.femaleEvents = femaleEvents;
    }


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
        HashMap<String,EventModel> returnEvents= new HashMap<>();
        PersonModel firstPerson = getFirstPerson();
        if((isFemaleEvents()&&firstPerson.getGender().equalsIgnoreCase("f"))||(isMaleEvents()&&firstPerson.getGender().equalsIgnoreCase("m"))){
            getPersonsEvents(firstPerson.getID(),returnEvents);
        }
        if(isMothersSide()&&firstPerson.getMotherID()!=null){
            getPersonAndAncestorEvents(firstPerson.getMotherID(),returnEvents);
        }
        if(isFathersSide()&&firstPerson.getFatherID()!=null){
            getPersonAndAncestorEvents(firstPerson.getFatherID(),returnEvents);
        }
        return returnEvents;
    }
    public static void getPersonAndAncestorEvents(String personID,HashMap<String,EventModel> events){
        PersonModel currentPerson = getPerson(personID);
        if((isFemaleEvents()&&currentPerson.getGender().equalsIgnoreCase("f"))||(isMaleEvents()&&currentPerson.getGender().equalsIgnoreCase("m"))){
            getPersonsEvents(currentPerson.getID(),events);
        }
        if(currentPerson.getMotherID()!=null){
            getPersonAndAncestorEvents(currentPerson.getMotherID(),events);
        }
        if(currentPerson.getFatherID()!=null){
            getPersonAndAncestorEvents(currentPerson.getFatherID(),events);
        }
    }
    public static HashMap<String,EventModel> getAllEvents() {
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
        instance.colorByLineType = new HashMap<>();
        lineColors = new ArrayList<>();
        lineColors.add(0xff388E3C);
        lineColors.add(0xff388E3C);
        lineColors.add(0xffF57F17);
        lineColors.add(0xffF92374);
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
    public static void getPersonsEvents(String id,HashMap<String,EventModel> events) {
        for(HashMap.Entry<String,EventModel> i: getAllEvents().entrySet()) {
            if(i.getValue().getPersonID().equals(id)){
                events.put(i.getValue().getID(),i.getValue());
            }
        }
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
                    int compare =i.getValue().getEventType().compareTo(previousEventModel.getEventType());
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

    public static void logout() {
        initializer();
    }

    public static EventModel getSpouseFirstEvent(String person_id) {
        String spouseId=getPerson(person_id).getSpouseID();
        if(spouseId==null){
            return null;
        }
        return getFirstEvent(spouseId);
    }

    private static EventModel getFirstEvent(String personId) {
        EventModel returnEvent = null;
        for(HashMap.Entry<String,EventModel> i: getEvents().entrySet()) {
            EventModel currentEvent = i.getValue();
            if(currentEvent.getPersonID().equals(personId)){
                if(currentEvent.getEventType().equalsIgnoreCase("birth")){
                    return currentEvent;
                }
                else if(returnEvent==null){
                    returnEvent=currentEvent;
                }
                else if(returnEvent.getYear()>currentEvent.getYear()){
                    returnEvent=currentEvent;
                }

            }
        }
        return returnEvent;
    }

    public static int getLineColor(String lineType) {
        if(instance.colorByLineType.containsKey(lineType)){
            return instance.colorByLineType.get(lineType);
        }
        else{
            instance.colorByLineType.put(lineType,lineColors.get((instance.colorByLineType.size()+1)%lineColors.size()));
            return instance.colorByLineType.get(lineType);
        }

    }

    public static void getFamilyLines(EventModel childEvent, ArrayList<Polyline> familyLines, int thickness, GoogleMap map) {
        boolean doFather=true;
        boolean doMother=true;
        if(childEvent.getPersonID().equals(getFirstPerson().getID())){
            if(!isFathersSide()){
                doFather=false;
            }
            if(!isMothersSide()){
                doMother=false;
            }
        }
        if(!isMaleEvents()){
            doFather=false;
        }
        if(!isFemaleEvents()){
            doMother=false;
        }
        String father=getPerson(childEvent.getPersonID()).getFatherID();
        if(doFather&&father!= null){
            EventModel fatherEvent = getFirstEvent(father);
            Polyline fatherLine =map.addPolyline( new PolylineOptions()
                    .add(
                            new LatLng(childEvent.getLatitude(), childEvent.getLongitude()),
                            new LatLng(fatherEvent.getLatitude(),fatherEvent.getLongitude())
                    )
                    .color(ModelData.getLineColor("family")));
            fatherLine.setWidth(thickness);
            familyLines.add(fatherLine);
            getFamilyLines(fatherEvent,familyLines,thickness-3,map);
        }
        String mother=getPerson(childEvent.getPersonID()).getMotherID();
        if(doMother&&mother!= null){
            EventModel motherEvent = getFirstEvent(mother);
            Polyline motherLine =map.addPolyline( new PolylineOptions()
                    .add(
                            new LatLng(childEvent.getLatitude(), childEvent.getLongitude()),
                            new LatLng(motherEvent.getLatitude(),motherEvent.getLongitude())
                    )
                    .color(ModelData.getLineColor("family")));
            motherLine.setWidth(thickness);
            familyLines.add(motherLine);
            getFamilyLines(motherEvent,familyLines,thickness-3,map);
        }
    }

    public static String getCurrentEvent() {
        return instance.currentEvent;
    }

    public static void setCurrentEvent(String currentEvent) {
        instance.currentEvent = currentEvent;
    }

    public static ArrayList<Model> search(String query) {
        ArrayList<Model> returnList=new ArrayList<>();
        HashMap<String,PersonModel> people = instance.people;
        HashMap<String,EventModel> events = instance.events;
        String lowerQuery =query.toLowerCase();
        for(HashMap.Entry<String,EventModel> i: events.entrySet()) {
            EventModel currentEvent=i.getValue();
            if(currentEvent.getEventType().toLowerCase().contains(lowerQuery)){
                returnList.add(currentEvent);
            }
            else if(currentEvent.getCity().toLowerCase().contains(lowerQuery)){
                returnList.add(currentEvent);
            }
            else if(currentEvent.getCountry().toLowerCase().contains(lowerQuery)){
                returnList.add(currentEvent);
            }
            else if(Integer.toString(currentEvent.getYear()).contains(lowerQuery)){
                returnList.add(currentEvent);
            }
        }
        for(HashMap.Entry<String,PersonModel> i :people.entrySet()){
            PersonModel currentPerson =i.getValue();
            if(currentPerson.getLastName().toLowerCase().contains(lowerQuery)){
                returnList.add(currentPerson);
            }
            else if(currentPerson.getFirstName().toLowerCase().contains(lowerQuery)){
                returnList.add(currentPerson);
            }
        }
        return returnList;
    }
}
