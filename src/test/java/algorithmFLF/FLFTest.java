package algorithmFLF;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created on 26.04.2017.
 */
public class FLFTest {
    @Test
    public void createList() throws Exception {

        String inputLineList = "(a|b)*&a&b&b";
        FLF myTree = new FLF();
        myTree.inputList = myTree.createList(inputLineList);
        myTree.printList(myTree.inputList);
        myTree.rootOfTree =  myTree.createTree(myTree.inputList);
        System.out.println(myTree.rootOfTree);
        myTree.calcNullable(myTree.rootOfTree);
        myTree.calcFirstLast(myTree.rootOfTree);
        myTree.calcFollow(myTree.rootOfTree);

        //input list
        Assert.assertTrue(myTree.inputList.size() == 10);

        Assert.assertEquals(myTree.inputList.get(0).operatorText, "*");
        Assert.assertTrue(myTree.inputList.get(0).priority == 4);
        Assert.assertTrue(myTree.inputList.get(0).nullable);
        Assert.assertTrue(!myTree.inputList.get(0).typeTreePart);
        Assert.assertTrue(myTree.inputList.get(0).firstList.size() == 2);
        Assert.assertTrue(myTree.inputList.get(0).firstList.contains(1));
        Assert.assertTrue(myTree.inputList.get(0).firstList.contains(2));
        Assert.assertTrue(myTree.inputList.get(0).lastList.size() == 2);
        Assert.assertTrue(myTree.inputList.get(0).lastList.contains(1));
        Assert.assertTrue(myTree.inputList.get(0).lastList.contains(2));

        Assert.assertEquals(myTree.inputList.get(1).symbolsText, "a");
        Assert.assertTrue(myTree.inputList.get(1).nodeNumber == 1);
        Assert.assertTrue(!myTree.inputList.get(1).nullable);
        Assert.assertTrue(myTree.inputList.get(1).typeTreePart);
        Assert.assertTrue(myTree.inputList.get(1).firstList.size() == 1);
        Assert.assertTrue(myTree.inputList.get(1).firstList.contains(1));
        Assert.assertTrue(myTree.inputList.get(1).lastList.size() == 1);
        Assert.assertTrue(myTree.inputList.get(1).lastList.contains(1));

        Assert.assertEquals(myTree.inputList.get(2).operatorText, "|");
        Assert.assertTrue(myTree.inputList.get(2).priority == 12);
        Assert.assertTrue(!myTree.inputList.get(2).nullable);
        Assert.assertTrue(!myTree.inputList.get(2).typeTreePart);
        Assert.assertTrue(myTree.inputList.get(2).firstList.size() == 2);
        Assert.assertTrue(myTree.inputList.get(2).firstList.contains(1));
        Assert.assertTrue(myTree.inputList.get(2).firstList.contains(2));
        Assert.assertTrue(myTree.inputList.get(2).lastList.size() == 2);
        Assert.assertTrue(myTree.inputList.get(2).lastList.contains(1));
        Assert.assertTrue(myTree.inputList.get(2).lastList.contains(2));

        Assert.assertEquals(myTree.inputList.get(3).symbolsText, "b");
        Assert.assertTrue(myTree.inputList.get(3).nodeNumber == 2);
        Assert.assertTrue(!myTree.inputList.get(3).nullable);
        Assert.assertTrue(myTree.inputList.get(3).typeTreePart);
        Assert.assertTrue(myTree.inputList.get(3).firstList.size() == 1);
        Assert.assertTrue(myTree.inputList.get(3).firstList.contains(2));
        Assert.assertTrue(myTree.inputList.get(3).lastList.size() == 1);
        Assert.assertTrue(myTree.inputList.get(3).lastList.contains(2));

        Assert.assertEquals(myTree.inputList.get(4).operatorText, "&");
        Assert.assertTrue(myTree.inputList.get(4).priority == 3);
        Assert.assertTrue(!myTree.inputList.get(4).nullable);
        Assert.assertTrue(!myTree.inputList.get(4).typeTreePart);
        Assert.assertTrue(myTree.inputList.get(4).firstList.size() == 3);
        Assert.assertTrue(myTree.inputList.get(4).firstList.contains(1));
        Assert.assertTrue(myTree.inputList.get(4).firstList.contains(2));
        Assert.assertTrue(myTree.inputList.get(4).firstList.contains(3));
        Assert.assertTrue(myTree.inputList.get(4).lastList.size() == 1);
        Assert.assertTrue(myTree.inputList.get(4).lastList.contains(3));

        Assert.assertEquals(myTree.inputList.get(5).symbolsText, "a");
        Assert.assertTrue(myTree.inputList.get(5).nodeNumber == 3);
        Assert.assertTrue(!myTree.inputList.get(5).nullable);
        Assert.assertTrue(myTree.inputList.get(5).typeTreePart);
        Assert.assertTrue(myTree.inputList.get(5).firstList.size() == 1);
        Assert.assertTrue(myTree.inputList.get(5).firstList.contains(3));
        Assert.assertTrue(myTree.inputList.get(5).lastList.size() == 1);
        Assert.assertTrue(myTree.inputList.get(5).lastList.contains(3));

        Assert.assertEquals(myTree.inputList.get(6).operatorText, "&");
        Assert.assertTrue(myTree.inputList.get(6).priority == 3);
        Assert.assertTrue(!myTree.inputList.get(6).nullable);
        Assert.assertTrue(!myTree.inputList.get(6).typeTreePart);
        Assert.assertTrue(myTree.inputList.get(6).firstList.size() == 3);
        Assert.assertTrue(myTree.inputList.get(6).firstList.contains(1));
        Assert.assertTrue(myTree.inputList.get(6).firstList.contains(2));
        Assert.assertTrue(myTree.inputList.get(6).firstList.contains(3));
        Assert.assertTrue(myTree.inputList.get(6).lastList.size() == 1);
        Assert.assertTrue(myTree.inputList.get(6).lastList.contains(4));

        Assert.assertEquals(myTree.inputList.get(7).symbolsText, "b");
        Assert.assertTrue(myTree.inputList.get(7).nodeNumber == 4);
        Assert.assertTrue(!myTree.inputList.get(7).nullable);
        Assert.assertTrue(myTree.inputList.get(7).typeTreePart);
        Assert.assertTrue(myTree.inputList.get(7).firstList.size() == 1);
        Assert.assertTrue(myTree.inputList.get(7).firstList.contains(4));
        Assert.assertTrue(myTree.inputList.get(7).lastList.size() == 1);
        Assert.assertTrue(myTree.inputList.get(7).lastList.contains(4));

        Assert.assertEquals(myTree.inputList.get(8).operatorText, "&");
        Assert.assertTrue(myTree.inputList.get(8).priority == 3);
        Assert.assertTrue(!myTree.inputList.get(8).nullable);
        Assert.assertTrue(!myTree.inputList.get(8).typeTreePart);
        Assert.assertTrue(myTree.inputList.get(8).firstList.size() == 3);
        Assert.assertTrue(myTree.inputList.get(8).firstList.contains(1));
        Assert.assertTrue(myTree.inputList.get(8).firstList.contains(2));
        Assert.assertTrue(myTree.inputList.get(8).firstList.contains(3));
        Assert.assertTrue(myTree.inputList.get(8).lastList.size() == 1);
        Assert.assertTrue(myTree.inputList.get(8).lastList.contains(5));

        Assert.assertEquals(myTree.inputList.get(9).symbolsText, "b");
        Assert.assertTrue(myTree.inputList.get(9).nodeNumber == 5);
        Assert.assertTrue(!myTree.inputList.get(9).nullable);
        Assert.assertTrue(myTree.inputList.get(9).typeTreePart);
        Assert.assertTrue(myTree.inputList.get(9).firstList.size() == 1);
        Assert.assertTrue(myTree.inputList.get(9).firstList.contains(5));
        Assert.assertTrue(myTree.inputList.get(9).lastList.size() == 1);
        Assert.assertTrue(myTree.inputList.get(9).lastList.contains(5));

        //
    }

