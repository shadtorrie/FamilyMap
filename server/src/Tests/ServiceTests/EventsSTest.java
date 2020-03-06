package Tests.ServiceTests;

import DAOs.AuthDAO;
import DAOs.DAO;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import ModelsServer.Auth;
import ModelsServer.Event;
import ModelsServer.Person;
import Services.DataAccessException;
import Services.EventsS;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;


class EventsSTest extends ServiceTest {

    @BeforeEach
    public void setup() throws DataAccessException {
        service = new EventsS();
        super.setup();
        dao = new EventDAO(db);
    }
    @Test
    public void testFindEventPass(){
        Result.Event results = null;
        try {
            String eventID = "1234";
            String personID = "pers";
            String username = "test1";
            dao.insert(new Event(eventID, personID,username,12.1f,11.1f,"USA","Provo","grad",2022));
            DAO authDao = new AuthDAO(db);
            String authID = "test1234";
            authDao.insert(new Auth(authID,username));
            DAO personDao = new PersonDAO(db);
            personDao.insert(new Person(personID,username,"Torrie","Test",'M'));
            db.closeConnection(true);
            results = (Result.Event) service.requestService(new Request.Event(eventID, authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertTrue(results.isSuccess(),"The service didn't find the event.");
    }
    @Test
    public void testFindEventMultiplePass(){
        Result.EventList results = null;
        try {
            String eventID = "1234";
            String personID = "pers";
            String eventID2 = "12342";
            String personID2 = "pers2";
            String username = "test1";
            Event firstEvent = new Event(eventID, personID,username,12.1f,11.1f,"USA","Provo","grad",2022);
            Event secondEvent = new Event(eventID2, personID2,username, 12.12f,11.12f,"Canada","Orem","birth",2023);
            dao.insert(firstEvent);
            dao.insert(secondEvent);
            DAO authDao = new AuthDAO(db);
            String authID = "test1234";
            authDao.insert(new Auth(authID,username));
            DAO personDao = new PersonDAO(db);
            personDao.insert(new Person(personID,username,"Torrie","Test",'M'));
            db.closeConnection(true);
            results = (Result.EventList) service.requestService(new Request.Event(authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertEquals(results.getData().size(),1,"The service didn't find the event.");
        Gson gson = new Gson();
        System.out.println(gson.toJson(results));
    }
    @Test
    public void testFindEventFailAuth(){
        Result.Event results = null;
        try {
            String eventID = "1234";
            String personID = "pers";
            String username = "test1";
            dao.insert(new Event(eventID, personID,username,12.1f,11.1f,"USA","Provo","grad",2022));
            String authID = "test1234";
            DAO personDao = new PersonDAO(db);
            personDao.insert(new Person(personID,username,"Torrie","Test",'M'));
            db.closeConnection(true);
            results = (Result.Event) service.requestService(new Request.Event(eventID, authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertFalse(results.isSuccess(),"The authentication token should be invalid");
        assertEquals("Invalid auth token",results.getMessage(),"The error message was incorrect.");
    }
    @Test
    public void testFindEventFail(){
        Result.Event results = null;
        try {
            DAO authDao = new AuthDAO(db);
            String authID = "test1234";
            String username = "test1";
            authDao.insert(new Auth(authID,username));
            db.closeConnection(true);
            results = (Result.Event) service.requestService(new Request.Event("test123", authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertFalse(results.isSuccess(),"The event should not be found");
        assertEquals("Invalid eventID parameter", results.getMessage(),"The error message was incorrect.");
    }

    @AfterEach
    public void teardown() throws DataAccessException {
        super.teardown();
    }

}