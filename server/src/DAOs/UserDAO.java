package DAOs;

import ModelsServer.Model;
import ModelsServer.User;
import Services.DataAccessException;
import Services.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class UserDAO extends DAO {
    public UserDAO() {
        super();
    }

    /**
     *
     * @param database
     */
    public UserDAO(Database database) {
        super(database);
    }

    /**
     *
     * @param insertModel
     * @return
     */
    @Override
    public Model insert(Model insertModel) throws DataAccessException {
        if(insertModel.getClass()!= User.class){
            throw new DataAccessException("Unable to insert user.");
        }
        User user = (User)insertModel;
        String sql = "INSERT INTO Users (username, password, email) VALUES(?,?,?)";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,user.getUserName());
            stmt.setString(2,user.getPassword());
            stmt.setString(3,user.getEmail());
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to insert user.");
        }
        return insertModel;
    }

    /**
     *
     * @param searchString
     * @return
     */
    @Override
    public Model find(String searchString) throws DataAccessException {
        User user;
        String sql = "SELECT * FROM Users WHERE username = ?;";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,searchString);
            result=stmt.executeQuery();
            if(result.next()){
                user=new User(result.getString("username"),result.getString("password"),result.getString("email"));
                return user;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to query user.");
        }
        return null;

    }

    @Override
    public ArrayList<Model> findMultiple(String username) throws DataAccessException {
        ArrayList<Model> users = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE username = ?;";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,username);
            result=stmt.executeQuery();
            while(result.next()){
                users.add(new User(result.getString("username"),result.getString("password"),result.getString("email")));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to query user.");
        }
        return users;
    }

    /**
     *
     */
    @Override
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Users";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to clear user table.");
        }

    }

    @Override
    public void delete(String ID) throws DataAccessException {

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
        String sql = "DELETE FROM Users WHERE username = ?";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,username);
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to delete User from table.");
        }
    }
}
