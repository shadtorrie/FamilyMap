package Tests.ServiceTests;

import DAOs.AuthDAO;
import DAOs.DAO;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import Models.AuthModel;
import Models.EventModel;
import Models.PersonModel;
import Requests.EventRequest;
import Results.EventListResult;
import Results.EventResult;
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
        EventResult results = null;
        try {
            String eventID = "1234";
            String personID = "pers";
            String username = "test1";
            dao.insert(new EventModel(eventID, personID,username,12.1f,11.1f,"USA","Provo","grad",2022));
            DAO authDao = new AuthDAO(db);
            String authID = "test1234";
            authDao.insert(new AuthModel(authID,username));
            DAO personDao = new PersonDAO(db);
            personDao.insert(new PersonModel(personID,username,"Torrie","Test","m"));
            db.closeConnection(true);
            results = (EventResult) service.requestService(new EventRequest(eventID, authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertTrue(results.isSuccess(),"The service didn't find the event.");
    }
    @Test
    public void testFindEventMultiplePass(){
        EventListResult results = null;
        try {
            String eventID = "1234";
            String personID = "pers";
            String eventID2 = "12342";
            String personID2 = "pers2";
            String username = "test1";
            EventModel firstEvent = new EventModel(eventID, personID,username,12.1f,11.1f,"USA","Provo","grad",2022);
            EventModel secondEvent = new EventModel(eventID2, personID2,username, 12.12f,11.12f,"Canada","Orem","birth",2023);
            dao.insert(firstEvent);
            dao.insert(secondEvent);
            DAO authDao = new AuthDAO(db);
            String authID = "test1234";
            authDao.insert(new AuthModel(authID,username));
            DAO personDao = new PersonDAO(db);
            personDao.insert(new PersonModel(personID,username,"Torrie","Test","m"));
            db.closeConnection(true);
            results = (EventListResult) service.requestService(new EventRequest(authID));
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
        EventResult results = null;
        try {
            String eventID = "1234";
            String personID = "pers";
            String username = "test1";
            dao.insert(new EventModel(eventID, personID,username,12.1f,11.1f,"USA","Provo","grad",2022));
            String authID = "test1234";
            DAO personDao = new PersonDAO(db);
            personDao.insert(new PersonModel(personID,username,"Torrie","Test","m"));
            db.closeConnection(true);
            results = (EventResult) service.requestService(new EventRequest(eventID, authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertFalse(results.isSuccess(),"The authentication token should be invalid");
        assertEquals("Error: Invalid auth token",results.getMessage(),"The error message was incorrect.");
    }
    @Test
    public void testFindEventFail(){
        EventResult results = null;
        try {
            DAO authDao = new AuthDAO(db);
            String authID = "test1234";
            String username = "test1";
            authDao.insert(new AuthModel(authID,username));
            db.closeConnection(true);
            results = (EventResult) service.requestService(new EventRequest("test123", authID));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(results,"Result was not received.");
        assertFalse(results.isSuccess(),"The event should not be found");
        assertEquals("Error: Invalid eventID parameter", results.getMessage(),"The error message was incorrect.");
    }

    @AfterEach
    public void teardown() throws DataAccessException {
        super.teardown();
    }

}