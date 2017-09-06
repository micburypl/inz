package LR.parserLR;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

/**
 * Created on 26.04.2017.
 */
public class GotoElementTest {
    @Test
    public void testGotoElement() throws Exception {
        HashSet<Integer> testSet = new HashSet<>();
        testSet.add(1);
        GotoElement testGTE = new GotoElement();
        testGTE.closureElementList.addAll(testSet);

        Assert.assertTrue(testGTE.closureElementList.size() == 1);
        Assert.assertTrue(testGTE.closureElementList.contains(1));
    }

}