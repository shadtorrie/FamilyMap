package DAOs;

import Models.Event;
import Models.Model;
import Models.Person;
import Services.DataAccessException;
import Services.Database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class PersonDAO extends DAO{
    public PersonDAO() {
        super();
    }
    /**
     *
     * @param database
     */
    public PersonDAO(Database database) {
        super(database);
    }

    /**
     *
     * @param insertModel
     * @return
     */
    @Override
    public Model insert(Model insertModel) throws DataAccessException {
        if(insertModel.getClass()!= Person.class){
            return null;
        }
        Person person = (Person)insertModel;
        String sql = "INSERT INTO Person (person_id, username, first_name, last_name, gender, father_id, mother_id, spouse_id) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,person.getID());
            stmt.setString(2,person.getUsername());
            stmt.setString(3,person.getFirst_name());
            stmt.setString(4,person.getLast_name());
            stmt.setInt(5,person.getGender());
            stmt.setString(6,person.getFather_id());
            stmt.setString(7,person.getMother_id());
            stmt.setString(8,person.getSpouse_id());

            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to insert Authentication.");
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
        Person person = null;
        String sql = "SELECT * FROM Person;";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            result=stmt.executeQuery();
            if(result.next()){
                person=new Person(result.getString("person_id"),result.getString("username"),
                        result.getString("first_name"),result.getString("last_name"),
                        (char)result.getInt("gender"),result.getString("father_id"),
                        result.getString("mother_id"),result.getString("spouse_id"));
                return person;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to query Person.");
        }
        return null;
    }

    @Override
    public ArrayList<Model> find() throws DataAccessException {
        ArrayList<Model> people = new ArrayList<>();
        String sql = "SELECT * FROM Person;";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            result=stmt.executeQuery();
            while(result.next()){
                people.add(new Person(result.getString("person_id"),result.getString("username"),
                        result.getString("first_name"),result.getString("last_name"),
                        (char)result.getInt("gender"),result.getString("father_id"),
                        result.getString("mother_id"),result.getString("spouse_id")));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to query Person.");
        }
        return people;
    }


    /**
     *
     */
    @Override
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Person";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to clear Person table.");
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
}
