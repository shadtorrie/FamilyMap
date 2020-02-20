package DAOs;

import Models.Model;
import Models.User;
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
            return null;
        }
        User user = (User)insertModel;
        String sql = "INSERT INTO Users (username, password, email) VALUES(?,?,?)";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,user.getID());
            stmt.setString(2,user.getPassword());
            stmt.setString(3,user.getEmail());
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to insert user.");
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
    public ArrayList<Model> find() throws DataAccessException {
        ArrayList<Model> users = new ArrayList<>();
        String sql = "SELECT * FROM Users;";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
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
            throw new DataAccessException("Unable to insert user.");
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
