package DAOs;

import Models.EventModel;
import Models.Model;
import Services.DataAccessException;
import Services.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventDAO extends DAO {
    public EventDAO() {
        super();
    }
    /**
     *
     * @param database
     */
    public EventDAO(Database database) {
        super(database);
    }

    /**
     *
     * @param insertModel
     * @return
     */
    @Override
    public Model insert(Model insertModel) throws DataAccessException {
        if(insertModel.getClass()!= EventModel.class){
            return null;
        }
        EventModel event = (EventModel)insertModel;
        String sql = "INSERT INTO Event (event_id,person, latitude, longitude, country, city, event_type, year) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,event.getID());
            stmt.setString(2,event.getPersonID());
            stmt.setFloat(3,event.getLatitude());
            stmt.setFloat(4,event.getLongitude());
            stmt.setString(5,event.getCountry());
            stmt.setString(6,event.getCity());
            stmt.setString(7,event.getEventType());
            stmt.setInt(8,event.getYear());

            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to insert event.");
        }
        return null;
    }

    /**
     *
     * @param searchString
     * @return
     */
    @Override
    public Model find(String searchString) throws DataAccessException {
        EventModel event;
        String sql="SELECT Event.Event_id, Event.person, Event.latitude,Event.longitude,Event.country,Event.city,Event.event_type,Event.year,Person.username FROM Event INNER JOIN Person ON Event.person=person.person_id where Event.event_id = ?;";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,searchString);
            result=stmt.executeQuery();
            if(result.next()){
                event=new EventModel(result.getString("event_id"),result.getString("person"),result.getString("username"),result.getFloat("latitude"),result.getFloat("longitude"),result.getString("country"),result.getString("city"),result.getString("event_type"),result.getInt("year"));
                return event;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to query event.");
        }
        return null;
    }

    @Override
    public ArrayList<Model> findMultiple(String username) throws DataAccessException {
        ArrayList<Model> events = new ArrayList<>();
        String sql = "SELECT * FROM Event WHERE person in (SELECT person_id FROM person WHERE username = ?);";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,username);
            result=stmt.executeQuery();
            while(result.next()){
                events.add(new EventModel(result.getString("event_id"),result.getString("person"),username,result.getFloat("latitude"),result.getFloat("longitude"),result.getString("country"),result.getString("city"),result.getString("event_type"),result.getInt("year")));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to query event.");
        }
        return events;
    }

    /**
     *
     */
    @Override
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Event";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to clear Event table.");
        }
    }

    @Override
    public void delete(String ID) throws DataAccessException {
        String sql = "DELETE FROM Event WHERE event_id =  ?";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,ID);
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to delete Event from table.");
        }
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public void deleteByUsername(String username) throws DataAccessException {
        String sql = "DELETE FROM Event WHERE person in  (SELECT person_id FROM person WHERE username =  ?)";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,username);
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to delete Event from table.");
        }
    }

}
