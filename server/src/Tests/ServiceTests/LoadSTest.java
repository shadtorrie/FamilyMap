package Tests.ServiceTests;

import DAOs.EventDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import Models.EventModel;
import Models.PersonModel;
import Models.UserModel;
import Requests.LoadRequest;
import Results.LoadResult;
import Services.DataAccessException;
import Services.LoadS;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoadSTest extends ServiceTest {
    ArrayList<EventModel> events;
    ArrayList<PersonModel> people;
    ArrayList<UserModel> users;
    @BeforeEach
    void setUp() throws DataAccessException, SQLException {
        service = new LoadS();
        events = new ArrayList<>();
        people = new ArrayList<>();
        users = new ArrayList<>();
        super.setup();
        db.closeConnection(true);
    }
    @Test
    public void testLoadEmpty(){
        LoadResult result = null;
        try {
            result = (LoadResult) service.requestService(new LoadRequest(users,people,events));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }
    @Test
    public void testLoadPeople(){
        LoadResult result = null;
        boolean success = false;
        PersonModel person1 = new PersonModel("1234","shad","Torrie","Shad","m");
        PersonModel person2 = new PersonModel("12a345","shaddy","Torrie1","Shad1","f");
        try {
            people.add(person1);
            people.add(person2);
            result = (LoadResult) service.requestService(new LoadRequest(users,people,events));
            db.openConnection();
            dao = new PersonDAO(db);
            PersonModel returnPerson1 = (PersonModel) dao.find(person1.getID());
            PersonModel returnPerson2 = (PersonModel) dao.find(person2.getID());
            assertEquals(person1,returnPerson1,"Load service didn't add the first person");
            assertEquals(person2,returnPerson2,"Load service didn't add the second person");
            success = true;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertTrue(success,"Load service encountered an error");
        assertTrue(result.isSuccess(),"Load service failed");
    }
    @Test
    public void testLoadEvent(){
        LoadResult result = null;
        boolean success = false;
        String person1 = "Shad";
        String person2 = "Macy";
        EventModel event1 = new EventModel("1234",person1,"test",12.2f,12.3f,"USA","Logan","Birth",1997);
        EventModel event2 = new EventModel("12a345",person2,"test",13.2f,14.2f,"USA","Laguna Hills","Birth",1996);
        PersonModel personModel = new PersonModel(person1,"test","first","last","m");
        PersonModel personModel2 = new PersonModel(person2,"test2","first2","last2","f");
        try {
            events.add(event1);
            events.add(event2);
            people.add(personModel);
            people.add(personModel2);
            result = (LoadResult) service.requestService(new LoadRequest(users,people,events));
            db.openConnection();
            dao = new EventDAO(db);
            EventModel returnEvent1 = (EventModel) dao.find(event1.getID());
            EventModel returnEvent2 = (EventModel) dao.find(event2.getID());
            assertEquals(event1,returnEvent1,"Load service didn't add the first event");
            assertEquals(event2,returnEvent2,"Load service didn't add the second event");
            success = true;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertTrue(success,"Load service encountered an error");
        assertTrue(result.isSuccess(),"Load service failed");
    }
    @Test
    public void testLoadUser(){
        LoadResult result = null;
        boolean success = false;
        UserModel user1 = new UserModel("1234","shadt","shad.torrie@gmail.com");
        UserModel user2 = new UserModel("12a345","Macy","macy@gail.com");
        try {
            users.add(user1);
            users.add(user2);
            result = (LoadResult) service.requestService(new LoadRequest(users,people,events));
            db.openConnection();
            dao = new UserDAO(db);
            UserModel returnUser1 = (UserModel) dao.find(user1.getID());
            UserModel returnUser2 = (UserModel) dao.find(user2.getID());
            assertEquals(user1,returnUser1,"Load service didn't add the first user");
            assertEquals(user2,returnUser2,"Load service didn't add the second user");
            success = true;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertTrue(success,"Load service encountered an error");
        assertTrue(result.isSuccess(),"Load service failed");
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        super.teardown();
    }
}