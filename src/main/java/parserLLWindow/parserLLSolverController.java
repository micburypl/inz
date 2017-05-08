package parserLLWindow;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import commonUtility.CommonUtility;
import firstFollow.FirstFollow;
import firstFollowWindow.FirstFollowInputController;
import firstFollowWindow.FirstFollowOutputFirstController;
import firstFollowWindow.FirstFollowOutputFollowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import parserLL.MovesTable;
import parserLL.MovesTableElement;
import parserLR.MovesElementLR;
import parserLRWindow.parserLRMovesOutputController;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by DELL6430u on 2017-05-01.
 */
public class parserLLSolverController implements Initializable {

    @FXML
    ListView parserLLInputList;
    @FXML
    ListView parserLLOutputList;
    @FXML
    StackPane parserLLOutputPane;
    @FXML
    TextField movesTableInput;
    @FXML
    VBox parserLLButtonVBox;
    @FXML
    Button parserLLMovesButton;
    @FXML
    Label parserLLInputStringLabel;
    @FXML
    Label parserLLPartialSolutionsLabel;

    List<FirstFollowInputController> listInput = new ArrayList<>();

    FirstFollow testFirstFollow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowInput.fxml"));
        showElement(false);
        try {
            //x.load();
            parserLLInputList.getItems().add(x.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        listInput.add(x.getController());
        Button b = new Button("+");
        b.setOnAction(handle -> {
            FXMLLoader x1 = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowInput.fxml"));
            try {
                parserLLInputList.getItems().add(parserLLInputList.getItems().size() - 1, x1.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            listInput.add(x1.getController());
        });
        parserLLInputList.getItems().add(b);


    }

    public void generateLL(ActionEvent actionEvent) {

        List<String> inputLineList = new ArrayList<>();


        // Correct
//
        String tempString;
        for(Integer i = 0; i < listInput.size(); i++) {
            tempString =  listInput.get(i).getLeftPart() + " -> " + listInput.get(i).getRightPart();
            System.out.println(tempString);
            inputLineList.add(tempString);
        }

        //

        // Fortest

//        inputLineList.add("S -> A S'");
//        inputLineList.add("S' -> + A S' | eps");
//        inputLineList.add("A -> B A' ");
//        inputLineList.add("A' -> * B A' | eps");
//        inputLineList.add("B -> ( S ) | a");

        //
//        if(!inputLineList.isEmpty()) {
        testFirstFollow = new FirstFollow(inputLineList);
        testFirstFollow.generateSolutionSet();

        if(testFirstFollow.errorFlag) {

            //blocked buttons
            showElement(false);

            parserLLOutputPane.getChildren().clear();
            GridPane gridPane = new GridPane();
            gridPane.setGridLinesVisible(true);
            Label tempLabel = new Label("Errors");
            gridPane.add(tempLabel, 0,0);
            gridPane.setHalignment(tempLabel, HPos.CENTER);
            Integer tempInt = 1;
            for(Integer errorLine: testFirstFollow.errorMessages.keySet() ) {
                tempLabel = new Label("In row " + errorLine + ". " + testFirstFollow.errorMessages.get(errorLine));
                gridPane.add(tempLabel, 0, tempInt++);
                gridPane.setHalignment(tempLabel, HPos.CENTER);
            }

            parserLLOutputPane.getChildren().add(gridPane);
            return;
        }

        //show buttons
        showElement(true);
//        parserLLButtonVBox.setVisible(true);
//        parserLLMovesButton.setVisible(true);

        parserLLOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();
        parserLLOutputPane.getChildren().add(gridPane);



        testFirstFollow.predictiveMap.generatePredictiveMap(testFirstFollow.parsedSet, testFirstFollow.firstElementMap, testFirstFollow.followElementMap);
//        } else {
//            System.out.println("Empty list");
//        }

    }

    public void printFirst(ActionEvent actionEvent) throws IOException {

        parserLLOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();

        Label tempLabel = new Label("Element");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("First(Element)");
        gridPane.add(tempLabel, 1,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(String keyFirst: testFirstFollow.firstElementMap.keySet() ){

            tempLabel = new Label(keyFirst);
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            String solutionString = "";
            for(String temp: testFirstFollow.firstElementMap.get(keyFirst).firstSet) {
                solutionString += temp + ", ";
            }
            if(solutionString.length() > 2) {
                solutionString = solutionString.substring(0, solutionString.length()-2);
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

        parserLLOutputPane.getChildren().add(gridPane);
    }

    public void printFollow(ActionEvent actionEvent) throws IOException {

        parserLLOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();

        Label tempLabel = new Label("Element");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("Follow(Element)");
        gridPane.add(tempLabel, 1,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(String keyFollow: testFirstFollow.followElementMap.keySet() ){

            tempLabel = new Label(keyFollow);
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            String solutionString = "";
            for(String temp: testFirstFollow.followElementMap.get(keyFollow).followSet) {
                solutionString += temp + ", ";
            }
            if(solutionString.length() > 2) {
                solutionString = solutionString.substring(0, solutionString.length()-2);
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

        parserLLOutputPane.getChildren().add(gridPane);
    }

    public void printParsingTable(ActionEvent actionEvent) {

        parserLLOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();

        Label tempLabel = new Label("Prod");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        String tempString;

        HashMap columnSign = new HashMap<String, Integer>();
        Integer maxColumn = 1;
        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(String keyNumber: testFirstFollow.predictiveMap.predictiveMap.keySet()) {

            tempLabel = new Label(keyNumber);
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            for(String column: testFirstFollow.predictiveMap.predictiveMap.get(keyNumber).keySet()) {
                if(!columnSign.containsKey(column)){
                    columnSign.put(column,maxColumn++);

                    tempLabel = new Label(column);
                    gridPane.add(tempLabel, (Integer) columnSign.get(column),0);
                    gridPane.setHalignment(tempLabel, HPos.CENTER);

                }
                tempString = "";
                for(ArrayList<String> elementList: testFirstFollow.predictiveMap.predictiveMap.get(keyNumber).get(column)) {
                    if(!tempString.equals("")) {
                        tempString += " | ";
                    }
                    tempString += keyNumber + " -> ";
                    for(String element: elementList) {
                        tempString += element + " ";
                    }

                    tempLabel = new Label(tempString);
                    gridPane.add(tempLabel, (Integer) columnSign.get(column),rowNumber);
                    gridPane.setHalignment(tempLabel, HPos.CENTER);


                }

            }
            rowNumber++;
        }

        Integer numberOfColumn = maxColumn;
        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane.getColumnConstraints().add(column);
        }

        parserLLOutputPane.getChildren().add(gridPane);
    }

    public void printMovesTable(ActionEvent actionEvent) throws IOException {
        MovesTable testMoveTable = new MovesTable();
        FXMLLoader x;
        parserLLOutputPane.getChildren().clear();
        ListView parserLLOutputList = new ListView();
        if(movesTableInput.getText() != null && !movesTableInput.getText().trim().isEmpty()) {

            //TODO Start symbol
            //testMoveTable.generateMovesTable("S","a + a * a", testFirstFollow.predictiveMap.predictiveMap);
            testMoveTable.generateMovesTable("S",movesTableInput.getText(), testFirstFollow.predictiveMap.predictiveMap);

        } else {
            x = new FXMLLoader(getClass().getResource("/fxml/test/parserLLWindow/parserLLMovesErrorOutput.fxml"));
            parserLLOutputList.getItems().add(x.load());
            parserLLMovesErrorOutputController xControler = x.getController();
            //errorMessage
            xControler.setError("Input is Empty");
            System.out.println("Input is Empty");
            parserLLOutputPane.getChildren().add(parserLLOutputList);
            return;
        }

        FXMLLoader xStart = new FXMLLoader(getClass().getResource("/fxml/test/parserLLWindow/parserLLMovesOutput.fxml"));
        parserLLOutputList.getItems().add(xStart.load());
        parserLLMovesOutputController xControlerStart = xStart.getController();
        xControlerStart.setOutput("Output");
        xControlerStart.setInput("Input");
        xControlerStart.setStack("Stack");

        for(MovesTableElement keyNumber: testMoveTable.movesList){


            if(!keyNumber.isWrong) {
                x = new FXMLLoader(getClass().getResource("/fxml/test/parserLLWindow/parserLLMovesOutput.fxml"));
                parserLLOutputList.getItems().add(x.load());
                parserLLMovesOutputController xControler = x.getController();
                //Input
                xControler.setInput(keyNumber.input);

                //Stack
                xControler.setStack(keyNumber.stack);

                //Output
                xControler.setOutput(keyNumber.output);

            } else {
                x = new FXMLLoader(getClass().getResource("/fxml/test/parserLLWindow/parserLLMovesErrorOutput.fxml"));
                parserLLOutputList.getItems().add(x.load());
                parserLLMovesErrorOutputController xControler = x.getController();
                //Input
                xControler.setError(keyNumber.errorInformation);
            }
        }
        parserLLOutputPane.getChildren().add(parserLLOutputList);





    }

    void showElement(Boolean show){
        parserLLButtonVBox.setVisible(show);
        parserLLMovesButton.setVisible(show);
        parserLLInputStringLabel.setVisible(show);
        parserLLPartialSolutionsLabel.setVisible(show);
        movesTableInput.setVisible(show);

    }
}
