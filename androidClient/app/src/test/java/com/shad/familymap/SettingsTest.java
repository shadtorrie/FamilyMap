package com.shad.familymap;
import org.junit.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import Data.ModelData;
import Data.Proxy;
import Models.EventModel;
import Models.PersonModel;
import Requests.*;
import static org.junit.Assert.*;

public class SettingsTest {
    private static String baseURL ="http://192.168.0.61:5316";
    private HashMap<String,EventModel> events;

    @Before
    public void setup(){
        events=null;
        loadData();
    }
    @Test
    public void noMaleTest(){
        ModelData.setMaleEvents(false);
        events=getEvents();
        assertNotNull(events);
        for(HashMap.Entry<String,EventModel> i: events.entrySet()) {
            assertNotEquals("m",ModelData.getPerson(i.getValue().getPersonID()).getGender().toLowerCase());
        }
    }
    @Test
    public void MaleTest(){
        ModelData.setFemaleEvents(false);
        events=getEvents();
        assertNotNull(events);
        for(HashMap.Entry<String,EventModel> i: events.entrySet()) {
            assertEquals("m",ModelData.getPerson(i.getValue().getPersonID()).getGender().toLowerCase());
        }
    }
    @Test
    public void FemaleTest(){
        ModelData.setMaleEvents(false);
        events=getEvents();
        assertNotNull(events);
        for(HashMap.Entry<String,EventModel> i: events.entrySet()) {
            assertEquals("f",ModelData.getPerson(i.getValue().getPersonID()).getGender().toLowerCase());
        }
    }
    public static void loadData() {
        try {
            ModelData.logout();
            Proxy mProxy=new Proxy();
            Requests request=new RegisterRequest(UUID.randomUUID().toString(),"testing123","jimmy@gmail.com","Jim","test","m");
            URL url= new URL(baseURL+"/user/register");
            mProxy.request((RegisterRequest)request,url);
            request=new EventRequest(ModelData.getAuthentication().getID());
            url= new URL(baseURL+"/event");
            mProxy.request((EventRequest)request,url);
            request=new PersonRequest(ModelData.getFirstPerson().getID(),ModelData.getAuthentication().getID());
            url= new URL(baseURL+"/person");
            mProxy.request((PersonRequest)request,url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void noFemaleTest(){
        ModelData.setFemaleEvents(false);
        events=getEvents();
        assertNotNull(events);
        for(HashMap.Entry<String,EventModel> i: events.entrySet()) {
            assertNotEquals("f",ModelData.getPerson(i.getValue().getPersonID()).getGender().toLowerCase());
        }
    }
    @Test
    public void noMotherSideTest(){
        ModelData.setMothersSide(false);
        events=getEvents();
        assertNotNull(events);
        PersonModel currentPerson =ModelData.getPerson(ModelData.getFirstPerson().getMotherID());
        ArrayList<String> motherside=new ArrayList<>();
        getAncestors(currentPerson,motherside);
        for(HashMap.Entry<String,EventModel> i: events.entrySet()) {
            String personID=i.getValue().getPersonID();
            assertFalse(motherside.contains(personID));
        }
    }
    @Test
    public void noFatherSideTest(){
        ModelData.setFathersSide(false);
        events=getEvents();
        assertNotNull(events);
        PersonModel currentPerson =ModelData.getPerson(ModelData.getFirstPerson().getFatherID());
        ArrayList<String> fatherSide=new ArrayList<>();
        getAncestors(currentPerson,fatherSide);
        for(HashMap.Entry<String,EventModel> i: events.entrySet()) {
            String personID=i.getValue().getPersonID();
            assertFalse(fatherSide.contains(personID));
        }
    }
    @Test
    public void motherSideTest(){
        ModelData.setFathersSide(false);
        events=getEvents();
        assertNotNull(events);
        PersonModel currentPerson =ModelData.getPerson(ModelData.getFirstPerson().getMotherID());
        ArrayList<String> motherside=new ArrayList<>();
        motherside.add(ModelData.getFirstPerson().getID());
        getAncestors(currentPerson,motherside);
        for(HashMap.Entry<String,EventModel> i: events.entrySet()) {
            String personID=i.getValue().getPersonID();
            assertTrue(motherside.contains(personID));
        }
    }
    @Test
    public void fatherSideTest(){
        ModelData.setMothersSide(false);
        events=getEvents();
        assertNotNull(events);
        PersonModel currentPerson =ModelData.getPerson(ModelData.getFirstPerson().getFatherID());
        ArrayList<String> fatherside=new ArrayList<>();
        fatherside.add(ModelData.getFirstPerson().getID());
        getAncestors(currentPerson,fatherside);
        for(HashMap.Entry<String,EventModel> i: events.entrySet()) {
            String personID=i.getValue().getPersonID();
            assertTrue(fatherside.contains(personID));
        }
    }

    private void getAncestors(PersonModel currentPerson, ArrayList<String> motherside) {
        motherside.add(currentPerson.getID());
        if(currentPerson.getMotherID()!=null){
            getAncestors(ModelData.getPerson(currentPerson.getMotherID()),motherside);
        }
        if(currentPerson.getFatherID()!=null){
            getAncestors(ModelData.getPerson(currentPerson.getFatherID()),motherside);
        }
    }

    public HashMap<String, EventModel> getEvents(){
        return ModelData.getEvents();
    }
}
