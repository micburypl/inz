package FF.firstFollow.firstFollowTestData;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created on 09.05.2017.
 */
public class FirstFollowTestSet {

    public ArrayList<FirstFollowTestMainSet> InputListTestList;

    FirstFollowTestMainSet tempElement;

    public FirstFollowTestSet(Boolean isLR) {
        if(isLR) {
            InputListTestList = new ArrayList<>();
            //1
            tempElement = new FirstFollowTestMainSet();
            //input
            tempElement.addInputElement("S "," A");
            tempElement.addInputElement("S "," S + A");
            tempElement.addInputElement("A "," B ");
            tempElement.addInputElement("A "," A * B");
            tempElement.addInputElement("B "," a | ( S )");
            //moves
            tempElement.addMovesElement("a + a");
            tempElement.addMovesElement("a * a");
            tempElement.addMovesElement("a + a * a");
            tempElement.addMovesElement("a * a + a");
            tempElement.addMovesElement("a * a + ");

            InputListTestList.add(tempElement);

            //2
            tempElement = new FirstFollowTestMainSet();
            //input
            tempElement.addInputElement("S","A");
            tempElement.addInputElement("A","B");
            tempElement.addInputElement("B","a | b");
            //moves
            tempElement.addMovesElement("a");
            tempElement.addMovesElement("b");
            tempElement.addMovesElement("c");

            InputListTestList.add(tempElement);
        } else {
            InputListTestList = new ArrayList<>();
            //1
            tempElement = new FirstFollowTestMainSet();
            //input
            tempElement.addInputElement("S ","A S'");
            tempElement.addInputElement("S' ","+ A S' | eps");
            tempElement.addInputElement("A "," B A' ");
            tempElement.addInputElement("A' ","* B A' | eps");
            tempElement.addInputElement("B "," ( S ) | a");


            //moves
            tempElement.addMovesElement("a + a");
            tempElement.addMovesElement("a * a");
            tempElement.addMovesElement("a + a * a");
            tempElement.addMovesElement("a * a + a");
            tempElement.addMovesElement("a * a + ");

            InputListTestList.add(tempElement);

            //2
            tempElement = new FirstFollowTestMainSet();
            //input
            tempElement.addInputElement("S","A");
            tempElement.addInputElement("A","B");
            tempElement.addInputElement("B","b");
            tempElement.addInputElement("B","a");
            //moves
            tempElement.addMovesElement("a");
            tempElement.addMovesElement("b");
            tempElement.addMovesElement("c");

            InputListTestList.add(tempElement);

        }


    }


    public Integer testData(Integer lastValue) {
        Integer i;
        Random generator = new Random();

        do{
            i = generator.nextInt(InputListTestList.size());
        } while(lastValue == i);


        return i;
    }



}
