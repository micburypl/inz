import javafx.application.Application;
import javafx.stage.Stage;
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
    Tree myTree = new Tree();



    myTree.inputQueue = myTree.createQueue("t e s t 1 - t e s t 2 * t e s t 3");
    myTree.printQueue(myTree.inputQueue);
    myTree.rootOfTree =  myTree.createTree(myTree.inputQueue);
    myTree.printTree( myTree.rootOfTree, 0);


    stage.setTitle("My JavaFX Application");
    //stage.setScene(scene);
    stage.show();
    }
}
