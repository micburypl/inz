package parserLRWindow;

import commonUtility.CommonUtility;
import firstFollowTestData.FirstFollowTestMainSet;
import firstFollowTestData.FirstFollowTestSet;
import firstFollowTestData.FirstFollowTestSetElement;
import firstFollowWindow.FirstFollowInputController;
import firstFollowWindow.FirstFollowInputExercisesController;
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
import parserLR.MovesElementLR;
import parserLR.ParserLR;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by DELL6430u on 2017-05-01.
 */
public class parserLRExercisesController implements Initializable {

    @FXML
    ListView parserLRInputList;
    @FXML
    StackPane parserLROutputPane;
    @FXML
    TextField movesTableInput;
    @FXML
    VBox parserLRButtonVBox;
    @FXML
    Button parserLRMovesButton;
    @FXML
    Label parserLRInputStringLabel;
    @FXML
    Label parserLRPartialSolutionsLabel;
    @FXML
    Button parserLRVerifyButton;
    @FXML
    Button parserLRRandomMoves;

    List<FirstFollowInputExercisesController> listInput = new ArrayList<>();

    ParserLR testGotoGenerator;

    FirstFollowTestSet tempSet;
    FirstFollowTestMainSet tempElement;
    Integer lastValue;
    Integer lastValueMoves;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowExercisesInput.fxml"));

//

