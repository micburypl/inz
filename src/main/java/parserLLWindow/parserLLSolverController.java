package parserLLWindow;

import firstFollow.FirstFollow;
import firstFollowWindow.FirstFollowOutputFirstController;
import firstFollowWindow.FirstFollowOutputFollowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by DELL6430u on 2017-05-01.
 */
public class parserLLSolverController implements Initializable {

    @FXML
    ListView parserLLInputList;
    @FXML
    ListView parserLLOutputList;

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
        parserLLOutputList.getItems().clear();
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
    }

    public void printFollow(ActionEvent actionEvent) throws IOException {
        parserLLOutputList.getItems().clear();
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
    }

    public void printParsingTable(ActionEvent actionEvent) {
    }

    public void printMovesTable(ActionEvent actionEvent) {
    }
}
