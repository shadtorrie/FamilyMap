package Models;

public class Model {
    private String ID;
    public Model(String ID){
        this.ID=ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
