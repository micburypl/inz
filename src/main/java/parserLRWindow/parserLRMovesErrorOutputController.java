package parserLRWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created on 08.05.2017.
 */
public class parserLRMovesErrorOutputController implements Initializable {

    @FXML
    Label error;

    public void setError(String temp){
        error.setText(temp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}