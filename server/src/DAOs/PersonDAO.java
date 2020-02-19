package DAOs;

import Models.Model;
import Services.Database;

/**
 *
 */
public class PersonDAO extends DAO{
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
