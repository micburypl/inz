package firstFollowWindow;

import firstFollow.FirstFollow;
import firstFollowTestData.FirstFollowTestMainSet;
import firstFollowTestData.FirstFollowTestSet;
import firstFollowTestData.FirstFollowTestSetElement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by DELL6430u on 2017-05-01.
 */
public class FirstFollowExercisesController implements Initializable {

    @FXML
    ListView firstFollowInputList;

    @FXML
    ListView  firstFollowOutputList;

    @FXML
    StackPane firstFollowOutputPane;

    @FXML
    VBox firstFollowButtonVBox;

    @FXML
    Label firstFollowPartialSolutionsLabel;

    @FXML
    Button firstFollowExercisesVerifyButton;

    List<FirstFollowInputExercisesController> listInput = new ArrayList<>();

    FirstFollow testFirstFollow;

    FirstFollowTestSet tempSet;
    FirstFollowTestMainSet tempElement;
    Integer lastValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lastValue = -1;
        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowExercisesInput.fxml"));

        try {
            //x.load();
            firstFollowInputList.getItems().add(x.load());

        } catch (IOException e) {
            e.printStackTrace();
        }

        listInput.add(x.getController());
            Button b = new Button("+");
            b.setOnAction(handle -> {
                System.out.println(firstFollowInputList.getSelectionModel());
                FXMLLoader x1 = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowExercisesInput.fxml"));
                try {
                    firstFollowInputList.getItems().add(firstFollowInputList.getItems().size() - 1, x1.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listInput.add(x1.getController());
            });
        //firstFollowInputList.getItems().add(b);

        showElement(false);
//        firstFollowButtonVBox.setVisible(false);
//        firstFollowPartialSolutionsLabel.setVisible(false);
//        firstFollowExercisesVerifyButton.setVisible(false);
    }

    public void randomInput(ActionEvent actionEvent) throws IOException {
        tempSet = new FirstFollowTestSet(true);
        lastValue = tempSet.testData(lastValue);
        tempElement = tempSet.InputListTestList.get(lastValue);
        firstFollowInputList.getItems().clear();
        listInput = new ArrayList<>();
        for(FirstFollowTestSetElement temp : tempElement.InputListTest){
            FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowExercisesInput.fxml"));


            firstFollowInputList.getItems().add(x.load());
            FirstFollowInputExercisesController elementContorller = x.getController();

            elementContorller.setLeftPart(temp.leftPart);
            elementContorller.setRightPart(temp.rightPart);
            listInput.add(x.getController());
        }

        showElement(false);
    }

    public void generateFirstAndFollow(ActionEvent actionEvent) {
        List<String> inputLineList = new ArrayList<>();
        String tempString;

        //

        for(Integer i = 0; i < listInput.size(); i++) {
            tempString =  listInput.get(i).getLeftPart() + " -> " + listInput.get(i).getRightPart();
            System.out.println(tempString);
            inputLineList.add(tempString);
        }

        //

//        inputLineList.add("S -> A S'");
//        inputLineList.add("S' -> + A S' | eps");
//        inputLineList.add("A -> B A' ");
//        inputLineList.add("A' -> * B A' | eps");
//        inputLineList.add("B -> ( S ) | a");


        testFirstFollow = new FirstFollow(inputLineList);
        testFirstFollow.generateSolutionSet();


        if(testFirstFollow.errorFlag) {

            //blocked buttons
            showElement(false);
//            firstFollowButtonVBox.setVisible(false);
//            firstFollowPartialSolutionsLabel.setVisible(false);
//            firstFollowExercisesVerifyButton.setVisible(false);


            firstFollowOutputPane.getChildren().clear();
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

            firstFollowOutputPane.getChildren().add(gridPane);
            return;
        }

        //show buttons
        showElement(true);
//        firstFollowButtonVBox.setVisible(true);
//        firstFollowPartialSolutionsLabel.setVisible(true);
//        firstFollowExercisesVerifyButton.setVisible(true);


        firstFollowOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();
        firstFollowOutputPane.getChildren().add(gridPane);



    }

    public void printFirst(ActionEvent actionEvent) throws IOException {
        firstFollowOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();

        Label tempLabel = new Label("Element");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label("First(Element)");
        gridPane.add(tempLabel, 1,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);


        Integer rowNumber = 1;
        gridPane.setGridLinesVisible(true);
        for(String keyFollow: testFirstFollow.firstElementMap.keySet() ){


            tempLabel = new Label(keyFollow);
            gridPane.add(tempLabel, 0,rowNumber);
            gridPane.setHalignment(tempLabel, HPos.CENTER);

            String solutionString = "";
            for(String temp: testFirstFollow.firstElementMap.get(keyFollow).firstSet) {
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

        firstFollowOutputPane.getChildren().add(gridPane);
    }

    public void printFollow(ActionEvent actionEvent) throws IOException {

        firstFollowOutputPane.getChildren().clear();
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

        firstFollowOutputPane.getChildren().add(gridPane);
    }

    void showElement(Boolean show) {
        firstFollowButtonVBox.setVisible(show);
        firstFollowPartialSolutionsLabel.setVisible(show);
        firstFollowExercisesVerifyButton.setVisible(show);
    }


}
