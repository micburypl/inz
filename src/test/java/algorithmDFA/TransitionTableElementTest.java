package algorithmDFA;


import org.junit.Assert;

import java.util.HashSet;

/**
 * Created on 26.04.2017.
 */
public class TransitionTableElementTest {
    @org.junit.Test
    public void testTransitionTableElement() {
        String testBeginState = "TestState";
        String testWord = "TestWord";
        String testEndState = "TestState";
        HashSet testSet = new HashSet();
        Integer testInteger = 100;
        testSet.add(testInteger);

        TransitionTableElement testTTE = new TransitionTableElement(testBeginState, testWord, testSet, testEndState);

        Assert.assertTrue(testTTE.beginState.equals(testBeginState));
        Assert.assertTrue(testTTE.word.equals(testWord));
        Assert.assertTrue(testTTE.numberSet.equals(testSet));
        Assert.assertTrue(testTTE.endState.equals(testEndState));

    }
}