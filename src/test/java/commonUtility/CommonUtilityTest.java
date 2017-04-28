package commonUtility;

import org.junit.Assert;

import java.util.ArrayList;

/**
 * Created on 25.04.2017.
 */
public class CommonUtilityTest {

    @org.junit.Test
    public void testIsTerminal() {
        String testString = "Test";
        String testString2 = "test";
        Assert.assertTrue(!CommonUtility.isTerminal(testString));
        Assert.assertTrue(CommonUtility.isTerminal(testString2));
    }

    @org.junit.Test
    public void testIsNonTerminal() {
        String testString = "Test";
        String testString2 = "test";
        Assert.assertTrue(CommonUtility.isNonTerminal(testString));
        Assert.assertTrue(!CommonUtility.isNonTerminal(testString2));
    }

    @org.junit.Test
    public void testParsedInputBySpace() {
        String testString = "T e s t";

        ArrayList testList = new ArrayList();
        testList.add("T");
        testList.add("e");
        testList.add("s");
        testList.add("t");
        Assert.assertTrue(CommonUtility.parsedInputBySpace(testString).equals(testList));

    }


    @org.junit.Test
    public void testGenerateList() {

        ArrayList testList = new ArrayList();
        testList.add("T");
        testList.add("e");
        testList.add("s");
        testList.add("t");

        String testString2 = "T";
        String testString3 = "e";
        String testString4 = "s";
        String testString5 = "t";

        // I
        Assert.assertTrue(CommonUtility.generateList(testString2, testString3, testString4, testString5).equals(testList));

        // II
        ArrayList testList2 = new ArrayList();
        testList2.add(testString4);
        testList2.add(testString5);

        Assert.assertTrue(CommonUtility.generateList(testString2, testString3, testList2).equals(testList));

    }
}
