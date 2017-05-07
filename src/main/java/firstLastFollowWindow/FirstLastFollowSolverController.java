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
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
    Pane firstLastFollowGraphPane;

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

        firstLastFollowButtonVBox.setVisible(false);

        b.setOnAction(handle -> {
            FXMLLoader x1 = new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollowInput.fxml"));
            list.add(x1.getController());
            try {
                firstLastFollowInputList.getItems().add(firstLastFollowInputList.getItems().size() - 1, x1.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //button.setOnAction

    }


    public void generateFLF(ActionEvent actionEvent) {

        String inputData;// = "(a|b)*&a&b&b";
        inputData = firstLastFollowInput.getText();

        if(inputData == null || inputData.isEmpty()) {

            firstLastFollowButtonVBox.setVisible(false);
            firstLastFollowGraphPane.getChildren().clear();
            firstLastFollowOutputPane.getChildren().clear();
            GridPane gridPane = new GridPane();

            Label tempLabel = new Label("Empty input. Please give correct input");
            gridPane.add(tempLabel, 0,0);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            firstLastFollowOutputPane.getChildren().add(gridPane);
            return;
        }

        myTree = new FLF();
        System.out.println(inputData);
        myTree.inputList = myTree.createList(inputData);

        if(myTree.errorFlag) {
            firstLastFollowButtonVBox.setVisible(false);
            firstLastFollowGraphPane.getChildren().clear();
            firstLastFollowOutputPane.getChildren().clear();
            GridPane gridPane = new GridPane();

            Label tempLabel = new Label("Wrong input. Please give correct input");
            gridPane.add(tempLabel, 0,0);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            firstLastFollowOutputPane.getChildren().add(gridPane);

            return;
        }

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

        firstLastFollowButtonVBox.setVisible(true);

        firstLastFollowOutputPane.getChildren().clear();
        firstLastFollowGraphPane.getChildren().clear();


        SwingNode swingComponentWrapper = new SwingNode();

        ArrayList<String> tempList = new ArrayList<>();


        GraphThreeMethods tempGraph = new GraphThreeMethods(myTree.outputList);

        swingComponentWrapper.setContent(tempGraph.graphComponent);

        firstLastFollowGraphPane.getChildren().add(swingComponentWrapper);


    }

    public void printTree(ActionEvent actionEvent) throws IOException {

        firstLastFollowGraphPane.getChildren().clear();

        SwingNode swingComponentWrapper = new SwingNode();

        ArrayList<String> tempList = new ArrayList<>();


        GraphThreeMethods tempGraph = new GraphThreeMethods(myTree.outputList);

        swingComponentWrapper.setContent(tempGraph.graphComponent);

        firstLastFollowGraphPane.getChildren().add(swingComponentWrapper);
//        firstLastFollowGraphPane.setVisible(false);
//        firstLastFollowGraphPane.setVisible(true);
    }

    public void printNullable(ActionEvent actionEvent) throws IOException {


        firstLastFollowOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();
        String solutionString;

        Label tempLabel = new Label("Number");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("Nullable");
        gridPane.add(tempLabel, 1,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(FLFPart element: myTree.outputList ){


            tempLabel = new Label(element.controlNumber.toString());
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            if(element.nullable) {
                solutionString = "True";
            } else {
                solutionString = "False";

            }
            tempLabel = new Label(solutionString);
            gridPane.add(tempLabel, 1,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            rowNumber++;

        }

        Integer numberOfColumn = 2;
        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane.getColumnConstraints().add(column);
        }
        firstLastFollowOutputPane.getChildren().add(gridPane);
    }

    public void printFirstLast(ActionEvent actionEvent) throws IOException {


        firstLastFollowOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();

        Label tempLabel = new Label("Element");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("First(Element)");
        gridPane.add(tempLabel, 1,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("Last(Element)");
        gridPane.add(tempLabel, 2,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);


        String tempString;
        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(FLFPart element: myTree.outputList ){

            tempLabel = new Label(element.controlNumber.toString());
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            tempString = "";
            for(Integer tempInt: element.firstList) {
                tempString += tempInt;
                tempString += ", ";
            }
            if(tempString.length() > 2) {
                tempString = tempString.substring(0, tempString.length()-2);
            }

            tempLabel = new Label(tempString);
            gridPane.add(tempLabel, 1,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            tempString = "";
            for(Integer tempInt: element.firstList) {
                tempString += tempInt;
                tempString += ", ";
            }
            if(tempString.length() > 2) {
                tempString = tempString.substring(0, tempString.length()-2);
            }

            tempLabel = new Label(tempString);
            gridPane.add(tempLabel, 2,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            rowNumber++;
        }
        firstLastFollowOutputPane.getChildren().add(gridPane);

        Integer numberOfColumn = 3;
        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane.getColumnConstraints().add(column);
        }
    }

    public void printFollow(ActionEvent actionEvent) throws IOException {

        firstLastFollowOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();
        String solutionString;

        Label tempLabel = new Label("Number");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("Nullable");
        gridPane.add(tempLabel, 1,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);


        String tempString;
        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(Integer element: myTree.followMap.keySet() ){

            tempLabel = new Label(element.toString());
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            tempString = "";
            for(Integer tempInt: myTree.followMap.get(element)) {
                tempString += tempInt;
                tempString += ", ";
            }
            if(tempString.length() > 2) {
                tempString = tempString.substring(0,tempString.length() - 2);
            } else if (tempString.length() > 0){

            } else {
                tempString= "empty";
            }

            tempLabel = new Label(tempString);
            gridPane.add(tempLabel, 1,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            rowNumber++;

        }

        Integer numberOfColumn = 2;
        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane.getColumnConstraints().add(column);
        }

        firstLastFollowOutputPane.getChildren().add(gridPane);

    }

    public void printTransitionTable(ActionEvent actionEvent) throws IOException {

        firstLastFollowOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();

        Label tempLabel = new Label("Begin State");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("Transition");
        gridPane.add(tempLabel, 1,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("End State");
        gridPane.add(tempLabel, 2,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("Elements");
        gridPane.add(tempLabel, 3,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        String tempString;
        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(TransitionTableElement element: myTree.transitionTable){

            tempLabel = new Label(element.beginState);
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            tempLabel = new Label(element.word);
            gridPane.add(tempLabel, 1,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            tempLabel = new Label(element.endState);
            gridPane.add(tempLabel, 2,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);


            tempString = "";
            for(Integer tempInt: element.numberSet) {
                tempString += tempInt;
                tempString += ", ";
            }
            if(tempString.length() > 2) {
                tempString = tempString.substring(0,tempString.length() - 2);
            } else if (tempString.length() > 0){

            } else {
                tempString= "empty";
            }

            tempLabel = new Label(tempString);
            gridPane.add(tempLabel, 3,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);
            rowNumber++;
        }

        Integer numberOfColumn = 4;
        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane.getColumnConstraints().add(column);
        }

        firstLastFollowOutputPane.getChildren().add(gridPane);
    }

    public void printGraph(ActionEvent actionEvent) {
        firstLastFollowOutputPane.getChildren().clear();

        SwingNode swingComponentWrapper = new SwingNode();
        HashMap<String, Boolean> tempList = new HashMap<>();

        for(String tempString: myTree.transitionProduction.keySet()){
            if(myTree.transitionProduction.get(tempString).contains(myTree.finalState)){
                tempList.put(tempString, true);
            } else {
                tempList.put(tempString, false);
            }

        }

        GraphMethods tempGraph = new GraphMethods(tempList, (ArrayList<TransitionTableElement>) myTree.transitionTable);

        swingComponentWrapper.setContent(tempGraph.graphComponent);

        firstLastFollowOutputPane.getChildren().add(swingComponentWrapper);
    }


}
