package firstLastFollowWindow;

import algorithmFLF.FLF;
import algorithmFLF.FLFPart;
import algorithmFLF.TransitionTableElement;
import graph.GraphMethods;
import graph.GraphThreeMethods;
import javafx.embed.swing.SwingNode;
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
import java.util.HashMap;
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
    }


    public void generateFLF(ActionEvent actionEvent) {

        String inputData = "(a|b)*&a&b&b";
        //inputData = firstLastFollowInput; /sprawdzac

        myTree = new FLF();
        System.out.println(inputData);
        myTree.inputList = myTree.createList(inputData);
        myTree.printList(myTree.inputList);
        myTree.rootOfTree =  myTree.createTree(myTree.inputList);
        System.out.println(myTree.rootOfTree);
        myTree.calcNullable(myTree.rootOfTree);
        myTree.calcFirstLast(myTree.rootOfTree);
        myTree.calcFollow(myTree.rootOfTree);
        myTree.generateTransitionTable();
        myTree.printTransitionTable();
        myTree.printFinalState();

        myTree.numerateTree(myTree.rootOfTree);


    }

    public void printTree(ActionEvent actionEvent) {

        firstLastFollowOutputPane.getChildren().clear();

        SwingNode swingComponentWrapper = new SwingNode();

        ArrayList<String> tempList = new ArrayList<>();



        GraphThreeMethods tempGraph = new GraphThreeMethods(myTree.outputList);

        swingComponentWrapper.setContent(tempGraph.graphComponent);

        firstLastFollowOutputPane.getChildren().add(swingComponentWrapper);




    }

    public void printNullable(ActionEvent actionEvent) throws IOException {

        firstLastFollowOutputPane.getChildren().clear();
        ListView firstLastFollowOutputList = new ListView();
        String tempString;

        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollow2Output.fxml"));
        firstLastFollowOutputList.getItems().add(x.load());
        FirstLastFollow2OutputController xControler = x.getController();
        xControler.setElementNo("Number");
        xControler.setFirst("Nullable");

        for(FLFPart element: myTree.outputList ){
             x = new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollow2Output.fxml"));
            firstLastFollowOutputList.getItems().add(x.load());
             xControler = x.getController();
            // number
            xControler.setElementNo(element.controlNumber.toString());
            //nullable
            if(element.nullable) {
                xControler.setFirst("True");
            } else {
                xControler.setFirst("False");
            }
        }
        firstLastFollowOutputPane.getChildren().add(firstLastFollowOutputList);
    }

    public void printFirstLast(ActionEvent actionEvent) throws IOException {

        firstLastFollowOutputPane.getChildren().clear();
        ListView firstLastFollowOutputList = new ListView();
        String tempString;

        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollow3Output.fxml"));
        firstLastFollowOutputList.getItems().add(x.load());
        FirstLastFollow3OutputController xControler = x.getController();
        xControler.setElementNo("Number");
        xControler.setFirst("First");
        xControler.setSecond("Second");

        for(FLFPart element: myTree.outputList ){
            x = new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollow3Output.fxml"));
            firstLastFollowOutputList.getItems().add(x.load());
            xControler = x.getController();
            // number
            xControler.setElementNo(element.controlNumber.toString());
            //first
            tempString = "";
            for(Integer tempInt: element.firstList) {
                tempString += tempInt;
                tempString += ", ";
            }
            xControler.setFirst(tempString.substring(0,tempString.length() - 2));
            //last
            tempString = "";
            for(Integer tempInt: element.firstList) {
                tempString += tempInt;
                tempString += ", ";
            }
            xControler.setSecond(tempString.substring(0,tempString.length() - 2));
        }
        firstLastFollowOutputPane.getChildren().add(firstLastFollowOutputList);
    }

    public void printFollow(ActionEvent actionEvent) throws IOException {

        firstLastFollowOutputPane.getChildren().clear();
        ListView firstLastFollowOutputList = new ListView();
        String tempString;

        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollow2Output.fxml"));
        firstLastFollowOutputList.getItems().add(x.load());
        FirstLastFollow2OutputController xControler = x.getController();
        xControler.setElementNo("Number");
        xControler.setFirst("Follow");

        for(Integer element: myTree.followMap.keySet() ){
             x = new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollow2Output.fxml"));
            firstLastFollowOutputList.getItems().add(x.load());
             xControler = x.getController();
            // number
            xControler.setElementNo(element.toString());
            //follow
            tempString = "";
            for(Integer tempInt: myTree.followMap.get(element)) {
                tempString += tempInt;
                tempString += ", ";
            }

            if(tempString.length() > 2) {
                xControler.setFirst(tempString.substring(0,tempString.length() - 2));
            } else if (tempString.length() > 0){
                xControler.setFirst(tempString);
            } else {
                xControler.setFirst("empty");
            }


        }
        firstLastFollowOutputPane.getChildren().add(firstLastFollowOutputList);

    }



    public void printTransitionTable(ActionEvent actionEvent) throws IOException {

        firstLastFollowOutputPane.getChildren().clear();
        ListView firstLastFollowOutputList = new ListView();
        String tempString;

        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollow4Output.fxml"));
        firstLastFollowOutputList.getItems().add(x.load());
        FirstLastFollow4OutputController xControler = x.getController();
        xControler.setElementNo("Begin state");
        xControler.setFirst("Transition");
        xControler.setSecond("End state");
        xControler.setThird("Elements");

        for(TransitionTableElement element: myTree.transitionTable){
            x = new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollow4Output.fxml"));
            firstLastFollowOutputList.getItems().add(x.load());
            xControler = x.getController();
            // Begin state
            xControler.setElementNo(element.beginState);
            //transition
            xControler.setFirst(element.word);
            // End state
            xControler.setSecond(element.endState);
            //Elements
            tempString = "";
            for(Integer tempInt: element.numberSet) {
                tempString += tempInt;
                tempString += ", ";
            }
            if(tempString.length() > 2) {
                xControler.setThird(tempString.substring(0,tempString.length() - 2));
            } else if (tempString.length() > 0){
                xControler.setThird(tempString);
            } else {
                xControler.setThird("empty");
            }
        }
        firstLastFollowOutputPane.getChildren().add(firstLastFollowOutputList);

    }

    public void printFinalState(ActionEvent actionEvent) throws IOException {

        firstLastFollowOutputPane.getChildren().clear();
        ListView firstLastFollowOutputList = new ListView();
        String tempString ="";

        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollow2Output.fxml"));
        firstLastFollowOutputList.getItems().add(x.load());
        FirstLastFollow2OutputController xControler = x.getController();

        for(String element: myTree.transitionProduction.keySet()){
            if(myTree.transitionProduction.get(element).contains(myTree.finalState)){
                tempString += element + ", ";
            }
        }

        xControler.setElementNo("Final state");
        if(tempString.length() > 2) {
            xControler.setFirst(tempString.substring(0,tempString.length() - 2));
        } else if (tempString.length() > 0){
            xControler.setFirst(tempString);
        } else {
            xControler.setFirst("empty");
        }

        firstLastFollowOutputPane.getChildren().add(firstLastFollowOutputList);
    }

    public void printGraph(ActionEvent actionEvent) {
        firstLastFollowOutputPane.getChildren().clear();

        SwingNode swingComponentWrapper = new SwingNode();

        ArrayList<String> tempList = new ArrayList<>();

        for(String tempString: myTree.transitionProduction.keySet()){
            tempList.add(tempString);
        }

        GraphMethods tempGraph = new GraphMethods(tempList, (ArrayList<TransitionTableElement>) myTree.transitionTable);

        swingComponentWrapper.setContent(tempGraph.graphComponent);

        firstLastFollowOutputPane.getChildren().add(swingComponentWrapper);
    }


}
