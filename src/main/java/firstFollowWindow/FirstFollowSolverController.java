package firstFollowWindow;

import com.sun.javafx.geom.Rectangle;
import commonUtility.CommonUtility;
import firstFollow.FirstFollow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by DELL6430u on 2017-05-01.
 */
public class FirstFollowSolverController implements Initializable {

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


    FileChooser fileChooser = new FileChooser();
    private Desktop desktop = Desktop.getDesktop();

    List<FirstFollowInputController> listInput = new ArrayList<>();

    FirstFollow testFirstFollow;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

            FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowInput.fxml"));

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
                FXMLLoader x1 = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowInput.fxml"));
                try {
                    firstFollowInputList.getItems().add(firstFollowInputList.getItems().size() - 1, x1.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listInput.add(x1.getController());
            });
        firstFollowInputList.getItems().add(b);

        firstFollowButtonVBox.setVisible(false);
        firstFollowPartialSolutionsLabel.setVisible(false);
    }

    public void generateFirstAndFollow(ActionEvent actionEvent) {
        List<String> inputLineList = new ArrayList<>();
        String tempString;

        //

        for(Integer i = 0; i < listInput.size(); i++) {
            //System.out.println(listInput.get(i).getLeftPart());
            System.out.println(listInput.get(i).productionLeftPart);
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
            firstFollowButtonVBox.setVisible(false);
            firstFollowPartialSolutionsLabel.setVisible(false);

            firstFollowOutputPane.getChildren().clear();
            GridPane gridPane = new GridPane();
            gridPane.setGridLinesVisible(true);
            Label tempLabel = new Label(CommonUtility.getKey("firstFollow.Errors"));
            //Label tempLabel = new Label("Errors");
            gridPane.add(tempLabel, 0,0);
            gridPane.setHalignment(tempLabel, HPos.CENTER);
            Integer tempInt = 1;
            for(Integer errorLine: testFirstFollow.errorMessages.keySet() ) {
                tempLabel = new Label(CommonUtility.getKey("firstFollow.InRow") + " " + errorLine + ". " + testFirstFollow.errorMessages.get(errorLine));
                gridPane.add(tempLabel, 0, tempInt++);
                gridPane.setHalignment(tempLabel, HPos.CENTER);
            }

            firstFollowOutputPane.getChildren().add(gridPane);
            return;
        }

        //show buttons
        firstFollowButtonVBox.setVisible(true);
        firstFollowPartialSolutionsLabel.setVisible(true);
        firstFollowOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();
        firstFollowOutputPane.getChildren().add(gridPane);



    }

    public void printFirst(ActionEvent actionEvent) throws IOException {
        firstFollowOutputPane.getChildren().clear();
        GridPane gridPane = new GridPane();

        Label tempLabel = new Label(CommonUtility.getKey("firstFollow.Element"));
        //Label tempLabel = new Label("Element");
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label(CommonUtility.getKey("firstFollow.FirstElement"));
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

        Label tempLabel = new Label(CommonUtility.getKey("firstFollow.Element"));
        gridPane.add(tempLabel, 0,0);
        gridPane.setHalignment(tempLabel, HPos.CENTER);

        tempLabel = new Label(CommonUtility.getKey("firstFollow.FollowElement"));
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

    public void openFile(ActionEvent actionEvent) throws IOException {

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            openFile(file);
        }
    }

    void openFile(File file) throws IOException {

        firstFollowInputList.getItems().clear();
        listInput = new ArrayList<>();
        String[] inputWord;
        for (String line : Files.readAllLines(Paths.get(file.getAbsolutePath()))) {

            FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowInput.fxml"));

            if(line.contains("->")) {
                //produciton
                inputWord = line.split("->");
                firstFollowInputList.getItems().add(x.load());
                FirstFollowInputController elementContorller = x.getController();

                elementContorller.setLeftPart(inputWord[0]);
                elementContorller.setRightPart(inputWord[1]);
                listInput.add(x.getController());
            }

        }

        Button b = new Button("+");
        b.setOnAction(handle -> {
            System.out.println(firstFollowInputList.getSelectionModel());
            FXMLLoader x1 = new FXMLLoader(getClass().getResource("/fxml/test/firstFollowWindow/firstFollowInput.fxml"));
            try {
                firstFollowInputList.getItems().add(firstFollowInputList.getItems().size() - 1, x1.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            listInput.add(x1.getController());
        });
        firstFollowInputList.getItems().add(b);


        firstFollowButtonVBox.setVisible(false);
        firstFollowPartialSolutionsLabel.setVisible(false);
        firstFollowOutputPane.getChildren().clear();

    }


}
