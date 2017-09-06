package LL.parserLL;

import FF.firstFollow.FirstFollow;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 28.04.2017.
 */
public class MovesTableTest {
    @Test
    public void testGenerateMovesTable() throws Exception {
        List<String> inputLineList = new ArrayList<>();
        inputLineList.add("S -> A S'");
        inputLineList.add("S' -> + A S' | eps");
        inputLineList.add("A -> B A' ");
        inputLineList.add("A' -> * B A' | eps");
        inputLineList.add("B -> ( S ) | a");
        FirstFollow testFirstFollow = new FirstFollow(inputLineList);
        testFirstFollow.generateSolutionSet();
        testFirstFollow.predictiveMap.generatePredictiveMap(testFirstFollow.parsedSet, testFirstFollow.firstElementMap, testFirstFollow.followElementMap);

        MovesTable testMoveTable = new MovesTable();
        testMoveTable.generateMovesTable("S","a + a * a", testFirstFollow.predictiveMap.predictiveMap);

        Assert.assertTrue(testMoveTable.movesList.size() == 17);

        Assert.assertTrue(testMoveTable.movesList.get(0).stack.equals("$S"));

        Assert.assertTrue(testMoveTable.movesList.get(0).input.equals("a+a*a$"));
        Assert.assertTrue(testMoveTable.movesList.get(0).output.equals("S -> AS'"));

        Assert.assertTrue(testMoveTable.movesList.get(1).stack.equals("$S'A"));
        Assert.assertTrue(testMoveTable.movesList.get(1).input.equals("a+a*a$"));
        Assert.assertTrue(testMoveTable.movesList.get(1).output.equals("A -> BA'"));

        Assert.assertTrue(testMoveTable.movesList.get(2).stack.equals("$S'A'B"));
        Assert.assertTrue(testMoveTable.movesList.get(2).input.equals("a+a*a$"));
        Assert.assertTrue(testMoveTable.movesList.get(2).output.equals("B -> a"));

        Assert.assertTrue(testMoveTable.movesList.get(3).stack.equals("$S'A'a"));
        Assert.assertTrue(testMoveTable.movesList.get(3).input.equals("a+a*a$"));
        Assert.assertTrue(testMoveTable.movesList.get(3).output.equals(""));

        Assert.assertTrue(testMoveTable.movesList.get(4).stack.equals("$S'A'"));
        Assert.assertTrue(testMoveTable.movesList.get(4).input.equals("+a*a$"));
        Assert.assertTrue(testMoveTable.movesList.get(4).output.equals("A' -> eps"));

        Assert.assertTrue(testMoveTable.movesList.get(5).stack.equals("$S'"));
        Assert.assertTrue(testMoveTable.movesList.get(5).input.equals("+a*a$"));
        Assert.assertTrue(testMoveTable.movesList.get(5).output.equals("S' -> +AS'"));

        Assert.assertTrue(testMoveTable.movesList.get(6).stack.equals("$S'A+"));
        Assert.assertTrue(testMoveTable.movesList.get(6).input.equals("+a*a$"));
        Assert.assertTrue(testMoveTable.movesList.get(6).output.equals(""));

        Assert.assertTrue(testMoveTable.movesList.get(7).stack.equals("$S'A"));
        Assert.assertTrue(testMoveTable.movesList.get(7).input.equals("a*a$"));
        Assert.assertTrue(testMoveTable.movesList.get(7).output.equals("A -> BA'"));

        Assert.assertTrue(testMoveTable.movesList.get(8).stack.equals("$S'A'B"));
        Assert.assertTrue(testMoveTable.movesList.get(8).input.equals("a*a$"));
        Assert.assertTrue(testMoveTable.movesList.get(8).output.equals("B -> a"));

        Assert.assertTrue(testMoveTable.movesList.get(9).stack.equals("$S'A'a"));
        Assert.assertTrue(testMoveTable.movesList.get(9).input.equals("a*a$"));
        Assert.assertTrue(testMoveTable.movesList.get(9).output.equals(""));

        Assert.assertTrue(testMoveTable.movesList.get(10).stack.equals("$S'A'"));
        Assert.assertTrue(testMoveTable.movesList.get(10).input.equals("*a$"));
        Assert.assertTrue(testMoveTable.movesList.get(10).output.equals("A' -> *BA'"));

        Assert.assertTrue(testMoveTable.movesList.get(11).stack.equals("$S'A'B*"));
        Assert.assertTrue(testMoveTable.movesList.get(11).input.equals("*a$"));
        Assert.assertTrue(testMoveTable.movesList.get(11).output.equals(""));

        Assert.assertTrue(testMoveTable.movesList.get(12).stack.equals("$S'A'B"));
        Assert.assertTrue(testMoveTable.movesList.get(12).input.equals("a$"));
        Assert.assertTrue(testMoveTable.movesList.get(12).output.equals("B -> a"));

        Assert.assertTrue(testMoveTable.movesList.get(13).stack.equals("$S'A'a"));
        Assert.assertTrue(testMoveTable.movesList.get(13).input.equals("a$"));
        Assert.assertTrue(testMoveTable.movesList.get(13).output.equals(""));

        Assert.assertTrue(testMoveTable.movesList.get(14).stack.equals("$S'A'"));
        Assert.assertTrue(testMoveTable.movesList.get(14).input.equals("$"));
        Assert.assertTrue(testMoveTable.movesList.get(14).output.equals("A' -> eps"));

        Assert.assertTrue(testMoveTable.movesList.get(15).stack.equals("$S'"));
        Assert.assertTrue(testMoveTable.movesList.get(15).input.equals("$"));
        Assert.assertTrue(testMoveTable.movesList.get(15).output.equals("S' -> eps"));

        Assert.assertTrue(testMoveTable.movesList.get(16).stack.equals("$"));
        Assert.assertTrue(testMoveTable.movesList.get(16).input.equals("$"));
        Assert.assertTrue(testMoveTable.movesList.get(16).output.equals(""));
    }
}