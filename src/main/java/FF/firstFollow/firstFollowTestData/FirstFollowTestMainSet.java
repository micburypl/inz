package FF.firstFollow.firstFollowTestData;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created on 09.05.2017.
 */
public class FirstFollowTestMainSet {


    public ArrayList<String> movesTestList;
    public ArrayList<FirstFollowTestSetElement> InputListTest;


    FirstFollowTestMainSet() {
        InputListTest = new ArrayList<>();
        movesTestList = new ArrayList<>();
    }

    void addInputElement(String lp, String rp){
        InputListTest.add(FirstFollowTestSetElement.generateFFTSE(lp,rp));
    }
    void addMovesElement(String movesElement){
        movesTestList.add(movesElement);
    }


    public Integer testMovesData(Integer lastValue) {
        Integer i;
        Random generator = new Random();
        do{
            i = generator.nextInt(movesTestList.size());
        } while(lastValue == i);
        return i;
    }

}
