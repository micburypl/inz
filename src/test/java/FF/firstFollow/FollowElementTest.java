package FF.firstFollow;

import org.junit.Assert;

/**
 * Created on 26.04.2017.
 */
public class FollowElementTest {
    @org.junit.Test
    public void testFollowElement() {
        FollowElement testElement = new FollowElement("test");
        String testString = "test";

        Assert.assertTrue(testElement.value.equals(testString));
        Assert.assertTrue(testElement.followSet.size() == 0);








    }
}