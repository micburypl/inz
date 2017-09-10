package LR.parserLR;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 26.04.2017.
 */
public class ParserLRTest {
    @Test
    public void generateLRSolution() throws Exception {
        List<String> inputLineList = new ArrayList<>();
        inputLineList.add("S -> A");
        inputLineList.add("S -> S + A");
        inputLineList.add("A -> B ");
        inputLineList.add("A -> A * B");
        inputLineList.add("B -> a | ( S )");

        ParserLR testGotoGenerator = new ParserLR(inputLineList);

        //gotoGeneratorMap
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.size() == 7);
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(1).size() == 3);
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(1).get(0).equals("A"));
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(1).get(2).equals("B"));

        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(2).size() == 5);
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(2).get(0).equals("A"));
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(2).get(2).equals("A"));
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(2).get(3).equals("*"));
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(2).get(4).equals("B"));

        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(3).size() == 3);
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(3).get(0).equals("B"));
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(3).get(2).equals("a"));

        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(4).size() == 5);
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(4).get(0).equals("B"));
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(4).get(2).equals("("));
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(4).get(3).equals("S"));
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(4).get(4).equals(")"));

        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(5).size() == 3);
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(5).get(0).equals("S"));
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(5).get(2).equals("A"));

        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(6).size() == 5);
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(6).get(0).equals("S"));
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(6).get(2).equals("S"));
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(6).get(3).equals("+"));
        Assert.assertTrue(testGotoGenerator.gotoGeneratorMap.listOfProduction.get(6).get(4).equals("A"));

        //actionTable
        Assert.assertTrue(testGotoGenerator.actionTable.size() == 12);
        Assert.assertTrue(testGotoGenerator.actionTable.get(0).size() == 2);
        Assert.assertTrue(testGotoGenerator.actionTable.get(0).get("a").value == 2);
        Assert.assertTrue(testGotoGenerator.actionTable.get(0).get("a").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(0).get("(").value == 5);
        Assert.assertTrue(testGotoGenerator.actionTable.get(0).get("(").isShift);

        Assert.assertTrue(testGotoGenerator.actionTable.get(1).size() == 4);
        Assert.assertTrue(testGotoGenerator.actionTable.get(1).get("eps").value == 5);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(1).get("eps").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(1).get(")").value == 5);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(1).get(")").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(1).get("*").value == 6);
        Assert.assertTrue(testGotoGenerator.actionTable.get(1).get("*").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(1).get("+").value == 5);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(1).get("+").isShift);

        Assert.assertTrue(testGotoGenerator.actionTable.get(2).size() == 4);
        Assert.assertTrue(testGotoGenerator.actionTable.get(2).get("eps").value == 3);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(2).get("eps").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(2).get(")").value == 3);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(2).get(")").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(2).get("*").value == 3);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(2).get("*").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(2).get("+").value == 3);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(2).get("+").isShift);

        Assert.assertTrue(testGotoGenerator.actionTable.get(3).size() == 4);
        Assert.assertTrue(testGotoGenerator.actionTable.get(3).get("eps").value == 1);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(3).get("eps").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(3).get(")").value == 1);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(3).get(")").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(3).get("*").value == 1);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(3).get("*").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(3).get("+").value == 1);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(3).get("+").isShift);

        Assert.assertTrue(testGotoGenerator.actionTable.get(4).size() == 2);
        Assert.assertTrue(testGotoGenerator.actionTable.get(4).get("eps").value == -1);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(4).get("eps").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(4).get("+").value == 7);
        Assert.assertTrue(testGotoGenerator.actionTable.get(4).get("+").isShift);

        Assert.assertTrue(testGotoGenerator.actionTable.get(5).size() == 2);
        Assert.assertTrue(testGotoGenerator.actionTable.get(5).get("a").value == 2);
        Assert.assertTrue(testGotoGenerator.actionTable.get(5).get("a").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(5).get("(").value == 5);
        Assert.assertTrue(testGotoGenerator.actionTable.get(5).get("(").isShift);

        Assert.assertTrue(testGotoGenerator.actionTable.get(6).size() == 2);
        Assert.assertTrue(testGotoGenerator.actionTable.get(6).get("a").value == 2);
        Assert.assertTrue(testGotoGenerator.actionTable.get(6).get("a").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(6).get("(").value == 5);
        Assert.assertTrue(testGotoGenerator.actionTable.get(6).get("(").isShift);

        Assert.assertTrue(testGotoGenerator.actionTable.get(7).size() == 2);
        Assert.assertTrue(testGotoGenerator.actionTable.get(7).get("a").value == 2);
        Assert.assertTrue(testGotoGenerator.actionTable.get(7).get("a").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(7).get("(").value == 5);
        Assert.assertTrue(testGotoGenerator.actionTable.get(7).get("(").isShift);

        Assert.assertTrue(testGotoGenerator.actionTable.get(8).size() == 2);
        Assert.assertTrue(testGotoGenerator.actionTable.get(8).get(")").value == 11);
        Assert.assertTrue(testGotoGenerator.actionTable.get(8).get(")").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(8).get("+").value == 7);
        Assert.assertTrue(testGotoGenerator.actionTable.get(8).get("+").isShift);

        Assert.assertTrue(testGotoGenerator.actionTable.get(9).size() == 4);
        Assert.assertTrue(testGotoGenerator.actionTable.get(9).get("eps").value == 2);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(9).get("eps").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(9).get(")").value == 2);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(9).get(")").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(9).get("*").value == 2);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(9).get("*").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(9).get("+").value == 2);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(9).get("+").isShift);

        Assert.assertTrue(testGotoGenerator.actionTable.get(10).size() == 4);
        Assert.assertTrue(testGotoGenerator.actionTable.get(10).get("eps").value == 6);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(10).get("eps").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(10).get(")").value == 6);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(10).get(")").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(10).get("*").value == 6);
        Assert.assertTrue(testGotoGenerator.actionTable.get(10).get("*").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(10).get("+").value == 6);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(10).get("+").isShift);

        Assert.assertTrue(testGotoGenerator.actionTable.get(11).size() == 4);
        Assert.assertTrue(testGotoGenerator.actionTable.get(11).get("eps").value == 4);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(11).get("eps").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(11).get(")").value == 4);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(11).get(")").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(11).get("*").value == 4);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(11).get("*").isShift);
        Assert.assertTrue(testGotoGenerator.actionTable.get(11).get("+").value == 4);
        Assert.assertTrue(!testGotoGenerator.actionTable.get(11).get("+").isShift);

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

    @Test
    public void testGenerateLRParser() throws Exception {
        List<String> inputLineList = new ArrayList<>();
        inputLineList.add("S -> A");
        inputLineList.add("S -> S + A");
        inputLineList.add("A -> B ");
        inputLineList.add("A -> A * B");
        inputLineList.add("B -> a | ( S )");

        ParserLR testGotoGenerator = new ParserLR(inputLineList);
        testGotoGenerator.generateLRParser("a * a + a");

        Assert.assertTrue(testGotoGenerator.correctExit == true);
        Assert.assertTrue(testGotoGenerator.movesList.size() == 15);
        Assert.assertTrue(testGotoGenerator.movesList.get(0).input.size() == 6);
        Assert.assertTrue(testGotoGenerator.movesList.get(0).stack.size() == 1);

        Assert.assertTrue(testGotoGenerator.movesList.get(1).input.size() == 5);
        Assert.assertTrue(testGotoGenerator.movesList.get(1).stack.size() == 3);

        Assert.assertTrue(testGotoGenerator.movesList.get(2).input.size() == 5);
        Assert.assertTrue(testGotoGenerator.movesList.get(2).stack.size() == 3);

        Assert.assertTrue(testGotoGenerator.movesList.get(3).input.size() == 5);
        Assert.assertTrue(testGotoGenerator.movesList.get(3).stack.size() == 3);

        Assert.assertTrue(testGotoGenerator.movesList.get(4).input.size() == 4);
        Assert.assertTrue(testGotoGenerator.movesList.get(4).stack.size() == 5);

        Assert.assertTrue(testGotoGenerator.movesList.get(5).input.size() == 3);
        Assert.assertTrue(testGotoGenerator.movesList.get(5).stack.size() == 7);

        Assert.assertTrue(testGotoGenerator.movesList.get(6).input.size() == 3);
        Assert.assertTrue(testGotoGenerator.movesList.get(6).stack.size() == 7);

        Assert.assertTrue(testGotoGenerator.movesList.get(7).input.size() == 3);
        Assert.assertTrue(testGotoGenerator.movesList.get(7).stack.size() == 3);

        Assert.assertTrue(testGotoGenerator.movesList.get(8).input.size() == 3);
        Assert.assertTrue(testGotoGenerator.movesList.get(8).stack.size() == 3);

        Assert.assertTrue(testGotoGenerator.movesList.get(9).input.size() == 2);
        Assert.assertTrue(testGotoGenerator.movesList.get(9).stack.size() == 5);

        Assert.assertTrue(testGotoGenerator.movesList.get(10).input.size() == 1);
        Assert.assertTrue(testGotoGenerator.movesList.get(10).stack.size() == 7);

        Assert.assertTrue(testGotoGenerator.movesList.get(11).input.size() == 1);
        Assert.assertTrue(testGotoGenerator.movesList.get(11).stack.size() == 7);

        Assert.assertTrue(testGotoGenerator.movesList.get(12).input.size() == 1);
        Assert.assertTrue(testGotoGenerator.movesList.get(12).stack.size() == 7);

        Assert.assertTrue(testGotoGenerator.movesList.get(13).input.size() == 1);
        Assert.assertTrue(testGotoGenerator.movesList.get(13).stack.size() == 3);

    }

}