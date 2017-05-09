package firstLastFollowWindow;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created on 09.05.2017.
 */
public class FirstLastFollowTestSet {

    ArrayList<String> dataSets = new ArrayList<>();



    FirstLastFollowTestSet() {
        dataSets = new ArrayList<>();
        //add data
        addElement("(a|b)*&a&b&b");
        addElement("(a|b)*");
        addElement("(a|b)&(c|d)");

    }

    void addElement (String newInput) {
        dataSets.add(newInput);
    }

    String testData() {
        Integer i;
        Random generator = new Random();

        i = generator.nextInt(dataSets.size());

        return dataSets.get(i);
    }

}
