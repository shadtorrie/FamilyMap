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
import Models.PersonModel;
import Requests.*;
import static org.junit.Assert.*;

public class OtherTests {
    @Before
    public void setup(){
        SettingsTest.loadData();
    }
    @Test
    public void SpouseEventTest(){
        EventModel event = ModelData.getSpouseFirstEvent(ModelData.getFirstPerson().getFatherID());
        assertNotNull(event);
        assertEquals(ModelData.getFirstPerson().getMotherID(),event.getPersonID());
        TreeMap<Integer, String> events =ModelData.getPersonsEvents(event.getPersonID());
        for(TreeMap.Entry<Integer,String> i: events.entrySet()) {
            assertTrue(i.getKey()>event.getYear()||event.getID().equals(i.getValue()));
        }
    }
    @Test
    public void PersonEventOrderTest(){
        TreeMap<Integer, String> events =ModelData.getPersonsEvents(ModelData.getFirstPerson().getID());
        int previousYear=-1;
        for(TreeMap.Entry<Integer,String> i: events.entrySet()) {
            assertTrue(previousYear<=ModelData.getEvent(i.getValue()).getYear());
        }
    }
}
