package com.shad.familymap;
import org.junit.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;
import Data.ModelData;
import Data.Proxy;
import Models.EventModel;
import Models.Model;
import Models.PersonModel;
import Requests.*;
import static org.junit.Assert.*;
public class SearchTests {
    @Before
    public void setup(){
        SettingsTest.loadData();
    }
    @Test
    public void SearchFailTest(){
        ArrayList<Model> models = ModelData.search(UUID.randomUUID().toString());
        assertEquals(0,models.size());
    }
    @Test
    public void SearchPersonTest(){
        String name= ModelData.getFirstPerson().getLastName();
        ArrayList<Model> models = ModelData.search(name);
        assertEquals(5,models.size());
    }
    @Test
    public void SearchEventTest(){
        TreeMap<Integer, String> events = ModelData.getPersonsEvents(ModelData.getFirstPerson().getID());
        EventModel event=ModelData.getEvent(events.firstEntry().getValue());
        ArrayList<Model> models = ModelData.search(event.getEventType());
        assertEquals(31,models.size());
    }

}
