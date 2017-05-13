package parserLRWindow;

import commonUtility.CommonUtility;
import firstFollowTestData.FirstFollowTestMainSet;
import firstFollowTestData.FirstFollowTestSet;
import firstFollowTestData.FirstFollowTestSetElement;
import firstFollowWindow.FirstFollowInputController;
import firstFollowWindow.FirstFollowInputExercisesController;
import graph.GraphMethods;
import javafx.embed.swing.SwingNode;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import parserLR.GotoTransition;
import parserLR.MovesElementLR;
import parserLR.ParserLR;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

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

    GridPane gridPane;
    GridPane gridPane2;

    ParserLR testGotoGenerator;

    FirstFollowTestSet tempSet;
    FirstFollowTestMainSet tempElement;
    Integer lastValue;
    Integer lastValueMoves;
    Integer rowSize;
    Integer columnSize;
    Boolean removeSpace;
    Boolean isImportedFile = false;
    Integer currentImportedInput;
    Boolean showRandomMovesButton = false;

    ArrayList<String> importedInputList;

    FileChooser fileChooser = new FileChooser();
    private Desktop desktop = Desktop.getDesktop();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowExercisesInput.fxml"));

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

        isImportedFile = false;

        tempSet = new FirstFollowTestSet(true);
        parserLROutputPane.getChildren().clear();

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
        if(!isImportedFile) {
            lastValueMoves = tempElement.testMovesData(lastValueMoves);
            String movesString = tempElement.movesTestList.get(lastValueMoves);
            movesTableInput.setText(movesString);
        } else {
            Integer i;
            Random generator = new Random();
            do{
                i = generator.nextInt(importedInputList.size());
            } while(currentImportedInput == i);

            currentImportedInput = i;
            movesTableInput.setText(importedInputList.get(i));
        }

    }

    public void generateLR(ActionEvent actionEvent) {

        List<String> inputLineList = new ArrayList<>();
        showRandomMovesButton = true;
        // Correct

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
            GridPane.setHalignment(tempLabel, HPos.CENTER);
            Integer tempInt = 1;
            for(Integer errorLine: testGotoGenerator.firstFollowSolution.errorMessages.keySet() ) {
                tempLabel = new Label("In row " + errorLine + ". " + testGotoGenerator.firstFollowSolution.errorMessages.get(errorLine));
                gridPane.add(tempLabel, 0, tempInt++);
                GridPane.setHalignment(tempLabel, HPos.CENTER);
            }

            parserLROutputPane.getChildren().add(gridPane);
            return;
        }

        //show buttons
        //parserLRButtonVBox.setVisible(true);
        showElement(true);
        showVerifyButton(false);
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

        showVerifyButton(true);
        parserLROutputPane.getChildren().clear();
        gridPane = new GridPane();

        Label tempLabel = new Label("Element");
        gridPane.add(tempLabel, 0,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("First(Element)");
        gridPane.add(tempLabel, 1,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(String keyFirst: testGotoGenerator.firstFollowSolution.firstElementMap.keySet() ){

            tempLabel = new Label(keyFirst);
            gridPane.add(tempLabel, 0,rowNumber);
            GridPane.setHalignment(tempLabel, HPos.CENTER);

            String solutionString = "";
            for(String temp:testGotoGenerator.firstFollowSolution.firstElementMap.get(keyFirst).firstSet) {
                solutionString += temp + ", ";
            }
            if(solutionString.length() > 2) {
                solutionString = solutionString.substring(0, solutionString.length()-2);
            }

            tempLabel = new Label(solutionString);
            gridPane.add(tempLabel, 1,rowNumber);
            GridPane.setHalignment(tempLabel, HPos.CENTER);

            rowNumber++;
        }

        Integer numberOfColumn = 2;
        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane.getColumnConstraints().add(column);
        }

        //input
        removeSpace = true;
        TextField tempTextField;
        gridPane2 = new GridPane();
        gridPane2.setGridLinesVisible(true);
        tempLabel = new Label("Element");
        gridPane2.add(tempLabel, 0,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("First(Element)");
        gridPane2.add(tempLabel, 1,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);


        rowNumber = 1;

        for(String keyFollow: testGotoGenerator.firstFollowSolution.firstElementMap.keySet() ){


            tempLabel = new Label(keyFollow);
            gridPane2.add(tempLabel, 0,rowNumber);
            GridPane.setHalignment(tempLabel, HPos.CENTER);

//            String solutionString = "";
//            for(String temp: testGotoGenerator.firstFollowSolution.firstElementMap.get(keyFollow).firstSet) {
//                solutionString += temp + ", ";
//            }
//            if(solutionString.length() > 2) {
//                solutionString = solutionString.substring(0, solutionString.length()-2);
//            }

            tempTextField = new TextField();
            gridPane2.add(tempTextField, 1,rowNumber);
            GridPane.setHalignment(tempTextField, HPos.CENTER);

            rowNumber++;

        }
        numberOfColumn = 2;
        columnSize = numberOfColumn;
        rowSize = rowNumber;

        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane2.getColumnConstraints().add(column);
        }

        parserLROutputPane.getChildren().add(gridPane2);
    }

    public void printFollow(ActionEvent actionEvent) throws IOException {

        showVerifyButton(true);
        parserLROutputPane.getChildren().clear();
        gridPane = new GridPane();

        Label tempLabel = new Label("Element");
        gridPane.add(tempLabel, 0,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("Follow(Element)");
        gridPane.add(tempLabel, 1,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(String keyFollow: testGotoGenerator.firstFollowSolution.followElementMap.keySet() ){

            tempLabel = new Label(keyFollow);
            gridPane.add(tempLabel, 0,rowNumber);
            GridPane.setHalignment(tempLabel, HPos.CENTER);

            String solutionString = "";
            for(String temp: testGotoGenerator.firstFollowSolution.followElementMap.get(keyFollow).followSet) {
                solutionString += temp + ", ";
            }
            if(solutionString.length() > 2) {
                solutionString = solutionString.substring(0, solutionString.length()-2);
            }

            tempLabel = new Label(solutionString);
            gridPane.add(tempLabel, 1,rowNumber);
            GridPane.setHalignment(tempLabel, HPos.CENTER);

            rowNumber++;
        }

        Integer numberOfColumn = 2;
        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane.getColumnConstraints().add(column);
        }

        //input

        removeSpace = true;
        gridPane2 = new GridPane();

        TextField tempTextField;

        tempLabel = new Label("Element");
        gridPane2.add(tempLabel, 0,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("Follow(Element)");
        gridPane2.add(tempLabel, 1,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        rowNumber = 1;
        gridPane2.setGridLinesVisible(true);
        for(String keyFollow: testGotoGenerator.firstFollowSolution.followElementMap.keySet() ){


            tempLabel = new Label(keyFollow);
            gridPane2.add(tempLabel, 0,rowNumber);
            GridPane.setHalignment(tempLabel, HPos.CENTER);

            tempTextField = new TextField("");
            gridPane2.add(tempTextField, 1,rowNumber);
            GridPane.setHalignment(tempTextField, HPos.CENTER);

            rowNumber++;

        }

        numberOfColumn = 2;
        columnSize = numberOfColumn;
        rowSize = rowNumber;


        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane2.getColumnConstraints().add(column);
        }

        parserLROutputPane.getChildren().add(gridPane2);
    }

    public void printActionTable(ActionEvent actionEvent) {

        showVerifyButton(true);
        parserLROutputPane.getChildren().clear();
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        HashMap columnSign = new HashMap<String, Integer>();
        Integer maxColumn = 1;

        //add "No" if position 0,0

        Label tempLabel = new Label("No");
        gridPane.add(tempLabel, 0,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        Integer rowNumber = 1;

        for(Integer row: testGotoGenerator.actionTable.keySet()) {
            //add first element in row index

            tempLabel = new Label(row.toString());
            gridPane.add(tempLabel, 0,rowNumber);
            GridPane.setHalignment(tempLabel, HPos.CENTER);

            for(String column: testGotoGenerator.actionTable.get(row).keySet()) {
                if(!columnSign.containsKey(column)){
                    columnSign.put(column,maxColumn++);

                    tempLabel = new Label(column);
                    gridPane.add(tempLabel, (Integer) columnSign.get(column),0);
                    GridPane.setHalignment(tempLabel, HPos.CENTER);

                }

                tempLabel = new Label(testGotoGenerator.actionTable.get(row).get(column).value.toString());
                gridPane.add(tempLabel, (Integer) columnSign.get(column),rowNumber);
                GridPane.setHalignment(tempLabel, HPos.CENTER);
            }
            rowNumber++;
        }


        Integer numberOfColumn = maxColumn;
        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane.getColumnConstraints().add(column);
        }

        columnSize = numberOfColumn;
        rowSize = rowNumber;

        //input
        removeSpace = false;
        gridPane2 = new GridPane();
        TextField tempTextField;
        tempLabel = new Label("No");
        gridPane2.add(tempLabel, 0,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        columnSign = new HashMap<String, Integer>();
        maxColumn = 1;
        rowNumber = 1;
        gridPane2.setGridLinesVisible(true);

        for(Integer i = 0; i < columnSize; i++) {
            for(Integer j = 0; j < rowSize; j++) {

                if(i == 0 || j == 0) {
                    tempLabel = new Label(CommonUtility.getNodeFromGridPane(gridPane, i, j));
                    gridPane2.add(tempLabel, i,j);
                    GridPane.setHalignment(tempLabel, HPos.CENTER);
                    continue;
                }
                tempTextField = new TextField("");
                gridPane2.add(tempTextField, i,j);
                GridPane.setHalignment(tempTextField, HPos.CENTER);

            }
        }

        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane2.getColumnConstraints().add(column);
        }

        parserLROutputPane.getChildren().add(gridPane2);

    }


    public void printGotoTable(ActionEvent actionEvent) {

        showVerifyButton(true);
        parserLROutputPane.getChildren().clear();
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        HashMap columnSign = new HashMap<String, Integer>();
        Integer maxColumn = 1;

        //add "No" if position 0,0
        Label tempLabel = new Label("No");
        gridPane.add(tempLabel, 0,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        Integer rowNumber = 1;

        for(Integer row: testGotoGenerator.gotoTable.keySet()) {
            //add first element in row index
            tempLabel = new Label(row.toString());
            gridPane.add(tempLabel, 0,rowNumber);
            GridPane.setHalignment(tempLabel, HPos.CENTER);

            for(String column: testGotoGenerator.gotoTable.get(row).keySet()) {
                if(!columnSign.containsKey(column)){
                    columnSign.put(column,maxColumn++);
                    tempLabel = new Label(column);
                    gridPane.add(tempLabel, (Integer) columnSign.get(column),0);
                    GridPane.setHalignment(tempLabel, HPos.CENTER);
                }
                tempLabel = new Label(testGotoGenerator.gotoTable.get(row).get(column).toString());
                gridPane.add(tempLabel, (Integer) columnSign.get(column),rowNumber);
                GridPane.setHalignment(tempLabel, HPos.CENTER);
            }
            rowNumber++;
        }



        Integer numberOfColumn = maxColumn;
        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane.getColumnConstraints().add(column);
        }

        columnSize = numberOfColumn;
        rowSize = rowNumber;

        //input
        removeSpace = false;
        gridPane2 = new GridPane();
        TextField tempTextField;
        tempLabel = new Label("No");
        gridPane2.add(tempLabel, 0,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        columnSign = new HashMap<String, Integer>();
        gridPane2.setGridLinesVisible(true);

        for(Integer i = 0; i < columnSize; i++) {
            for(Integer j = 0; j < rowSize; j++) {

                if(i == 0 || j == 0) {
                    tempLabel = new Label(CommonUtility.getNodeFromGridPane(gridPane, i, j));
                    gridPane2.add(tempLabel, i,j);
                    GridPane.setHalignment(tempLabel, HPos.CENTER);
                    continue;
                }
                tempTextField = new TextField("");
                gridPane2.add(tempTextField, i,j);
                GridPane.setHalignment(tempTextField, HPos.CENTER);

            }
        }

        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane2.getColumnConstraints().add(column);
        }

        parserLROutputPane.getChildren().add(gridPane2);
    }



    public void printMovesTable(ActionEvent actionEvent) throws IOException {

        showVerifyButton(false); // będzie do zmiany
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

    public void printTransitionTable(ActionEvent actionEvent) throws IOException {

        showVerifyButton(true);
        parserLROutputPane.getChildren().clear();
        gridPane = new GridPane();

        Label tempLabel = new Label("Begin State");
        gridPane.add(tempLabel, 0,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("Transition");
        gridPane.add(tempLabel, 1,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("End State");
        gridPane.add(tempLabel, 2,0);
        GridPane.setHalignment(tempLabel, HPos.CENTER);

        String tempString;
        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(GotoTransition element: testGotoGenerator.gotoGeneratorMap.gotoTransitionList){

            tempLabel = new Label(element.from.toString());
            gridPane.add(tempLabel, 0,rowNumber);
            GridPane.setHalignment(tempLabel, HPos.CENTER);

            tempLabel = new Label(element.value);
            gridPane.add(tempLabel, 1,rowNumber);
            GridPane.setHalignment(tempLabel, HPos.CENTER);

            tempLabel = new Label(element.to.toString());
            gridPane.add(tempLabel, 2,rowNumber);
            GridPane.setHalignment(tempLabel, HPos.CENTER);

            rowNumber++;
        }

        Integer numberOfColumn = 3;
        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane.getColumnConstraints().add(column);
        }

        columnSize = numberOfColumn;
        rowSize = rowNumber;

        //input
        removeSpace = false;
        gridPane2 = new GridPane();
        TextField tempTextField;

        gridPane2.setGridLinesVisible(true);

        for(Integer i = 0; i < columnSize; i++) {
            for(Integer j = 0; j < rowSize; j++) {

                if(i == 0 || j == 0 || i == 1) {
                    tempLabel = new Label(CommonUtility.getNodeFromGridPane(gridPane, i, j));
                    gridPane2.add(tempLabel, i,j);
                    GridPane.setHalignment(tempLabel, HPos.CENTER);
                    continue;
                }
                tempTextField = new TextField("");
                gridPane2.add(tempTextField, i,j);
                GridPane.setHalignment(tempTextField, HPos.CENTER);

            }
        }

        for(Integer i = 0; i < numberOfColumn; i++) {

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numberOfColumn);
            gridPane2.getColumnConstraints().add(column);
        }

        parserLROutputPane.getChildren().add(gridPane2);
    }

    public void printGraph(ActionEvent actionEvent) {

        parserLROutputPane.getChildren().clear();

        SwingNode swingComponentWrapper = new SwingNode();
        HashMap<String, Boolean> tempList = new HashMap<>();

        for(Integer tempInt: testGotoGenerator.gotoGeneratorMap.gotoElementMap.keySet()){
            tempList.put(tempInt.toString(), false);
        }

        GraphMethods tempGraph = new GraphMethods(tempList, testGotoGenerator.gotoGeneratorMap.gotoTransitionList);

        swingComponentWrapper.setContent(tempGraph.graphComponent);

        parserLROutputPane.getChildren().add(swingComponentWrapper);
    }

    private void showElement(Boolean show){
        parserLRButtonVBox.setVisible(show);
        parserLRMovesButton.setVisible(show);
        parserLRInputStringLabel.setVisible(show);
        parserLRPartialSolutionsLabel.setVisible(show);
        movesTableInput.setVisible(show);
        parserLRVerifyButton.setVisible(show);
        if(!isImportedFile) {
            parserLRRandomMoves.setVisible(show);
        } else  if(!importedInputList.isEmpty() && showRandomMovesButton){
            parserLRRandomMoves.setVisible(true);
        } else {
            parserLRRandomMoves.setVisible(false);
        }
    }

    private void showVerifyButton(Boolean show){
        parserLRVerifyButton.setVisible(show);
    }

    public void parserLLExerciseVerify(ActionEvent actionEvent) {
        Boolean correct = CommonUtility.compareGridPane(gridPane, gridPane2, rowSize, columnSize, removeSpace);
        parserLROutputPane.getChildren().clear();
        parserLROutputPane.getChildren().add(gridPane2);
    }


    public void openFile(ActionEvent actionEvent) throws IOException {
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            openFile(file);
        }
    }

    void openFile(File file) throws IOException {

        parserLRInputList.getItems().clear();
        currentImportedInput = -1;
        showRandomMovesButton = false;
        listInput = new ArrayList<>();
        importedInputList = new ArrayList<>();
        String[] inputWord;
        String tempString;
        isImportedFile = true;
        for (String line : Files.readAllLines(Paths.get(file.getAbsolutePath()))) {

            FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowExercisesInput.fxml"));



            tempString = line;
            if(tempString.replaceAll(" ","").isEmpty()){
                continue;
            }

            if(line.contains("->")) {
                //production
                inputWord = line.split("->");
                parserLRInputList.getItems().add(x.load());
                FirstFollowInputExercisesController elementContorller = x.getController();
                elementContorller.setLeftPart(inputWord[0]);
                elementContorller.setRightPart(inputWord[1]);
                listInput.add(x.getController());
            } else {
                //inputString
                importedInputList.add(line);
            }

        }
        showElement(false);
        parserLROutputPane.getChildren().clear();

    }
}
