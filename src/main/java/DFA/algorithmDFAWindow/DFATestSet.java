package DFA.algorithmDFAWindow;

import java.util.ArrayList;
import java.util.Random;


public class DFATestSet {

    ArrayList<String> dataSets = new ArrayList<>();



    DFATestSet() {
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
