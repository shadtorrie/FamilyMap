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
            User resultModel = (ModelsServer.User) ((UserDAO)dao).findByUsernameAndPassword(loginRequest.getUserName(),loginRequest.getPassword());
            if(resultModel==null){
                dbConnection.closeConnection(false);
                return new Result.Login("Error: Request property missing or has invalid value",false);
            }
            dao = new AuthDAO(dbConnection);
            Auth authModel = (Auth) dao.insert(new Auth(UUID.randomUUID().toString(),resultModel.getID()));
            dbConnection.closeConnection(true);
            return new Result.Login(authModel.getID(),resultModel.getID(),resultModel.getPersonID(),true);
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
