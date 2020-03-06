package Data;

import java.util.ArrayList;
import java.util.Random;

public class Locations {
    public ArrayList<Location> data;
    public Location getRandom(){
        Random random = new Random();
        return data.get(random.nextInt(data.size()));
    }
}
