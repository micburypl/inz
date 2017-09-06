package FF.firstFollowWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by DELL6430u on 2017-04-10.
 */
public class FirstFollowOutputFirstController implements Initializable {

    public Label separator;
    @FXML
    Label firstSymbolPart;
    @FXML
    Label firstSolutionPart;

    public void setSymbolPart(String temp){
        firstSymbolPart.setText(temp);

    }

    public void setSolutionPart(String temp){
        firstSolutionPart.setText(temp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
