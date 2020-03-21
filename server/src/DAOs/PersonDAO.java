package DAOs;

import Models.Model;
import Models.PersonModel;
import Services.DataAccessException;
import Services.Database;

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
        if(insertModel.getClass()!= PersonModel.class){
            return null;
        }
        PersonModel person = (PersonModel)insertModel;
        String sql = "INSERT INTO Person (person_id, username, first_name, last_name, gender, father_id, mother_id, spouse_id) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,person.getID());
            stmt.setString(2,person.getAssociatedUsername());
            stmt.setString(3,person.getFirstName());
            stmt.setString(4,person.getLastName());
            stmt.setString(5,person.getGender());
            stmt.setString(6,person.getFatherID());
            stmt.setString(7,person.getMotherID());
            stmt.setString(8,person.getSpouseID());

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
        PersonModel person = null;
        String sql = "SELECT * FROM Person WHERE person_id = ?;";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,searchString);
            result=stmt.executeQuery();
            if(result.next()){
                person=new PersonModel(result.getString("person_id"),result.getString("username"),
                        result.getString("first_name"),result.getString("last_name"),
                        result.getString("gender"),result.getString("father_id"),
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
    public Model find(String searchString,String firstName,String lastName) throws DataAccessException {
        PersonModel person = null;
        String sql = "SELECT * FROM Person WHERE username = ? and first_name = ? and last_name = ?;";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,searchString);
            stmt.setString(2,firstName);
            stmt.setString(3,lastName);
            result=stmt.executeQuery();
            if(result.next()){
                person=new PersonModel(result.getString("person_id"),result.getString("username"),
                        result.getString("first_name"),result.getString("last_name"),
                        result.getString("gender"),result.getString("father_id"),
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
    public ArrayList<Model> findMultiple(String username) throws DataAccessException {
        ArrayList<Model> people = new ArrayList<>();
        String sql = "SELECT * FROM Person WHERE username = ?;";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,username);
            result=stmt.executeQuery();
            while(result.next()){
                people.add(new PersonModel(result.getString("person_id"),result.getString("username"),
                        result.getString("first_name"),result.getString("last_name"),
                        result.getString("gender"),result.getString("father_id"),
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

    @Override
    public void delete(String ID) throws DataAccessException {

    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public void deleteByUsername(String username) throws DataAccessException {
        String sql = "DELETE FROM person WHERE username =  ?;";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,username);
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to delete person from table.");
        }
    }
}
