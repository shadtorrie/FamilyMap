package Services;

import DAOs.DAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import ModelsServer.Person;
import ModelsServer.User;
import Request.Fill;
import Request.Login;
import Request.Register;
import Request.Requests;
import Result.Results;

import java.sql.SQLException;

public class RegisterS extends Service {
    /**
     *
     */
    public RegisterS() throws DataAccessException {
        super();
    }

    @Override
    public Results requestService(Requests request) {
        try{
            if(!request.getClass().equals(Register.class)){
                throw new DataAccessException();
            }
            Register registerRequest = (Register) request;
            dbConnection.openConnection();
            DAO dao = new UserDAO(dbConnection);
            if(registerRequest.getUserName().length()==0||registerRequest.getPassword().length()==0
                    ||registerRequest.getEmail().length()==0||registerRequest.getFirstName().length()==0
                    ||registerRequest.getLastName().length()==0||(Character.toLowerCase(registerRequest.getGender())!='m'&&Character.toLowerCase(registerRequest.getGender())!='f')){
                return new Result.Register("Request property missing or has invalid value",false);
            }

            User result = (ModelsServer.User) dao.find(registerRequest.getUserName());
            if(result!= null ){
                dbConnection.closeConnection(false);
                return new Result.Register("Username already taken by another user",false);
            }

            User userResultModel = (ModelsServer.User) dao.insert(new User(registerRequest.getUserName(),registerRequest.getPassword(),registerRequest.getEmail()));
            if(userResultModel==null){
                dbConnection.closeConnection(false);
                return new Result.Register("Request property missing or has invalid value",false);
            }
            dbConnection.closeConnection(true);
            Service fillService = new FillS();
            Result.Fill fillResult = (Result.Fill) fillService.requestService(new Fill(userResultModel.getID(),4,registerRequest.getGender(),registerRequest.getFirstName(),registerRequest.getLastName()));
            if(!fillResult.isSuccess()){
                return new Result.Register("Internal server error",false);
            }
            dbConnection.openConnection();
            PersonDAO personDao=new PersonDAO(dbConnection);
            Person personResultModel = (Person) personDao.find(userResultModel.getID(),registerRequest.getFirstName(),registerRequest.getLastName());
            if(personResultModel==null){
                dbConnection.closeConnection(false);
                return new Result.Register("Internal server error",false);
            }
            dbConnection.closeConnection(true);
            Service loginService = new LoginS();
            Result.Login loginResult = (Result.Login) loginService.requestService(new Login(userResultModel.getID(),userResultModel.getPassword()));
            return new Result.Register(loginResult.getAuthToken(),userResultModel.getID(),personResultModel.getID(),true);
        }
        catch(DataAccessException | SQLException e){
            e.printStackTrace();
            try {
                dbConnection.closeConnection(false);
            } catch (DataAccessException ex) {
                ex.printStackTrace();
            }
            return new Result.Register("Internal server error",false);
        }
    }
}
