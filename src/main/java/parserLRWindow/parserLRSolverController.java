package parserLRWindow;

import commonUtility.CommonUtility;
import firstFollow.FirstFollow;
import firstFollowWindow.FirstFollowOutputFirstController;
import firstFollowWindow.FirstFollowOutputFollowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import parserLLWindow.parserLLInputController;
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
public class parserLRSolverController implements Initializable {

    @FXML
    ListView parserLRInputList;
    @FXML
    StackPane parserLROutputPane;
    @FXML
    TextField movesTableInput;

    List<parserLRInputController> listInput = new ArrayList<>();

    ParserLR testGotoGenerator;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/parserLRWindow/parserLRInput.fxml"));

        try {
            //x.load();
            parserLRInputList.getItems().add(x.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        listInput.add(x.getController());
        Button b = new Button("+");
        b.setOnAction(handle -> {
            FXMLLoader x1 = new FXMLLoader(getClass().getResource("/fxml/test/parserLRWindow/parserLRInput.fxml"));
            try {
                parserLRInputList.getItems().add(parserLRInputList.getItems().size() - 1, x1.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            listInput.add(x1.getController());
        });
        parserLRInputList.getItems().add(b);
    }

    public void generateLR(ActionEvent actionEvent) {

        List<String> inputLineList = new ArrayList<>();


        // Correct
//
//        String tempString;
//        for(Integer i = 0; i < listInput.size(); i++) {
//            //System.out.println(listInput.get(i).getLeftPart());
//            System.out.println(listInput.get(i).productionLeftPart);
//            tempString =  listInput.get(i).getLeftPart() + " -> " + listInput.get(i).getRightPart();
//            System.out.println(tempString);
//            inputLineList.add(tempString);
//        }

        //

        // Fortest

        inputLineList.add("S -> A");
        inputLineList.add("S -> S + A");
        inputLineList.add("A -> B ");
        inputLineList.add("A -> A * B");
        inputLineList.add("B -> a | ( S )");

        //
        if(!inputLineList.isEmpty()) {
            testGotoGenerator = new ParserLR(inputLineList);
//            testFirstFollow = new FirstFollow(inputLineList);
//            testFirstFollow.generateSolutionSet();
//            testFirstFollow.predictiveMap.generatePredictiveMap(testFirstFollow.parsedSet, testFirstFollow.firstElementMap, testFirstFollow.followElementMap);
        } else {
            System.out.println("Empty list");
        }

    }

    public void printFirst(ActionEvent actionEvent) throws IOException {
        parserLROutputPane.getChildren().clear();
        ListView parserLROutputList = new ListView();
        for(String keyFirst: testGotoGenerator.firstFollowSolution.firstElementMap.keySet() ){
            FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowOutputFirst.fxml"));
            parserLROutputList.getItems().add(x.load());
            FirstFollowOutputFirstController xControler = x.getController();
            xControler.setSymbolPart(keyFirst);
            String solutionString = "";
            for(String temp: testGotoGenerator.firstFollowSolution.firstElementMap.get(keyFirst).firstSet) {
                solutionString += temp + ", ";
            }
            if(solutionString.length() > 2) {
                solutionString = solutionString.substring(0, solutionString.length()-2);
            }
            xControler.setSolutionPart(solutionString);
        }
        parserLROutputPane.getChildren().add(parserLROutputList);
    }

    public void printFollow(ActionEvent actionEvent) throws IOException {
        parserLROutputPane.getChildren().clear();
        ListView parserLROutputList = new ListView();
        for(String keyFollow: testGotoGenerator.firstFollowSolution.followElementMap.keySet() ){
            FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowOutputFollow.fxml"));
            parserLROutputList.getItems().add(x.load());
            FirstFollowOutputFollowController xControler = x.getController();

            xControler.setSymbolPart(keyFollow);
            String solutionString = "";
            for(String temp: testGotoGenerator.firstFollowSolution.followElementMap.get(keyFollow).followSet) {
                solutionString += temp + ", ";
            }
            if(solutionString.length() > 2) {
                solutionString = solutionString.substring(0, solutionString.length()-2);
            }

            xControler.setSolutionPart(solutionString);
        }
        parserLROutputPane.getChildren().add(parserLROutputList);
    }

    public void printActionTable(ActionEvent actionEvent) {
        parserLROutputPane.getChildren().clear();

        GridPane gridPane = new GridPane();


        Label tempLabel;

        HashMap columnSign = new HashMap<String, Integer>();
        Integer maxColumn = 1;

        //add "No" if position 0,0
        gridPane.add(new Label("No"), 0,0);

        Integer rowNumber = 1;

        for(Integer row: testGotoGenerator.actionTable.keySet()) {
            //add first element in row index
            tempLabel = new Label(row.toString());
            gridPane.add(tempLabel,0, rowNumber);
            for(String column: testGotoGenerator.actionTable.get(row).keySet()) {
                if(!columnSign.containsKey(column)){
                    columnSign.put(column,maxColumn++);
                    gridPane.add(new Label(column), (Integer) columnSign.get(column),0);
                }
                tempLabel = new Label(testGotoGenerator.actionTable.get(row).get(column).value.toString());
                gridPane.add(tempLabel, (Integer) columnSign.get(column),rowNumber);
            }
            rowNumber++;
        }
        parserLROutputPane.getChildren().add(gridPane);

    }


    public void printGotoTable(ActionEvent actionEvent) {
        parserLROutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();
        Label tempLabel;

        HashMap columnSign = new HashMap<String, Integer>();
        Integer maxColumn = 1;

        //add "No" if position 0,0
        tempLabel = new Label("No");
        gridPane.add(tempLabel, 0,0);

        Integer rowNumber = 1;

        for(Integer row: testGotoGenerator.gotoTable.keySet()) {
            //add first element in row index
            tempLabel = new Label(row.toString());
            gridPane.add(tempLabel,0, rowNumber);
            for(String column: testGotoGenerator.gotoTable.get(row).keySet()) {
                if(!columnSign.containsKey(column)){
                    columnSign.put(column,maxColumn++);
                    tempLabel = new Label(column);
                    gridPane.add(tempLabel, (Integer) columnSign.get(column),0);
                }
                tempLabel = new Label(testGotoGenerator.gotoTable.get(row).get(column).toString());
                gridPane.add(tempLabel, (Integer) columnSign.get(column),rowNumber);
            }
            rowNumber++;
        }


        parserLROutputPane.getChildren().add(gridPane);
    }



    public void printMovesTable(ActionEvent actionEvent) throws IOException {

        if(movesTableInput.getText() != null && !movesTableInput.getText().trim().isEmpty()) {
            testGotoGenerator.generateLRParser("a * a + a");

        } else {
            System.out.println("Input Empty");
        }





        parserLROutputPane.getChildren().clear();
        ListView parserLROutputList = new ListView();
        FXMLLoader xStart = new FXMLLoader(getClass().getResource("/fxml/test/parserLRWindow/parserLRMovesOutput.fxml"));
        parserLROutputList.getItems().add(xStart.load());
        parserLRMovesOutputController xControlerStart = xStart.getController();
        xControlerStart.setMoveNo("No");
        xControlerStart.setInput("Input");
        xControlerStart.setStack("Stack");
        for(MovesElementLR keyNumber: testGotoGenerator.movesList){
            FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/parserLRWindow/parserLRMovesOutput.fxml"));
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

        }
        parserLROutputPane.getChildren().add(parserLROutputList);

    }
}
