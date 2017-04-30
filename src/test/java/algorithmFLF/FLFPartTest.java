package algorithmFLF;

import org.junit.Assert;

import static org.junit.Assert.*;

/**
 * Created on 26.04.2017.
 */
public class FLFPartTest {
    @org.junit.Test
    public void testFLFPart() {
        FLFPart testFLFPart = new FLFPart();
        Assert.assertTrue(testFLFPart.leftChild == null);
        Assert.assertTrue(testFLFPart.rightChild == null);
        Assert.assertTrue(testFLFPart.singleChild == null);
        Assert.assertTrue(testFLFPart.parentPointer == null);
        Assert.assertTrue(testFLFPart.firstList.size() == 0);
        Assert.assertTrue(testFLFPart.lastList.size() == 0);
    }
}