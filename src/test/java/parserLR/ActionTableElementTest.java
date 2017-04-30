package parserLR;

import org.junit.Assert;

import static org.junit.Assert.*;

/**
 * Created on 26.04.2017.
 */
public class ActionTableElementTest {
    @org.junit.Test
    public void testActionTableElement() {
        Integer v = 10;
        Boolean iS = true;
        ActionTableElement testATE = new ActionTableElement(v, iS);

        Assert.assertTrue(testATE.isShift == true);
        Assert.assertTrue(testATE.value == 10);


    }
}