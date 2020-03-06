package Data;

import java.util.ArrayList;
import java.util.Random;

public class sNames {
    public ArrayList<String> data;
    public String getRandom(){
        Random random = new Random();
        return data.get(random.nextInt(data.size()));
    }
}
