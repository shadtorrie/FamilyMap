package Services;

import Request.Requests;
import Result.Results;

public abstract class Service {
    private Database dbConnection;
    public Service() {
        this.dbConnection = new Database();
        dbConnection.getConnection();
    }

    public abstract Results requestService(Requests request);
}
