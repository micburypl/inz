import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017-03-13.
 */
public class Start extends Application {

    public static void main(String[] argc){
        launch(argc);
    }

    public void start(Stage stage) throws Exception {

    System.out.print("Hello world!!");

    System.out.print("Hello world ok");
    Tree myTree;
    List<String> inputLineLinst = new ArrayList<String>();
        inputLineLinst.add("t e s t 1 - t e s t 2 * t e s t 3" );
        inputLineLinst.add("test1 * test2 - test3");
        inputLineLinst.add("TE ST*T EST/TES T");
        inputLineLinst.add("qa1!asfa^234  fgg");
        inputLineLinst.add("aaa&&bbb||ccc");
        inputLineLinst.add("aaa&bbb|ccc");
        inputLineLinst.add("aaa+-bbb");
        inputLineLinst.add("(a(b)c)");
        inputLineLinst.add("(a+c)");
        inputLineLinst.add("a(ac)");
        inputLineLinst.add("a*(ac)");
        inputLineLinst.add("(a+c)b");
        inputLineLinst.add("(a+c)*");
        inputLineLinst.add("(a+c)*b");
        inputLineLinst.add("a+(a+c)/c");
        inputLineLinst.add("(a+(a+c))/c");
        inputLineLinst.add("(a+b)&&(a*b)||(a-b)");
        inputLineLinst.add("b+!a+b");
        inputLineLinst.add("bb!a*b");
        inputLineLinst.add("!aaaaa");
        inputLineLinst.add("(a+b)^(a*c)");
        inputLineLinst.add("!(a*b)");
        inputLineLinst.add("a+b");
        inputLineLinst.add("a+b*c");
        inputLineLinst.add("(a+b)*c");
        inputLineLinst.add("a+b-c");
        inputLineLinst.add("A+B^C");
        inputLineLinst.add("(A+B)^C");
        inputLineLinst.add("(A+B)^C*D");
        inputLineLinst.add("a^!b");
        inputLineLinst.add("abcd && !1234 ^ (ddd34 + eeee11) || fgh12 * ii23");

    for(String line: inputLineLinst) {
        myTree = new Tree();
        myTree.inputQueue = myTree.createQueue(line);


        System.out.println(myTree.inputQueue);
        if(myTree.inputQueue != null && myTree.inputQueue.size() > 0) {
            myTree.printQueue(myTree.inputQueue);
            myTree.rootOfTree =  myTree.createTree(myTree.inputQueue);
            System.out.println(myTree.rootOfTree);
            myTree.printTree( myTree.rootOfTree, 0);
        }



    }

    stage.setTitle("My JavaFX Application");
    //stage.setScene(scene);
    stage.show();
    }
}