    @Test
    public void generateTransitionTable() throws Exception {
        String inputLineList = "(a|b)*&a&b&b";
        FLF myTree = new FLF();
        myTree.inputList = myTree.createList(inputLineList);
        //myTree.printList(myTree.inputList);
        myTree.rootOfTree =  myTree.createTree(myTree.inputList);
        //System.out.println(myTree.rootOfTree);
        myTree.calcNullable(myTree.rootOfTree);
        myTree.calcFirstLast(myTree.rootOfTree);
        myTree.calcFollow(myTree.rootOfTree);
        myTree.printTree( myTree.rootOfTree, 0);
        myTree.printFollow();
        System.out.println(myTree.transitionData);
        myTree.generateTransitionTable();
        System.out.println(myTree.transitionProduction);
        myTree.printTransitionTable();

        myTree.printFinalState();



        Assert.assertTrue(myTree.transitionProduction.size() == 4);
        Assert.assertTrue(myTree.transitionProduction.containsKey("A"));
        Assert.assertTrue(myTree.transitionProduction.get("A").size() == 3);
        Assert.assertTrue(myTree.transitionProduction.get("A").contains(1));
        Assert.assertTrue(myTree.transitionProduction.get("A").contains(2));
        Assert.assertTrue(myTree.transitionProduction.get("A").contains(3));

        Assert.assertTrue(myTree.transitionProduction.containsKey("B"));
        Assert.assertTrue(myTree.transitionProduction.get("B").size() == 4);
        Assert.assertTrue(myTree.transitionProduction.get("B").contains(1));
        Assert.assertTrue(myTree.transitionProduction.get("B").contains(2));
        Assert.assertTrue(myTree.transitionProduction.get("B").contains(3));
        Assert.assertTrue(myTree.transitionProduction.get("B").contains(4));

        Assert.assertTrue(myTree.transitionProduction.containsKey("C"));
        Assert.assertTrue(myTree.transitionProduction.get("C").size() == 4);
        Assert.assertTrue(myTree.transitionProduction.get("C").contains(1));
        Assert.assertTrue(myTree.transitionProduction.get("C").contains(2));
        Assert.assertTrue(myTree.transitionProduction.get("C").contains(3));
        Assert.assertTrue(myTree.transitionProduction.get("C").contains(5));

        Assert.assertTrue(myTree.transitionProduction.containsKey("D"));
        Assert.assertTrue(myTree.transitionProduction.get("D").size() == 4);
        Assert.assertTrue(myTree.transitionProduction.get("D").contains(1));
        Assert.assertTrue(myTree.transitionProduction.get("D").contains(2));
        Assert.assertTrue(myTree.transitionProduction.get("D").contains(3));
        Assert.assertTrue(myTree.transitionProduction.get("D").contains(6));


        Assert.assertTrue(myTree.transitionTable.size() == 8);

        Assert.assertTrue(myTree.transitionTable.get(0).beginState.equals("A"));
        Assert.assertTrue(myTree.transitionTable.get(0).word.equals("a"));
        Assert.assertTrue(myTree.transitionTable.get(0).endState.equals("B"));
        Assert.assertTrue(myTree.transitionTable.get(0).numberSet.size() == 4);
        Assert.assertTrue(myTree.transitionTable.get(0).numberSet.contains(1));
        Assert.assertTrue(myTree.transitionTable.get(0).numberSet.contains(2));
        Assert.assertTrue(myTree.transitionTable.get(0).numberSet.contains(3));
        Assert.assertTrue(myTree.transitionTable.get(0).numberSet.contains(4));

        Assert.assertTrue(myTree.transitionTable.get(1).beginState.equals("A"));
        Assert.assertTrue(myTree.transitionTable.get(1).word.equals("b"));
        Assert.assertTrue(myTree.transitionTable.get(1).endState.equals("A"));
        Assert.assertTrue(myTree.transitionTable.get(1).numberSet.size() == 3);
        Assert.assertTrue(myTree.transitionTable.get(1).numberSet.contains(1));
        Assert.assertTrue(myTree.transitionTable.get(1).numberSet.contains(1));
        Assert.assertTrue(myTree.transitionTable.get(1).numberSet.contains(1));

        Assert.assertTrue(myTree.transitionTable.get(2).beginState.equals("B"));
        Assert.assertTrue(myTree.transitionTable.get(2).word.equals("a"));
        Assert.assertTrue(myTree.transitionTable.get(2).endState.equals("B"));
        Assert.assertTrue(myTree.transitionTable.get(2).numberSet.size() == 4);
        Assert.assertTrue(myTree.transitionTable.get(2).numberSet.contains(1));
        Assert.assertTrue(myTree.transitionTable.get(2).numberSet.contains(2));
        Assert.assertTrue(myTree.transitionTable.get(2).numberSet.contains(3));
        Assert.assertTrue(myTree.transitionTable.get(2).numberSet.contains(4));

        Assert.assertTrue(myTree.transitionTable.get(3).beginState.equals("B"));
        Assert.assertTrue(myTree.transitionTable.get(3).word.equals("b"));
        Assert.assertTrue(myTree.transitionTable.get(3).endState.equals("C"));
        Assert.assertTrue(myTree.transitionTable.get(3).numberSet.size() == 4);
        Assert.assertTrue(myTree.transitionTable.get(3).numberSet.contains(1));
        Assert.assertTrue(myTree.transitionTable.get(3).numberSet.contains(2));
        Assert.assertTrue(myTree.transitionTable.get(3).numberSet.contains(3));
        Assert.assertTrue(myTree.transitionTable.get(3).numberSet.contains(5));

        Assert.assertTrue(myTree.transitionTable.get(4).beginState.equals("C"));
        Assert.assertTrue(myTree.transitionTable.get(4).word.equals("a"));
        Assert.assertTrue(myTree.transitionTable.get(4).endState.equals("B"));
        Assert.assertTrue(myTree.transitionTable.get(5).numberSet.size() == 4);
        Assert.assertTrue(myTree.transitionTable.get(4).numberSet.contains(1));
        Assert.assertTrue(myTree.transitionTable.get(4).numberSet.contains(2));
        Assert.assertTrue(myTree.transitionTable.get(4).numberSet.contains(3));
        Assert.assertTrue(myTree.transitionTable.get(4).numberSet.contains(4));

        Assert.assertTrue(myTree.transitionTable.get(5).beginState.equals("C"));
        Assert.assertTrue(myTree.transitionTable.get(5).word.equals("b"));
        Assert.assertTrue(myTree.transitionTable.get(5).endState.equals("D"));
        Assert.assertTrue(myTree.transitionTable.get(5).numberSet.size() == 4);
        Assert.assertTrue(myTree.transitionTable.get(5).numberSet.contains(1));
        Assert.assertTrue(myTree.transitionTable.get(5).numberSet.contains(2));
        Assert.assertTrue(myTree.transitionTable.get(5).numberSet.contains(3));
        Assert.assertTrue(myTree.transitionTable.get(5).numberSet.contains(6));

        Assert.assertTrue(myTree.transitionTable.get(6).beginState.equals("D"));
        Assert.assertTrue(myTree.transitionTable.get(6).word.equals("a"));
        Assert.assertTrue(myTree.transitionTable.get(6).endState.equals("B"));
        Assert.assertTrue(myTree.transitionTable.get(6).numberSet.size() == 4);
        Assert.assertTrue(myTree.transitionTable.get(6).numberSet.contains(1));
        Assert.assertTrue(myTree.transitionTable.get(6).numberSet.contains(2));
        Assert.assertTrue(myTree.transitionTable.get(6).numberSet.contains(3));
        Assert.assertTrue(myTree.transitionTable.get(6).numberSet.contains(4));

        Assert.assertTrue(myTree.transitionTable.get(7).beginState.equals("D"));
        Assert.assertTrue(myTree.transitionTable.get(7).word.equals("b"));
        Assert.assertTrue(myTree.transitionTable.get(7).endState.equals("A"));
        Assert.assertTrue(myTree.transitionTable.get(7).numberSet.size() == 3);
        Assert.assertTrue(myTree.transitionTable.get(7).numberSet.contains(1));
        Assert.assertTrue(myTree.transitionTable.get(7).numberSet.contains(2));
        Assert.assertTrue(myTree.transitionTable.get(7).numberSet.contains(3));

        Assert.assertTrue(myTree.finalState.equals(6));

    }

}