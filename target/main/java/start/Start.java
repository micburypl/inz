package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jgraph.JGraph;
import other.FLF;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 2017-03-13.
 */
public class Start extends Application {

    public static void main(String[] argc) {
        launch(argc);
    }

    public void start(Stage stage) throws Exception {

        //System.out.print("Hello world!!");

        //System.out.print("Hello world ok");

        FLF myTree;
        List<String> inputLineLinst = new ArrayList<String>();
//        inputLineLinst.add("t e s t 1 - t e s t 2 * t e s t 3" );
//        inputLineLinst.add("test1 * test2 - test3");
//        inputLineLinst.add("TE ST*T EST/TES T");
//        inputLineLinst.add("qa1!asfa^234  fgg");
//        inputLineLinst.add("aaa&&bbb||ccc");
//        inputLineLinst.add("aaa&bbb|ccc");
//        inputLineLinst.add("aaa+-bbb");
//        inputLineLinst.add("(a(b)c)");
//        inputLineLinst.add("(a+c)");
//        inputLineLinst.add("a(ac)");
//        inputLineLinst.add("a*(ac)");
//        inputLineLinst.add("(a+c)b");
//        inputLineLinst.add("(a+c)*");
//        inputLineLinst.add("(a+c)*b");
//        inputLineLinst.add("a+(a+c)/c");
//        inputLineLinst.add("(a+(a+c))/c");
//        inputLineLinst.add("(a+b)&&(a*b)||(a-b)");
//        inputLineLinst.add("b+!a+b");
//        inputLineLinst.add("bb!a*b");
//        inputLineLinst.add("!aaaaa");
//        inputLineLinst.add("(a+b)^(a*c)");
//        inputLineLinst.add("!(a*b)");
//        inputLineLinst.add("a+b");
//        inputLineLinst.add("a+b*c");
//        inputLineLinst.add("(a+b)*c");
//        inputLineLinst.add("a+b-c");
//        inputLineLinst.add("A+B^C");
//        inputLineLinst.add("(A+B)^C");
//        inputLineLinst.add("(A+B)^C*D");
//        inputLineLinst.add("a^!b");
//        inputLineLinst.add("abcd && !1234 ^ (ddd34 + eeee11) || fgh12 * ii23");
//        inputLineLinst.add("(eps|bb&bb)*");

        //inputLineLinst.add("A -> B | B a | a b | a b | ab | b a | C | b a ");
        //inputLineLinst.add("A -> a | b | c | ab | a b | B | B | B a");
//        inputLineLinst.add("A -> B a");
//        inputLineLinst.add("A -> a B b");
//        inputLineLinst.add("B -> eps | b");


//        System.out.println(inputLineLinst);


        inputLineLinst.add("(a|b)*&a&b&b"); // example


        for(String line: inputLineLinst) {
        myTree = new FLF();
        myTree.inputList = myTree.createList(line);


        System.out.println(myTree.inputList);

        if(myTree.inputList != null && myTree.inputList.size() > 0) {
            myTree.printList(myTree.inputList);
            myTree.rootOfTree =  myTree.createTree(myTree.inputList);
            System.out.println(myTree.rootOfTree);
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

        }
    }
//        inputLineLinst.add("Goal -> A");
//        inputLineLinst.add("A -> ( A ) | Two");
//        inputLineLinst.add("Two -> a");
//        inputLineLinst.add("Two -> b");


//        inputLineLinst.add("A -> B | a");
//        inputLineLinst.add("B -> A | b");

//        inputLineLinst.add("S -> A S'");
//        inputLineLinst.add("A -> B A' ");
//        inputLineLinst.add("B -> ( S ) | a");
//        inputLineLinst.add("A' -> * B A' | eps");
//        inputLineLinst.add("S' -> + A S' | eps");



        //First testFirst = new First(inputLineLinst);
        //testFirst.generateParsedSet();
        Scene testScene;
        testScene = new Scene((Parent)new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollowSolver.fxml")).load(), 300, 300);
        stage.setTitle("My JavaFX Application");
        stage.setScene(testScene);
        stage.show();


    }



}
