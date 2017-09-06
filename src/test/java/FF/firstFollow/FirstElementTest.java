package FF.firstFollow;

import org.junit.Assert;

/**
 * Created on 26.04.2017.
 */
public class FirstElementTest {

    @org.junit.Test
    public void testFirstElement() {
        FirstElement testElement = new FirstElement("test");
        String testString = "test";

        Assert.assertTrue(testElement.value.equals(testString));
        Assert.assertTrue(testElement.isTerminal.equals(true));
        Assert.assertTrue(testElement.firstSet.size() == 1);
        Assert.assertTrue(testElement.firstSet.contains(testString));
    }

}