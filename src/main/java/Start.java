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
        inputLineLinst.add("(a|b)*&a&b&b");

    for(String line: inputLineLinst) {
        myTree = new FLF();
        myTree.inputList = myTree.createList(line);


        System.out.println(myTree.inputList);

        if(myTree.inputList != null && myTree.inputList.size() > 0) {
            myTree.printList(myTree.inputList);
            myTree.rootOfTree =  myTree.createTree(myTree.inputList);
            System.out.println(myTree.rootOfTree);
            myTree.isNullable(myTree.rootOfTree);
            myTree.calcFirstLast(myTree.rootOfTree);
            myTree.calcFollow(myTree.rootOfTree);
            myTree.printTree( myTree.rootOfTree, 0);
            myTree.printFollow();
            System.out.println(myTree.transitionData);
        }



    }

    stage.setTitle("My JavaFX Application");
    //stage.setScene(scene);
    stage.show();
    }



}
