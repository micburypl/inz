package firstLastFollowWindow;

import algorithmFLF.FLF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by DELL6430u on 2017-04-10.
 */
public class FirstLastFollowSolverController implements Initializable {

    FLF myTree;

    @FXML
    ListView firstLastFollowInputList;

    @FXML
    VBox firstLastFollowButtonVBox;

    @FXML
    Pane firstLastFollowOutputPane;

    @FXML
    Button firstLastFollowSolveButton;

    @FXML
    TextField firstLastFollowInput;

    List<FirstLastFollowInputController> list = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollowInput.fxml"));
        list.add(x.getController());
        Button b = new Button("+");
        b.setOnAction(handle -> {
            FXMLLoader x1 = new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollowInput.fxml"));
            list.add(x1.getController());
            try {
                firstLastFollowInputList.getItems().add(firstLastFollowInputList.getItems().size() - 1, x1.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            firstLastFollowInputList.getItems().add(x.load());

            firstLastFollowInputList.getItems().add(b);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void generateFLF(ActionEvent actionEvent) {

        myTree = new FLF();
        System.out.println(firstLastFollowInput);
        myTree.inputList = myTree.createList(firstLastFollowInput.getText());
        myTree.printList(myTree.inputList);
        myTree.rootOfTree =  myTree.createTree(myTree.inputList);
        System.out.println(myTree.rootOfTree);
        myTree.calcNullable(myTree.rootOfTree);
        myTree.calcFirstLast(myTree.rootOfTree);
        myTree.calcFollow(myTree.rootOfTree);

    }

    public void printTree(ActionEvent actionEvent) {
    }

    public void printNullable(ActionEvent actionEvent) {
    }

    public void printFirstLast(ActionEvent actionEvent) {
    }

    public void printFollow(ActionEvent actionEvent) {
    }

    public void printEclosureFunction(ActionEvent actionEvent) {
    }

    public void printTransitionTable(ActionEvent actionEvent) {
    }

    public void printGraph(ActionEvent actionEvent) {
    }


}
