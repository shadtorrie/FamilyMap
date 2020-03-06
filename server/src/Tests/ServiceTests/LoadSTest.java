package Tests.ServiceTests;

import DAOs.EventDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import ModelsServer.Event;
import ModelsServer.Person;
import ModelsServer.User;
import Request.Load;
import Services.DataAccessException;
import Services.LoadS;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoadSTest extends ServiceTest {
    ArrayList<Event> events;
    ArrayList<Person> people;
    ArrayList<User> users;
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
        Result.Load result = null;
        try {
            result = (Result.Load) service.requestService(new Load(users,people,events));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }
    @Test
    public void testLoadPeople(){
        Result.Load result = null;
        boolean success = false;
        Person person1 = new Person("1234","shad","Torrie","Shad",'m');
        Person person2 = new Person("12a345","shaddy","Torrie1","Shad1",'f');
        try {
            people.add(person1);
            people.add(person2);
            result = (Result.Load) service.requestService(new Load(users,people,events));
            db.openConnection();
            dao = new PersonDAO(db);
            Person returnPerson1 = (Person) dao.find(person1.getID());
            Person returnPerson2 = (Person) dao.find(person2.getID());
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
        Result.Load result = null;
        boolean success = false;
        Event event1 = new Event("1234","shad","test",12.2f,12.3f,"USA","Logan","Birth",1997);
        Event event2 = new Event("12a345","Macy","test",13.2f,14.2f,"USA","Laguna Hills","Birth",1996);
        try {
            events.add(event1);
            events.add(event2);
            result = (Result.Load) service.requestService(new Load(users,people,events));
            db.openConnection();
            dao = new EventDAO(db);
            Event returnEvent1 = (Event) dao.find(event1.getID());
            Event returnEvent2 = (Event) dao.find(event2.getID());
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
        Result.Load result = null;
        boolean success = false;
        User user1 = new User("1234","shadt","shad.torrie@gmail.com");
        User user2 = new User("12a345","Macy","macy@gail.com");
        try {
            users.add(user1);
            users.add(user2);
            result = (Result.Load) service.requestService(new Load(users,people,events));
            db.openConnection();
            dao = new UserDAO(db);
            User returnUser1 = (User) dao.find(user1.getID());
            User returnUser2 = (User) dao.find(user2.getID());
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