package Data;

import java.util.ArrayList;
import java.util.Random;

public class mNames extends FirstName {
    public ArrayList<String> data;
    @Override
    public String getRandom(){
        Random random = new Random();
        return data.get(random.nextInt(data.size()));
    }
}
