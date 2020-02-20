package DAOs;

import Models.Model;
import Services.DataAccessException;
import Services.Database;

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
    public Model insert(Model insertModel) {
        return null;
    }

    /**
     *
     * @param searchString
     * @return
     */
    @Override
    public Model find(String searchString) {
        return null;
    }

    @Override
    public ArrayList<Model> find() throws DataAccessException {
        return null;
    }

    /**
     *
     */
    @Override
    public void clear() {

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
