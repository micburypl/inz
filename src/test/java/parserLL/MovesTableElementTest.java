package parserLL;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created on 26.04.2017.
 */
public class MovesTableElementTest {
    @Test
    public void testMovesTableElement() throws Exception {
        ArrayList<String> testStack = new ArrayList<>();
        ArrayList<String> testInput = new ArrayList<>();
        ArrayList<String> testOutputRight = new ArrayList<>();
        String testString1 = "test1";
        String testString2 = "test2";
        String testString3 = "test3";
        String testString4 = "test4";
        testStack.add(testString1);
        testInput.add(testString2);
        testOutputRight.add(testString3);

        MovesTableElement testMTE = new MovesTableElement(testStack, testInput, testString4, testOutputRight);
        Assert.assertTrue(testMTE.stack.equals("$" + testString1));
        Assert.assertTrue(testMTE.input.equals(testString2 + "$"));
        Assert.assertTrue(testMTE.output.equals(testString4 + " -> " + testString3));
    }

}