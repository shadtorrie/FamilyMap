package com.shad.familymap;

import org.junit.*;
import org.junit.Before;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import Data.Exceptions.RequestFailedException;
import Data.ModelData;
import Data.Proxy;
import Models.PersonModel;
import Requests.*;

import static org.junit.Assert.*;

public class ConnectionTests {
    Proxy mProxy;
    private static String baseURL ="http://192.168.0.22:5316";
    private static String username=UUID.randomUUID().toString();
    private static String password=UUID.randomUUID().toString();
    private static String email="test@gmail.com";
    private static String firstName = "test";
    private static String lastName = "Testing";
    private static String gender = "m";
    @Before
    public void setup(){
        mProxy=new Proxy();
    }
    @Test
    public void LoginTest() {
        ModelData.logout();
        try {
            LoginRequest request=new LoginRequest(username,password);
            URL url= new URL(baseURL+"/user/login");
            mProxy.request(request,url);
        } catch (Exception e) {
            e.printStackTrace();

        }
        assertTrue(ModelData.loggedIn());
        assertNotNull(ModelData.getAuthentication());
        assertNotNull(ModelData.getFirstPerson());
    }
    @Test
    public void registerTest() {
        try {

            RegisterRequest request=new RegisterRequest(username,password,email,firstName,lastName,gender);
            URL url= new URL(baseURL+"/user/register");
            mProxy.request(request,url);
        } catch (Exception e) {
            e.printStackTrace();

        }
        assertTrue(ModelData.loggedIn());
        assertNotNull(ModelData.getAuthentication());
        assertNotNull(ModelData.getFirstPerson());
        ModelData.logout();
        assertFalse(ModelData.loggedIn());
        assertNull(ModelData.getAuthentication());
        assertNull(ModelData.getFirstPerson());
    }
    @Test
    public void personTest() {
        if(!ModelData.loggedIn()){
            LoginTest();
        }
        try {
            PersonRequest request=new PersonRequest(ModelData.getFirstPerson().getID(),ModelData.getAuthentication().getID());
            URL url= new URL(baseURL+"/person");
            mProxy.request(request,url);
        } catch (Exception e) {
            e.printStackTrace();

        }
        PersonModel firstPerson = ModelData.getFirstPerson();
        assertEquals(username,firstPerson.getAssociatedUsername());
        assertNotNull(firstPerson.getFatherID());
        assertNotNull(firstPerson.getMotherID());
        assertEquals(firstName,firstPerson.getFirstName());
        assertEquals(lastName,firstPerson.getLastName());
        assertEquals(gender,firstPerson.getGender());
        assertEquals(31,ModelData.getPeople().size());
    }
    @Test
    public void EventTest() {
        if(!ModelData.loggedIn()){
            LoginTest();
        }
        try {
            EventRequest request=new EventRequest(ModelData.getAuthentication().getID());
            URL url= new URL(baseURL+"/event");
            mProxy.request(request,url);
        } catch (Exception e) {
            e.printStackTrace();

        }
        assertEquals(91,ModelData.getAllEvents().size());
    }
    @Test
    public void LoginFailTest(){
        String usernameFake=UUID.randomUUID().toString();
        String passwordFake=UUID.randomUUID().toString();
        try {
            ModelData.logout();
            LoginRequest request=new LoginRequest(usernameFake,passwordFake);
            URL url= new URL(baseURL+"/user/login");
            mProxy.request(request,url);
        } catch (Exception e) {
            assertEquals("Error: Invalid Username and/or password",e.getMessage());

        }
        assertFalse(ModelData.loggedIn());
        assertNull(ModelData.getAuthentication());
        assertNull(ModelData.getFirstPerson());
    }
    @Test
    public void EventFailTest() {
        try {
            EventRequest request=new EventRequest(UUID.randomUUID().toString());
            URL url= new URL(baseURL+"/event");
            mProxy.request(request,url);
        } catch (Exception e) {
            assertEquals("Error: Invalid auth token",e.getMessage());

        }
        assertEquals(0,ModelData.getAllEvents().size());
    }
}