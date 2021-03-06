package DAOs;

import Models.AuthModel;
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
        if(insertModel.getClass()!= AuthModel.class){
            return null;
        }
        AuthModel auth = (AuthModel)insertModel;
        String sql = "INSERT INTO Authorization_Token (auth_token,username) VALUES(?,?)";
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,auth.getID());
            stmt.setString(2,auth.getUserName());

            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Unable to insert Authorization_Token.");
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
        AuthModel auth;
        String sql = "SELECT * FROM Authorization_Token WHERE auth_token = ?;";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,searchString);
            result=stmt.executeQuery();
            if(result.next()){
                auth=new AuthModel(result.getString("auth_token"),result.getString("username"));
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
    public ArrayList<Model> findMultiple(String username) throws DataAccessException {
        ArrayList<Model> auths = new ArrayList<>();
        String sql = "SELECT * FROM Authorization_Token WHERE username = ?;;";
        ResultSet result = null;
        try (PreparedStatement stmt = super.getDbConnection().getPreparedStatement(sql)){
            stmt.setString(1,username);
            result=stmt.executeQuery();
            while(result.next()){
                auths.add(new AuthModel(result.getString("auth_token"),result.getString("username")));
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

    }


}
