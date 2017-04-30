package parserLR;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created on 26.04.2017.
 */
public class GotoTransitionTest {
    @Test
    public void testGotoTransition() throws Exception {
        Integer f = 10;
        Integer t = 12;
        String v = "Test";

        GotoTransition testGT = new GotoTransition(f,t,v);
        Assert.assertTrue(testGT.value.equals(v));
        Assert.assertTrue(testGT.from == f);
        Assert.assertTrue(testGT.to == t);
    }
}