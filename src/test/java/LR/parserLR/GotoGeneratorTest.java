package LR.parserLR;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 26.04.2017.
 */
public class GotoGeneratorTest {
    @Test
    public void TestGenerateGoto() throws Exception {
        List<String> inputLineList = new ArrayList<>();
        inputLineList.add("S -> A");
        inputLineList.add("S -> S + A");
        inputLineList.add("A -> B ");
        inputLineList.add("A -> A * B");
        inputLineList.add("B -> a | ( S )");

        ParserLR testGotoGenerator = new ParserLR(inputLineList);
        //gotoTable
        Assert.assertTrue(testGotoGenerator.gotoTable.size() == 4);
        Assert.assertTrue(testGotoGenerator.gotoTable.get(0).size() == 3);
        Assert.assertTrue(testGotoGenerator.gotoTable.get(0).containsKey("A"));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(0).get("A").equals(1));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(0).containsKey("B"));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(0).get("B").equals(3));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(0).containsKey("S"));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(0).get("S").equals(4));

        Assert.assertTrue(testGotoGenerator.gotoTable.get(5).size() == 3);
        Assert.assertTrue(testGotoGenerator.gotoTable.get(5).containsKey("A"));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(5).get("A").equals(1));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(5).containsKey("B"));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(5).get("B").equals(3));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(5).containsKey("S"));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(5).get("S").equals(8));

        Assert.assertTrue(testGotoGenerator.gotoTable.get(6).size() == 1);
        Assert.assertTrue(testGotoGenerator.gotoTable.get(6).containsKey("B"));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(6).get("B").equals(9));

        Assert.assertTrue(testGotoGenerator.gotoTable.get(7).size() == 2);
        Assert.assertTrue(testGotoGenerator.gotoTable.get(7).containsKey("A"));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(7).get("A").equals(10));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(7).containsKey("B"));
        Assert.assertTrue(testGotoGenerator.gotoTable.get(7).get("B").equals(3));
    }

}