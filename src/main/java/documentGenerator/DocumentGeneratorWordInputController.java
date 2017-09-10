package documentGenerator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created on 12.05.2017.
 */
public class DocumentGeneratorWordInputController  implements Initializable {

    @FXML
    TextField wordPart;
    Integer number;



    public String getWordPart() {
        return wordPart.getText();
    }

    public Number getNumber() {
        return number;
    }

    public void setWordPart(String input) {
        wordPart.setText(input);
    }

    public void setNumber(Integer input) {
        number = input;
    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
