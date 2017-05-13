package documentGenerator;

import firstFollow.FirstFollow;
import firstFollowWindow.FirstFollowInputController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.zip.CheckedOutputStream;

/**
 * Created on 12.05.2017.
 */
public class DocumentGeneratorController  implements Initializable {

    @FXML
    ListView documentGeneratorInputList;

    @FXML
    ListView documentGeneratorWordsInputList;

    @FXML
    ListView documentGeneratorOutputPane;

    @FXML
    Label documentGeneratorOutputPaneLabel;


    GridPane gridPane;

    HashMap<Integer, DocumentGeneratorInputController> listInput = new HashMap<>();
    HashMap<Integer, DocumentGeneratorWordInputController> listWordInput = new HashMap<>();

    Integer productionLastNumber;
    Integer wordLastNumber;

    FileChooser fileChooser = new FileChooser();

    File file;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        documentGeneratorOutputPane.setVisible(false);
        documentGeneratorOutputPaneLabel.setVisible(false);
        productionLastNumber = 1;
        wordLastNumber = 1;
        documentGeneratorWordsInputList.getItems().clear();
        documentGeneratorInputList.getItems().clear();
        //production

        FXMLLoader x = new FXMLLoader(getClass().getResource("/fxml/test/DocumentGenerator/DocumentGeneratorInput.fxml"));

