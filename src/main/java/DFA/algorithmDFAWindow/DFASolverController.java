package DFA.algorithmDFAWindow;

import DFA.algorithmDFA.DFA;
import DFA.algorithmDFA.DFAPart;
import DFA.algorithmDFA.TransitionSolverElement;
import DFA.algorithmDFA.TransitionTableElement;
import commonUtility.CommonUtility;
import graph.GraphMethods;
import graph.GraphThreeMethods;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.net.URL;
import java.util.*;


public class DFASolverController implements Initializable {

    DFA myTree;



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

    @FXML
    Label firstLastFollowGraphLabel;

    @FXML
    Label firstLastFollowSolutionsLabel;

    @FXML
    Label firstLastFollowPartialSolutionsLabel;

    @FXML
    TextField firstLastFollowWordInput;

    @FXML
    Label inputStringLabel;

    @FXML
    Button movesTableButton;

    List<DFAInputController> list = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/DFAWindow/DFAInput.fxml"));
        list.add(x.getController());
        Button b = new Button("+");

        //firstLastFollowButtonVBox.setVisible(false);
        showElement(false);

        b.setOnAction(handle -> {
            FXMLLoader x1 = new FXMLLoader(getClass().getResource("/fxml/test/DFAWindow/DFAInput.fxml"));
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

            //firstLastFollowButtonVBox.setVisible(false);
            showElement(false);
            firstLastFollowGraphPane.getChildren().clear();
            firstLastFollowOutputPane.getChildren().clear();
            GridPane gridPane = new GridPane();


            Label tempLabel = new Label(CommonUtility.getKey("firstLastFollow.emptyInput"));

            //Label tempLabel = new Label("Empty input. Please give correct input");
            gridPane.add(tempLabel, 0,0);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            firstLastFollowOutputPane.getChildren().add(gridPane);
            return;
        }

        myTree = new DFA();
        System.out.println(inputData);
        myTree.inputList = myTree.createList(inputData);

        if(myTree.errorFlag) {
            showElement(false);
            //firstLastFollowButtonVBox.setVisible(false);
            firstLastFollowGraphPane.getChildren().clear();
            firstLastFollowOutputPane.getChildren().clear();
            GridPane gridPane = new GridPane();

            Label tempLabel = new Label(CommonUtility.getKey("firstLastFollow.wrongInput"));

           // Label tempLabel = new Label("Wrong input. Please give correct input");
            gridPane.add(tempLabel, 0,0);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            //firstLastFollowOutputPane.getChildren().add(gridPane);

            ScrollPane tempScrollPane = new ScrollPane(gridPane);
            firstLastFollowOutputPane.getChildren().add(tempScrollPane);

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

        //firstLastFollowButtonVBox.setVisible(true);
        showElement(true);

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


        Label tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.Number" )+ "   ");
        //Label tempLabel = new Label("   Number   ");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);


        tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.Nullable") + "   ");
        //tempLabel = new Label("   Nullable   ");
        gridPane.add(tempLabel, 1,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(DFAPart element: myTree.outputList ){


            tempLabel = new Label("el. " + element.controlNumber.toString());
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            if(element.nullable) {
                solutionString = "   True   ";
            } else {
                solutionString = "   False   ";

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
        //firstLastFollowOutputPane.getChildren().add(gridPane);

        ScrollPane tempScrollPane = new ScrollPane(gridPane);
        firstLastFollowOutputPane.getChildren().add(tempScrollPane);
    }

    public void printFirstLast(ActionEvent actionEvent) throws IOException {


        firstLastFollowOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();

        Label tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.Element" )+ "   ");
        //Label tempLabel = new Label("   Element   ");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.FirstElement" )+ "   ");
        //tempLabel = new Label("   First(Element)   ");
        gridPane.add(tempLabel, 1,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.LastElement" )+ "   ");
        //tempLabel = new Label("   Last(Element)   ");
        gridPane.add(tempLabel, 2,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);


        String tempString;
        Integer rowNumber = 1;

        gridPane.setGridLinesVisible(true);

        for(DFAPart element: myTree.outputList ){

            tempLabel = new Label("el. " + element.controlNumber.toString());
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
            for(Integer tempInt: element.lastList) {
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

        Integer numberOfColumn = 3;
        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane.getColumnConstraints().add(column);
        }

        //firstLastFollowOutputPane.getChildren().add(gridPane);

        ScrollPane tempScrollPane = new ScrollPane(gridPane);
        firstLastFollowOutputPane.getChildren().add(tempScrollPane);
    }

    public void printFollow(ActionEvent actionEvent) throws IOException {

        firstLastFollowOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();
        String solutionString;


        Label tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.Number" )+ "   ");

        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.FollowNumber" )+ "   ");
        //tempLabel = new Label("   Follow(Number)   ");
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
                tempString = CommonUtility.getKey("firstLastFollow.Empty");
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

        //firstLastFollowOutputPane.getChildren().add(gridPane);

        ScrollPane tempScrollPane = new ScrollPane(gridPane);
        firstLastFollowOutputPane.getChildren().add(tempScrollPane);

    }

    public void printTransitionTable(ActionEvent actionEvent) throws IOException {

        firstLastFollowOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();

        Label tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.BeginState" )+ "   ");
        //Label tempLabel = new Label("   Begin State   ");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.Transition" )+ "   ");
        //tempLabel = new Label("   Transition   ");
        gridPane.add(tempLabel, 1,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.EndState" )+ "   ");
        //tempLabel = new Label("   End State   ");
        gridPane.add(tempLabel, 2,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.Elements" )+ "   ");
        //tempLabel = new Label("   Elements   ");
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
                tempString= CommonUtility.getKey("firstLastFollow.Empty");
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

        //firstLastFollowOutputPane.getChildren().add(gridPane);

        ScrollPane tempScrollPane = new ScrollPane(gridPane);
        firstLastFollowOutputPane.getChildren().add(tempScrollPane);
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



    public void checkInput(ActionEvent actionEvent) {

        firstLastFollowOutputPane.getChildren().clear();

        GridPane gridPane = new GridPane();
        String input = firstLastFollowWordInput.getText();
        ArrayList<String> inputList = new ArrayList<>();
        for(String s: input.split(" ")) {
            inputList.add(s);
        }

        ArrayList<TransitionSolverElement> answerList = myTree.generateTransitionCheckList(inputList);

        Label tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.CurrentState" )+ "   ");
       // Label tempLabel = new Label("   Current State   ");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.Word" )+ "   ");
        //tempLabel = new Label("   Word   ");
        gridPane.add(tempLabel, 1,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("   " + CommonUtility.getKey("firstLastFollow.EndState" )+ "   ");

        //tempLabel = new Label("   End State   ");
        gridPane.add(tempLabel, 2,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);


        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(TransitionSolverElement tse: answerList) {

            tempLabel = new Label(tse.currentState);
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            tempLabel = new Label(tse.currentWord);
            tempLabel.setWrapText(true);
            gridPane.add(tempLabel, 1,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            tempLabel = new Label(tse.endState);
            gridPane.add(tempLabel, 2,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            rowNumber++;

        }
        Integer numberOfColumn = 3;

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(20);
        gridPane.getColumnConstraints().add(column);
        column = new ColumnConstraints();
        column.setPercentWidth(60);
        gridPane.getColumnConstraints().add(column);
        column = new ColumnConstraints();
        column.setPercentWidth(20);
        gridPane.getColumnConstraints().add(column);

        //firstLastFollowOutputPane.getChildren().add(gridPane);

        ScrollPane tempScrollPane = new ScrollPane(gridPane);
        System.out.println(tempScrollPane.getWidth());
        firstLastFollowOutputPane.getChildren().add(tempScrollPane);
        System.out.println(tempScrollPane.getWidth());
    }

    void showElement(Boolean show) {
        firstLastFollowButtonVBox.setVisible(show);
        firstLastFollowGraphLabel.setVisible(show);
        firstLastFollowSolutionsLabel.setVisible(show);
        firstLastFollowPartialSolutionsLabel.setVisible(show);
        inputStringLabel.setVisible(show);
        movesTableButton.setVisible(show);
        firstLastFollowWordInput.setVisible(show);
    }





}
