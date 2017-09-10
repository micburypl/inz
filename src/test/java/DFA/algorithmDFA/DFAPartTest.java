package DFA.algorithmDFA;

import org.junit.Assert;

/**
 * Created on 26.04.2017.
 */
public class DFAPartTest {
    @org.junit.Test
    public void testFLFPart() {
        DFAPart testFLFPart = new DFAPart();
        Assert.assertTrue(testFLFPart.leftChild == null);
        Assert.assertTrue(testFLFPart.rightChild == null);
        Assert.assertTrue(testFLFPart.singleChild == null);
        Assert.assertTrue(testFLFPart.parentPointer == null);
        Assert.assertTrue(testFLFPart.firstList.size() == 0);
        Assert.assertTrue(testFLFPart.lastList.size() == 0);
    }
}