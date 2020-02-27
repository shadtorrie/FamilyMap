package DAOs;

import Models.Auth;
import Models.Event;
import Models.Model;
import Services.DataAccessException;
import Services.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class AuthDAO extends DAO {
    public AuthDAO() {
        super();
    }
    /**
     *
     * @param database
     */
    public AuthDAO(Database database) {
        super(database);
    }

    /**
     *
     * @param insertModel
     * @return
     */
    @Override
    public Model insert(Model insertModel) throws DataAccessException {
        if(insertModel.getClass()!= Auth.class){
            return null;
        }
        Auth auth = (Auth)insertModel;
        String sql = "INSERT INTO Authorization_Token (auth_token,username) VALUES(?,?)";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,auth.getID());
            stmt.setString(2,auth.getUsername());

            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to insert Authorization_Token.");
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
        Auth auth;
        String sql = "SELECT * FROM Authorization_Token WHERE auth_token = ?;";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,searchString);
            result=stmt.executeQuery();
            if(result.next()){
                auth=new Auth(result.getString("auth_token"),result.getString("username"));
                return auth;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to query Authorization_Token.");
        }
        return null;
    }

    @Override
    public ArrayList<Model> find() throws DataAccessException {
        ArrayList<Model> auths = new ArrayList<>();
        String sql = "SELECT * FROM Authorization_Token;";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            result=stmt.executeQuery();
            while(result.next()){
                auths.add(new Auth(result.getString("auth_token"),result.getString("username")));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to query Authorization_Token.");
        }
        return auths;
    }

    /**
     *
     */
    @Override
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Authorization_Token";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to clear Authorization_Token table.");
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
