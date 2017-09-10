package FF.firstFollow;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 26.04.2017.
 */
public class FirstFollowTest {
    @Test
    public void testGenerateSolutionSet() throws Exception {
        List<String> inputLineList = new ArrayList<>();
        inputLineList.add("S -> A S'");
        inputLineList.add("S' -> + A S' | eps");
        inputLineList.add("A -> B A' ");
        inputLineList.add("A' -> * B A' | eps");
        inputLineList.add("B -> ( S ) | a");

        FirstFollow testFirstFollow = new FirstFollow(inputLineList);
        testFirstFollow.generateSolutionSet();

        //FIRST

        Assert.assertTrue(!testFirstFollow.firstElementMap.get("A").isTerminal);
        Assert.assertTrue(!testFirstFollow.firstElementMap.get("A").isEpsilonTransition);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("A").firstSet.size() == 2);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("A").firstSet.contains("a"));
        Assert.assertTrue(testFirstFollow.firstElementMap.get("A").firstSet.contains("("));

        Assert.assertTrue(testFirstFollow.firstElementMap.get("a").isTerminal);
        Assert.assertTrue(!testFirstFollow.firstElementMap.get("a").isEpsilonTransition);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("a").firstSet.size() == 1);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("a").firstSet.contains("a"));

        Assert.assertTrue(!testFirstFollow.firstElementMap.get("B").isTerminal);
        Assert.assertTrue(!testFirstFollow.firstElementMap.get("B").isEpsilonTransition);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("B").firstSet.size() == 2);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("B").firstSet.contains("a"));
        Assert.assertTrue(testFirstFollow.firstElementMap.get("B").firstSet.contains("("));

        Assert.assertTrue(!testFirstFollow.firstElementMap.get("S").isTerminal);
        Assert.assertTrue(!testFirstFollow.firstElementMap.get("S").isEpsilonTransition);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("S").firstSet.size() == 2);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("S").firstSet.contains("a"));
        Assert.assertTrue(testFirstFollow.firstElementMap.get("S").firstSet.contains("("));

        Assert.assertTrue(!testFirstFollow.firstElementMap.get("S'").isTerminal);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("S'").isEpsilonTransition);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("S'").firstSet.size() == 2);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("S'").firstSet.contains("+"));
        Assert.assertTrue(testFirstFollow.firstElementMap.get("S'").firstSet.contains("eps"));

        Assert.assertTrue(!testFirstFollow.firstElementMap.get("A'").isTerminal);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("A'").isEpsilonTransition);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("A'").firstSet.size() == 2);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("A'").firstSet.contains("eps"));
        Assert.assertTrue(testFirstFollow.firstElementMap.get("A'").firstSet.contains("*"));

        Assert.assertTrue(testFirstFollow.firstElementMap.get("(").isTerminal);
        Assert.assertTrue(!testFirstFollow.firstElementMap.get("(").isEpsilonTransition);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("(").firstSet.size() == 1);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("(").firstSet.contains("("));

        Assert.assertTrue(testFirstFollow.firstElementMap.get(")").isTerminal);
        Assert.assertTrue(!testFirstFollow.firstElementMap.get(")").isEpsilonTransition);
        Assert.assertTrue(testFirstFollow.firstElementMap.get(")").firstSet.size() == 1);
        Assert.assertTrue(testFirstFollow.firstElementMap.get(")").firstSet.contains(")"));

        Assert.assertTrue(testFirstFollow.firstElementMap.get("*").isTerminal);
        Assert.assertTrue(!testFirstFollow.firstElementMap.get("*").isEpsilonTransition);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("*").firstSet.size() == 1);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("*").firstSet.contains("*"));

        Assert.assertTrue(testFirstFollow.firstElementMap.get("+").isTerminal);
        Assert.assertTrue(!testFirstFollow.firstElementMap.get("+").isEpsilonTransition);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("+").firstSet.size() == 1);
        Assert.assertTrue(testFirstFollow.firstElementMap.get("+").firstSet.contains("+"));

        //Follow set

        Assert.assertTrue(testFirstFollow.followElementMap.size() == 5);

        Assert.assertTrue(testFirstFollow.followElementMap.get("A").followSet.size() == 3);
        Assert.assertTrue(testFirstFollow.followElementMap.get("A").followSet.contains("eps"));
        Assert.assertTrue(testFirstFollow.followElementMap.get("A").followSet.contains(")"));
        Assert.assertTrue(testFirstFollow.followElementMap.get("A").followSet.contains("+"));

        Assert.assertTrue(testFirstFollow.followElementMap.get("B").followSet.size() == 4);
        Assert.assertTrue(testFirstFollow.followElementMap.get("B").followSet.contains("eps"));
        Assert.assertTrue(testFirstFollow.followElementMap.get("B").followSet.contains(")"));
        Assert.assertTrue(testFirstFollow.followElementMap.get("B").followSet.contains("+"));
        Assert.assertTrue(testFirstFollow.followElementMap.get("B").followSet.contains("*"));

        Assert.assertTrue(testFirstFollow.followElementMap.get("S").followSet.size() == 2);
        Assert.assertTrue(testFirstFollow.followElementMap.get("S").followSet.contains("eps"));
        Assert.assertTrue(testFirstFollow.followElementMap.get("S").followSet.contains(")"));

        Assert.assertTrue(testFirstFollow.followElementMap.get("S'").followSet.size() == 2);
        Assert.assertTrue(testFirstFollow.followElementMap.get("S'").followSet.contains("eps"));
        Assert.assertTrue(testFirstFollow.followElementMap.get("S'").followSet.contains(")"));

        Assert.assertTrue(testFirstFollow.followElementMap.get("A'").followSet.size() == 3);
        Assert.assertTrue(testFirstFollow.followElementMap.get("A'").followSet.contains("eps"));
        Assert.assertTrue(testFirstFollow.followElementMap.get("A'").followSet.contains(")"));
        Assert.assertTrue(testFirstFollow.followElementMap.get("A'").followSet.contains("+"));



    }

}