        listInput.add(x.getController());
        Button b = new Button("+");
        b.setOnAction(handle -> {
            FXMLLoader x1 = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowExercisesInput.fxml"));
            try {
                parserLRInputList.getItems().add(parserLRInputList.getItems().size() - 1, x1.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            listInput.add(x1.getController());
        });
        //parserLRInputList.getItems().add(b);

        //parserLRButtonVBox.setVisible(false);
        showElement(false);
    }

    public void randomInput(ActionEvent actionEvent) throws IOException {
        tempSet = new FirstFollowTestSet(true);
        lastValue = tempSet.testData(lastValue);
        tempElement = tempSet.InputListTestList.get(lastValue);
        parserLRInputList.getItems().clear();
        listInput = new ArrayList<>();
        for(FirstFollowTestSetElement temp : tempElement.InputListTest){
            FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowExercisesInput.fxml"));


            parserLRInputList.getItems().add(x.load());
            FirstFollowInputExercisesController elementContorller = x.getController();

            elementContorller.setLeftPart(temp.leftPart);
            elementContorller.setRightPart(temp.rightPart);
            listInput.add(x.getController());
        }

        showElement(false);
        lastValueMoves = -1;
        movesTableInput.clear();
    }

    public void randomMovesInput(ActionEvent actionEvent) {
        lastValueMoves = tempElement.testMovesData(lastValueMoves);
        String movesString = tempElement.movesTestList.get(lastValueMoves);
        movesTableInput.setText(movesString);

    }

    public void generateLR(ActionEvent actionEvent) {

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

//        inputLineList.add("S -> A");
//        inputLineList.add("S -> S + A");
//        inputLineList.add("A -> B ");
//        inputLineList.add("A -> A * B");
//        inputLineList.add("B -> a | ( S )");

        //
//        if(!inputLineList.isEmpty()) {
            testGotoGenerator = new ParserLR(inputLineList);
        if(testGotoGenerator.firstFollowSolution.errorFlag) {
            //blocked buttons
            //parserLRButtonVBox.setVisible(false);
            showElement(false);

            parserLROutputPane.getChildren().clear();
            GridPane gridPane = new GridPane();
            gridPane.setGridLinesVisible(true);
            Label tempLabel = new Label("Errors");
            gridPane.add(tempLabel, 0,0);
            gridPane.setHalignment(tempLabel, HPos.CENTER);
            Integer tempInt = 1;
            for(Integer errorLine: testGotoGenerator.firstFollowSolution.errorMessages.keySet() ) {
                tempLabel = new Label("In row " + errorLine + ". " + testGotoGenerator.firstFollowSolution.errorMessages.get(errorLine));
                gridPane.add(tempLabel, 0, tempInt++);
                gridPane.setHalignment(tempLabel, HPos.CENTER);
            }

            parserLROutputPane.getChildren().add(gridPane);
            return;
        }

        //show buttons
        //parserLRButtonVBox.setVisible(true);
        showElement(true);
        parserLROutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();
        parserLROutputPane.getChildren().add(gridPane);

//            testFirstFollow = new FirstFollow(inputLineList);
//            testFirstFollow.generateSolutionSet();
//            testFirstFollow.predictiveMap.generatePredictiveMap(testFirstFollow.parsedSet, testFirstFollow.firstElementMap, testFirstFollow.followElementMap);
//        } else {
//            System.out.println("Empty list");
//        }

    }

    public void printFirst(ActionEvent actionEvent) throws IOException {

        parserLROutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();

        Label tempLabel = new Label("Element");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("First(Element)");
        gridPane.add(tempLabel, 1,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(String keyFirst: testGotoGenerator.firstFollowSolution.firstElementMap.keySet() ){

            tempLabel = new Label(keyFirst);
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            String solutionString = "";
            for(String temp:testGotoGenerator.firstFollowSolution.firstElementMap.get(keyFirst).firstSet) {
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

        parserLROutputPane.getChildren().add(gridPane);
    }

    public void printFollow(ActionEvent actionEvent) throws IOException {


        parserLROutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();

        Label tempLabel = new Label("Element");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("Follow(Element)");
        gridPane.add(tempLabel, 1,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(String keyFollow: testGotoGenerator.firstFollowSolution.followElementMap.keySet() ){

            tempLabel = new Label(keyFollow);
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            String solutionString = "";
            for(String temp: testGotoGenerator.firstFollowSolution.followElementMap.get(keyFollow).followSet) {
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

        parserLROutputPane.getChildren().add(gridPane);
    }

    public void printActionTable(ActionEvent actionEvent) {
        parserLROutputPane.getChildren().clear();

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        HashMap columnSign = new HashMap<String, Integer>();
        Integer maxColumn = 1;

        //add "No" if position 0,0

        Label tempLabel = new Label("No");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        Integer rowNumber = 1;

        for(Integer row: testGotoGenerator.actionTable.keySet()) {
            //add first element in row index

            tempLabel = new Label(row.toString());
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            for(String column: testGotoGenerator.actionTable.get(row).keySet()) {
                if(!columnSign.containsKey(column)){
                    columnSign.put(column,maxColumn++);

                    tempLabel = new Label(column);
                    gridPane.add(tempLabel, (Integer) columnSign.get(column),0);
                    gridPane.setHalignment(tempLabel, HPos.CENTER);

                }

                tempLabel = new Label(testGotoGenerator.actionTable.get(row).get(column).value.toString());
                gridPane.add(tempLabel, (Integer) columnSign.get(column),rowNumber);
                gridPane.setHalignment(tempLabel, HPos.CENTER);
            }
            rowNumber++;
        }
        parserLROutputPane.getChildren().add(gridPane);

        Integer numberOfColumn = maxColumn;
        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane.getColumnConstraints().add(column);
        }

    }


    public void printGotoTable(ActionEvent actionEvent) {
        parserLROutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        HashMap columnSign = new HashMap<String, Integer>();
        Integer maxColumn = 1;

        //add "No" if position 0,0
        Label tempLabel = new Label("No");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        Integer rowNumber = 1;

        for(Integer row: testGotoGenerator.gotoTable.keySet()) {
            //add first element in row index
            tempLabel = new Label(row.toString());
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            for(String column: testGotoGenerator.gotoTable.get(row).keySet()) {
                if(!columnSign.containsKey(column)){
                    columnSign.put(column,maxColumn++);
                    tempLabel = new Label(column);
                    gridPane.add(tempLabel, (Integer) columnSign.get(column),0);
                    gridPane.setHalignment(tempLabel, HPos.CENTER);

                }
                tempLabel = new Label(testGotoGenerator.gotoTable.get(row).get(column).toString());
                gridPane.add(tempLabel, (Integer) columnSign.get(column),rowNumber);
                gridPane.setHalignment(tempLabel, HPos.CENTER);

            }
            rowNumber++;
        }

        parserLROutputPane.getChildren().add(gridPane);

        Integer numberOfColumn = maxColumn;
        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane.getColumnConstraints().add(column);
        }
    }



    public void printMovesTable(ActionEvent actionEvent) throws IOException {
        FXMLLoader x;
        parserLROutputPane.getChildren().clear();
        ListView parserLROutputList = new ListView();

        if(movesTableInput.getText() != null && !movesTableInput.getText().trim().isEmpty()) {
            //testGotoGenerator.generateLRParser("a * a + a");
            testGotoGenerator.generateLRParser(movesTableInput.getText());

        } else {
            x = new FXMLLoader(getClass().getResource("/fxml/test/parserLRWindow/parserLRMovesErrorOutput.fxml"));
            parserLROutputList.getItems().add(x.load());
            parserLRMovesErrorOutputController xControler = x.getController();
            //errorMessage
            xControler.setError("Input is Empty");
            System.out.println("Input is Empty");
            parserLROutputPane.getChildren().add(parserLROutputList);
            return;
        }
        FXMLLoader xStart = new FXMLLoader(getClass().getResource("/fxml/test/parserLRWindow/parserLRMovesOutput.fxml"));
        parserLROutputList.getItems().add(xStart.load());
        parserLRMovesOutputController xControlerStart = xStart.getController();
        xControlerStart.setMoveNo("No");
        xControlerStart.setInput("Input");
        xControlerStart.setStack("Stack");
        for(MovesElementLR keyNumber: testGotoGenerator.movesList){

            if(!keyNumber.isWrong) {
                x = new FXMLLoader(getClass().getResource("/fxml/test/parserLRWindow/parserLRMovesOutput.fxml"));
                parserLROutputList.getItems().add(x.load());
                parserLRMovesOutputController xControler = x.getController();

                //Input
                String tempInput = "";
                for(String tempString: keyNumber.input) {
                    tempInput += tempString;
                }
                tempInput = tempInput.substring(0, tempInput.length() - 3 ) + CommonUtility.dolarValue;
                xControler.setInput(tempInput);

                //Stack
                String tempStack = "";
                for(String tempString: keyNumber.stack) {
                    tempStack += tempString;
                }
                xControler.setStack(tempStack);

                //movesNo
                xControler.setMoveNo(keyNumber.movesNumber.toString());
            } else {
                x = new FXMLLoader(getClass().getResource("/fxml/test/parserLRWindow/parserLRMovesErrorOutput.fxml"));
                parserLROutputList.getItems().add(x.load());
                parserLRMovesErrorOutputController xControler = x.getController();
                //errorMessage
                xControler.setError(keyNumber.errorMessage);
                break;
            }

        }
        parserLROutputPane.getChildren().add(parserLROutputList);

    }

    void showElement(Boolean show){
        parserLRButtonVBox.setVisible(show);
        parserLRMovesButton.setVisible(show);
        parserLRInputStringLabel.setVisible(show);
        parserLRPartialSolutionsLabel.setVisible(show);
        movesTableInput.setVisible(show);
        parserLRVerifyButton.setVisible(show);
        parserLRRandomMoves.setVisible(show);
    }
}
