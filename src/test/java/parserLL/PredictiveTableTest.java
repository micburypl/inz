package parserLL;

import firstFollow.FirstFollow;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created on 26.04.2017.
 */
public class PredictiveTableTest {
    @Test
    public void MovesElementLR() throws Exception {
        List<String> inputLineList = new ArrayList<>();
        inputLineList.add("S -> A S'");
        inputLineList.add("S' -> + A S' | eps");
        inputLineList.add("A -> B A' ");
        inputLineList.add("A' -> * B A' | eps");
        inputLineList.add("B -> ( S ) | a");
        FirstFollow testFirstFollow = new FirstFollow(inputLineList);
        testFirstFollow.generateSolutionSet();
        testFirstFollow.predictiveMap.generatePredictiveMap(testFirstFollow.parsedSet, testFirstFollow.firstElementMap, testFirstFollow.followElementMap);

        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.size() == 5);

        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.containsKey("A"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A").size(), 2);
        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.get("A").containsKey("a"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A").get("a").size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A").get("a").get(0).size(), 2);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A").get("a").get(0).get(0), "B");
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A").get("a").get(0).get(1), "A'");
        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.get("A").containsKey("("));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A").get("(").size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A").get("(").get(0).size(), 2);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A").get("(").get(0).get(0), "B");
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A").get("(").get(0).get(1), "A'");

        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.containsKey("B"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("B").size(), 2);
        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.get("B").containsKey("a"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("B").get("a").size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("B").get("a").get(0).size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("B").get("a").get(0).get(0), "a");
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("B").get("(").size(), 1);
        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.get("B").containsKey("("));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("B").get("(").size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("B").get("(").get(0).size(), 3);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("B").get("(").get(0).get(0), "(");
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("B").get("(").get(0).get(1), "S");
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("B").get("(").get(0).get(2), ")");

        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.containsKey("S"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S").size(), 2);
        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.get("S").containsKey("a"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S").get("a").size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S").get("a").get(0).size(), 2);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S").get("a").get(0).get(0), "A");
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S").get("a").get(0).get(1), "S'");
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S").get("(").size(), 1);
        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.get("S").containsKey("("));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S").get("(").size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S").get("(").get(0).size(), 2);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S").get("(").get(0).get(0), "A");
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S").get("(").get(0).get(1), "S'");

        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.containsKey("S'"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S'").size(), 3);
        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.get("S'").containsKey("eps"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S'").get("eps").size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S'").get("eps").get(0).size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S'").get("eps").get(0).get(0), "eps");
        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.get("S'").containsKey(")"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S'").get(")").size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S'").get(")").get(0).size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S'").get(")").get(0).get(0), "eps");
        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.get("S'").containsKey("+"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S'").get("+").size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S'").get("+").get(0).size(), 3);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S'").get("+").get(0).get(0), "+");
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S'").get("+").get(0).get(1), "A");
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("S'").get("+").get(0).get(2), "S'");

        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.containsKey("A'"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").size(), 4);
        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.get("A'").containsKey("eps"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get("eps").size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get("eps").get(0).size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get("eps").get(0).get(0), "eps");

        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.get("A'").containsKey(")"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get(")").size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get(")").get(0).size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get(")").get(0).get(0), "eps");

        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.get("A'").containsKey("*"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get("*").size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get("*").get(0).size(), 3);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get("*").get(0).get(0), "*");
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get("*").get(0).get(1), "B");
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get("*").get(0).get(2), "A'");

        Assert.assertTrue(testFirstFollow.predictiveMap.predictiveMap.get("A'").containsKey("+"));
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get("+").size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get("+").get(0).size(), 1);
        Assert.assertEquals(testFirstFollow.predictiveMap.predictiveMap.get("A'").get("+").get(0).get(0), "eps");



    }

}