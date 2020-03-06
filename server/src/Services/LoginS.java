package Services;

import DAOs.AuthDAO;
import DAOs.DAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import ModelsServer.Auth;
import ModelsServer.Model;
import ModelsServer.User;
import Request.Login;
import Request.Requests;
import Result.Results;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class LoginS extends Service {
    /**
     *
     */
    public LoginS() throws DataAccessException {
        super();
    }

    /**
     *
     * @param request
     * @return
     */
    @Override
    public Results requestService(Requests request) {
        try{
            if(!request.getClass().equals(Login.class)){
                throw new DataAccessException();
            }
            Login loginRequest = (Login) request;
            dbConnection.openConnection();
            DAO dao = new UserDAO(dbConnection);
            User resultModel = (ModelsServer.User) dao.find(loginRequest.getUserName());
            if(resultModel==null){
                dbConnection.closeConnection(false);
                return new Result.Login("Request property missing or has invalid value",false);
            }
            dao=new PersonDAO(dbConnection);
            ArrayList<Model> personResultModel = dao.findMultiple(resultModel.getID());
            if(personResultModel.size()==0||personResultModel.get(0)==null){
                dbConnection.closeConnection(false);
                return new Result.Login("Request property missing or has invalid value",false);
            }
            dao = new AuthDAO(dbConnection);
            Auth authModel = (Auth) dao.insert(new Auth(UUID.randomUUID().toString(),resultModel.getID()));
            dbConnection.closeConnection(true);
            return new Result.Login(authModel.getID(),resultModel.getID(),personResultModel.get(0).getID(),true);
        }
        catch(DataAccessException | SQLException e){
            e.printStackTrace();
            try {
                dbConnection.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            return new Result.Login("Internal server error",false);
        }
    }

}
