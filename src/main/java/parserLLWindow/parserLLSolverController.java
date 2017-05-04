package parserLLWindow;

import com.sun.org.apache.bcel.internal.generic.RETURN;
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

    List<parserLLInputController> listInput = new ArrayList<>();

    FirstFollow testFirstFollow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/parserLLWindow/parserLLInput.fxml"));

        try {
            //x.load();
            parserLLInputList.getItems().add(x.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        listInput.add(x.getController());
        Button b = new Button("+");
        b.setOnAction(handle -> {
            FXMLLoader x1 = new FXMLLoader(getClass().getResource("/fxml/test/parserLLWindow/parserLLInput.fxml"));
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

        inputLineList.add("S -> A S'");
        inputLineList.add("S' -> + A S' | eps");
        inputLineList.add("A -> B A' ");
        inputLineList.add("A' -> * B A' | eps");
        inputLineList.add("B -> ( S ) | a");

        //
        if(!inputLineList.isEmpty()) {
            testFirstFollow = new FirstFollow(inputLineList);
            testFirstFollow.generateSolutionSet();
            testFirstFollow.predictiveMap.generatePredictiveMap(testFirstFollow.parsedSet, testFirstFollow.firstElementMap, testFirstFollow.followElementMap);
        } else {
            System.out.println("Empty list");
        }

    }

    public void printFirst(ActionEvent actionEvent) throws IOException {
        parserLLOutputPane.getChildren().clear();
        ListView parserLLOutputList = new ListView();
        for(String keyFirst: testFirstFollow.firstElementMap.keySet() ){
            FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowOutputFirst.fxml"));
            parserLLOutputList.getItems().add(x.load());
            FirstFollowOutputFirstController xControler = x.getController();
            xControler.setSymbolPart(keyFirst);
            String solutionString = "";
            for(String temp: testFirstFollow.firstElementMap.get(keyFirst).firstSet) {
                solutionString += temp + ", ";
            }
            if(solutionString.length() > 2) {
                solutionString = solutionString.substring(0, solutionString.length()-2);
            }
            xControler.setSolutionPart(solutionString);
        }
        parserLLOutputPane.getChildren().add(parserLLOutputList);
    }

    public void printFollow(ActionEvent actionEvent) throws IOException {
        parserLLOutputPane.getChildren().clear();
        ListView parserLLOutputList = new ListView();
        for(String keyFollow: testFirstFollow.followElementMap.keySet() ){
            FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowOutputFollow.fxml"));
            parserLLOutputList.getItems().add(x.load());
            FirstFollowOutputFollowController xControler = x.getController();

            xControler.setSymbolPart(keyFollow);
            String solutionString = "";
            for(String temp: testFirstFollow.followElementMap.get(keyFollow).followSet) {
                solutionString += temp + ", ";
            }
            if(solutionString.length() > 2) {
                solutionString = solutionString.substring(0, solutionString.length()-2);
            }

            xControler.setSolutionPart(solutionString);
        }
        parserLLOutputPane.getChildren().add(parserLLOutputList);
    }

    public void printParsingTable(ActionEvent actionEvent) {

        parserLLOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Prod"), 0,0);
        Label tempLabel;
        String tempString;
        Boolean firstElement;

        HashMap columnSign = new HashMap<String, Integer>();
        Integer maxColumn = 1;
        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(String keyNumber: testFirstFollow.predictiveMap.predictiveMap.keySet()) {

            gridPane.add(new Label(keyNumber), 0,rowNumber);

            for(String column: testFirstFollow.predictiveMap.predictiveMap.get(keyNumber).keySet()) {
                if(!columnSign.containsKey(column)){
                    columnSign.put(column,maxColumn++);
                    gridPane.add(new Label(column), (Integer) columnSign.get(column),0);
                }
                //gridPane.add(new Label(column), (Integer) columnSign.get(column),rowNumber);
                //jakis for po tym skladajacy wartosci
                tempString = "";
                for(ArrayList<String> elementList: testFirstFollow.predictiveMap.predictiveMap.get(keyNumber).get(column)) {
                    if(!tempString.equals("")) {
                        tempString += " | ";
                    }
                    tempString += keyNumber + " -> ";
                    for(String element: elementList) {
                        tempString += element + " ";
                    }

                    gridPane.add(new Label(tempString), (Integer) columnSign.get(column),rowNumber);

                }

            }
            rowNumber++;
        }




        parserLLOutputPane.getChildren().add(gridPane);
    }

    public void printMovesTable(ActionEvent actionEvent) throws IOException {
        MovesTable testMoveTable = new MovesTable();
        if(movesTableInput.getText() != null && !movesTableInput.getText().trim().isEmpty()) {

            testMoveTable.generateMovesTable("S","a + a * a", testFirstFollow.predictiveMap.predictiveMap);

        } else {
            System.out.println("Input Empty");
            return;
        }

        parserLLOutputPane.getChildren().clear();
        ListView parserLLOutputList = new ListView();
        FXMLLoader xStart = new FXMLLoader(getClass().getResource("/fxml/test/parserLLWindow/parserLLMovesOutput.fxml"));
        parserLLOutputList.getItems().add(xStart.load());
        parserLLMovesOutputController xControlerStart = xStart.getController();
        xControlerStart.setOutput("Output");
        xControlerStart.setInput("Input");
        xControlerStart.setStack("Stack");

        for(MovesTableElement keyNumber: testMoveTable.movesList){
            FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/parserLLWindow/parserLLMovesOutput.fxml"));
            parserLLOutputList.getItems().add(x.load());
            parserLLMovesOutputController xControler = x.getController();

            //Input
            xControler.setInput(keyNumber.input);

            //Stack
            xControler.setStack(keyNumber.stack);

            //Output
            xControler.setOutput(keyNumber.output);

        }
        parserLLOutputPane.getChildren().add(parserLLOutputList);






    }
}
