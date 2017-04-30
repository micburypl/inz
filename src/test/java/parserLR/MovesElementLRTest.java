package parserLR;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created on 26.04.2017.
 */
public class MovesElementLRTest {
    @Test
    public void testMovesElementLR() throws Exception {
        Integer testInt = 10;
        ArrayList<String> testStack = new ArrayList<>();
        testStack.add("Test");
        ArrayList<String> testInput = new ArrayList<>();
        testInput.add("Test");

        MovesElementLR testMovesElementLR = new MovesElementLR(testInt, testStack, testInput);

        Assert.assertTrue(testMovesElementLR.input.size() == 1);
        Assert.assertTrue(testMovesElementLR.stack.size() == 1);
        Assert.assertTrue(testMovesElementLR.input.get(0).equals("Test"));
        Assert.assertTrue(testMovesElementLR.stack.get(0).equals("Test"));
        Assert.assertTrue(testMovesElementLR.movesNumber == 10);
    }
}