        try {
            documentGeneratorInputList.getItems().add(x.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // x.getController()removeButton

        listInput.put(productionLastNumber++, x.getController());
        Button b = new Button("+");
        b.setOnAction(handle -> {
            FXMLLoader x1 = new FXMLLoader(getClass().getResource("/fxml/test/DocumentGenerator/DocumentGeneratorInput.fxml"));
            try {
                documentGeneratorInputList.getItems().add(documentGeneratorInputList.getItems().size() - 1, x1.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            listInput.put(productionLastNumber++, x1.getController());
        });
        documentGeneratorInputList.getItems().add(b);


        // Word
        x = new FXMLLoader(getClass().getResource("/fxml/test/DocumentGenerator/DocumentGeneratorWordInput.fxml"));

        try {
            documentGeneratorWordsInputList.getItems().add(x.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        listWordInput.put(wordLastNumber++, x.getController());
        Button bWord = new Button("+");
        bWord.setOnAction(handle -> {
            FXMLLoader x2 = new FXMLLoader(getClass().getResource("/fxml/test/DocumentGenerator/DocumentGeneratorWordInput.fxml"));
            try {
                documentGeneratorWordsInputList.getItems().add(documentGeneratorWordsInputList.getItems().size() - 1, x2.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            listWordInput.put(wordLastNumber++, x2.getController());
        });
        documentGeneratorWordsInputList.getItems().add(bWord);



        //showElement(false);


    }





    public void openFile(ActionEvent actionEvent) throws IOException {

        file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            openFile(file);
        }
    }

    void openFile(File file) throws IOException {

        documentGeneratorInputList.getItems().clear();
        documentGeneratorWordsInputList.getItems().clear();

        productionLastNumber = 1;
        wordLastNumber = 1;
        FXMLLoader x;
        listInput = new HashMap<>();
        listWordInput = new HashMap<>();
        String[] inputWord;

        for (String line : Files.readAllLines(Paths.get(file.getAbsolutePath()))) {
            if (line.contains("->")) {
                x = new FXMLLoader(getClass().getResource("/fxml/test/DocumentGenerator/DocumentGeneratorInput.fxml"));
                inputWord = line.split("->");
                documentGeneratorInputList.getItems().add(x.load());
                DocumentGeneratorInputController elementContorller = x.getController();

                elementContorller.setLeftPart(inputWord[0]);
                elementContorller.setRightPart(inputWord[1]);
                elementContorller.setNumber(productionLastNumber++);
                listInput.put(productionLastNumber, x.getController());
            } else {
                x = new FXMLLoader(getClass().getResource("/fxml/test/DocumentGenerator/DocumentGeneratorWordInput.fxml"));
                documentGeneratorWordsInputList.getItems().add(x.load());
                DocumentGeneratorWordInputController elementContorller = x.getController();

                elementContorller.setWordPart(line);
                elementContorller.setNumber(wordLastNumber++);
                listWordInput.put(wordLastNumber, x.getController());
            }
        }

        Button b = new Button("+");
        b.setOnAction(handle -> {
            FXMLLoader x1 = new FXMLLoader(getClass().getResource("/fxml/test/DocumentGenerator/DocumentGeneratorInput.fxml"));
            try {
                documentGeneratorInputList.getItems().add(documentGeneratorInputList.getItems().size() - 1, x1.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            listInput.put(productionLastNumber++, x1.getController());
        });
        documentGeneratorInputList.getItems().add(b);


        Button bWord = new Button("+");
        bWord.setOnAction(handle -> {
            FXMLLoader x1 = new FXMLLoader(getClass().getResource("/fxml/test/DocumentGenerator/DocumentGeneratorWordInput.fxml"));
            try {
                documentGeneratorWordsInputList.getItems().add(documentGeneratorWordsInputList.getItems().size() - 1, x1.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            listWordInput.put(wordLastNumber++, x1.getController());
        });
        documentGeneratorWordsInputList.getItems().add(bWord);

        documentGeneratorOutputPane.setVisible(false);
        documentGeneratorOutputPaneLabel.setVisible(false);

    }

    public void verification(ActionEvent actionEvent) {

        ArrayList<String> dataToFile = new ArrayList<>();

        String tempString;
        for(Integer i: listInput.keySet()) {
            if(listInput.get(i).getLeftPart() == null || listInput.get(i).getLeftPart().length() == 0) {
                listInput.get(i).setLeftPart("");
            }
            if(listInput.get(i).getRightPart() == null || listInput.get(i).getRightPart().length() == 0) {
                listInput.get(i).setRightPart("");
            }

            tempString =  listInput.get(i).getLeftPart() + " -> " + listInput.get(i).getRightPart();
            System.out.println(tempString);
            dataToFile.add(tempString);
        }

        FirstFollow ff = new FirstFollow(dataToFile);
        ff.generateSolutionSet();

        Label tempLabel;
        Integer tempInt = 0;
        gridPane = new GridPane();

        if(ff.errorFlag) {
            for(Integer errorLine: ff.errorMessages.keySet() ) {
                tempLabel = new Label("In row " + errorLine + ". " + ff.errorMessages.get(errorLine));
                gridPane.add(tempLabel, 0, tempInt++);
                gridPane.setHalignment(tempLabel, HPos.CENTER);
            }
        } else {
            tempLabel = new Label("Correct Data");
            gridPane.add(tempLabel, 0, tempInt++);
            gridPane.setHalignment(tempLabel, HPos.CENTER);
        }

        documentGeneratorOutputPane.getItems().clear();
        documentGeneratorOutputPane.getItems().add(gridPane);
        documentGeneratorOutputPane.setVisible(true);
        documentGeneratorOutputPaneLabel.setVisible(true);

    }

    public void savingFile(ActionEvent actionEvent) {

        ArrayList<String> dataToFile = new ArrayList<>();

        String tempString;
        for(Integer i: listInput.keySet()) {
            tempString =  listInput.get(i).getLeftPart() + " -> " + listInput.get(i).getRightPart();
            System.out.println(tempString);
            if(tempString.replaceAll(" ", "").equals("->")){
                continue;
            }
            dataToFile.add(tempString);
        }

        for(Integer i: listWordInput.keySet()) {
//            if(!String.valueOf(listWordInput.get(i).getWordPart()).isEmpty()) {
//                continue;
//            }
            dataToFile.add(String.valueOf(listWordInput.get(i).getWordPart()));
        }


        File file = fileChooser.showSaveDialog(new Stage());
        System.out.println(file);
        CheckedOutputStream text = null;

        BufferedWriter writer;

        if(file != null && !dataToFile.isEmpty()){
            try {
                writer = new BufferedWriter(new FileWriter(file + ".txt"));


                for (String toAddToList: dataToFile) {
                    writer.write(toAddToList);
                    writer.newLine();
                }


                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(File file) {

    }
}
