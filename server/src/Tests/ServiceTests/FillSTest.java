package Tests.ServiceTests;

import DAOs.EventDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import Models.Model;
import Models.UserModel;
import Requests.FillRequest;
import Results.FillResult;
import Services.DataAccessException;
import Services.FillS;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FillSTest extends ServiceTest {
    @BeforeEach
    public void setup() throws DataAccessException {
        super.service=new FillS();
        super.setup();
        dao = new UserDAO(db);
    }
    @Test
    public void testFillZeroGenerations() {
        String username="test";
        int genCount=0;
        FillResult result = null;
        try {
            dao.insert(new UserModel(username,"test","shad"));
            db.closeConnection(true);
            result = (FillResult) service.requestService(new FillRequest(username,genCount));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(result,"Request Fill service failed.");
        assertTrue(result.isSuccess(),"Fill service failed.");
        assertEquals("Successfully added 1 persons and 1 events to the database.",result.getMessage(),"Added the incorrect amount of people or events");
        ArrayList<Model> person = null;
        ArrayList<Model> events = null;
        try{
            db.openConnection();
            dao = new PersonDAO(db);
            person = dao.findMultiple(username);
            dao = new EventDAO(db);
            events = dao.findMultiple(username);
        }
        catch(DataAccessException e){
            e.printStackTrace();
        }
        assertNotNull(person,"Person was not inserted");
        assertNotNull(events,"Event was not inserted");
        assertEquals(1,person.size(),"Not the correct amount of people.");
        assertEquals(1,events.size(),"Not the correct amount of events.");

    }
    @Test
    public void testFillFailInvalidUsername() {
        String username="test";
        int genCount=0;
        FillResult result = null;
        try {
            db.closeConnection(true);
            result = (FillResult) service.requestService(new FillRequest(username,genCount));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(result,"Request Fill service failed.");
        assertFalse(result.isSuccess(),"Fill service passed.");
        assertEquals("Invalid username or generations parameter",result.getMessage(),"Added the incorrect amount of people or events");
        ArrayList<Model> person = null;
        ArrayList<Model> events = null;
        try{
            db.openConnection();
            dao = new PersonDAO(db);
            person = dao.findMultiple(username);
            dao = new EventDAO(db);
            events = dao.findMultiple(username);
        }
        catch(DataAccessException e){
            e.printStackTrace();
        }
        assertNotNull(person,"Person was inserted");
        assertNotNull(events,"Event was inserted");
        assertEquals(0,person.size(),"Not the correct amount of people.");
        assertEquals(0,events.size(),"Not the correct amount of events.");

    }
    @Test
    public void testFillFailInvalidGenCount() {
        String username="test";
        int genCount=-1;
        FillResult result = null;
        try {
            dao.insert(new UserModel(username,"test","shad"));
            db.closeConnection(true);
            result = (FillResult) service.requestService(new FillRequest(username,genCount));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(result,"Request Fill service failed.");
        assertFalse(result.isSuccess(),"Fill service passed.");
        assertEquals("Invalid username or generations parameter",result.getMessage(),"Added the incorrect amount of people or events");
        ArrayList<Model> person = null;
        ArrayList<Model> events = null;
        try{
            db.openConnection();
            dao = new PersonDAO(db);
            person = dao.findMultiple(username);
            dao = new EventDAO(db);
            events = dao.findMultiple(username);
        }
        catch(DataAccessException e){
            e.printStackTrace();
        }
        assertNotNull(person,"Person was inserted");
        assertNotNull(events,"Event was inserted");
        assertEquals(0,person.size(),"Not the correct amount of people.");
        assertEquals(0,events.size(),"Not the correct amount of events.");
    }
    @Test
    public void testFillPass() {
        String username="test";
        int genCount=2;
        FillResult result = null;
        try {
            dao.insert(new UserModel(username,"test","shad"));
            db.closeConnection(true);
            result = (FillResult) service.requestService(new FillRequest(username,genCount));
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        assertNotNull(result,"Request Fill service failed.");
        assertTrue(result.isSuccess(),"Fill service failed.");
        assertEquals("Successfully added 7 persons and 19 events to the database.",result.getMessage(),"Added the incorrect amount of people or events");
        ArrayList<Model> person = null;
        ArrayList<Model> events = null;
        try{
            db.openConnection();
            dao = new PersonDAO(db);
            person = dao.findMultiple(username);
            dao = new EventDAO(db);
            events = dao.findMultiple(username);
        }
        catch(DataAccessException e){
            e.printStackTrace();
        }
        assertNotNull(person,"Person search failed");
        assertNotNull(events,"Event person search failed");
        assertEquals(7,person.size(),"Not the correct amount of people.");
        assertEquals(19,events.size(),"Not the correct amount of events.");
    }
    @AfterEach
    public void teardown() throws DataAccessException {
        super.teardown();
    }
}