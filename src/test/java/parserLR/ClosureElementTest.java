package parserLR;
import commonUtility.CommonUtility;
import org.junit.Assert;

import java.util.ArrayList;
/**
 * Created on 25.04.2017.
 */
public class ClosureElementTest {
    @org.junit.Test public void testClosureElement() {
        Integer testCounter = 10;
        ArrayList<String> testInputList = new ArrayList<>();
        Integer testProdNumber = 12;
        String testString = "Test";
        String testString2 = CommonUtility.dot;
        testInputList.add(testString);
        testInputList.add(testString2);
        ClosureElement testCE = new ClosureElement(testCounter, testInputList, testProdNumber);

        Assert.assertTrue(testCE.production.size() == 2);
        Assert.assertTrue(testCE.production.get(0).equals(testString));
        Assert.assertTrue(testCE.production.get(1).equals(testString2));
        Assert.assertTrue(testCE.counterValue == testCounter);
        Assert.assertTrue(testCE.isLastItem == true);
        Assert.assertTrue(testCE.producitonNumber == testProdNumber);


    }

}
