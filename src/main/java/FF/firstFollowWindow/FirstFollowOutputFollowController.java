package FF.firstFollowWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by DELL6430u on 2017-04-10.
 */
public class FirstFollowOutputFollowController implements Initializable {
    @FXML
    private
    Label followSymbolPart;
    @FXML
    Label followSolutionPart;
    public void setSymbolPart(String temp){
        followSymbolPart.setText(temp);
    }

    public void setSolutionPart(String temp){
        followSolutionPart.setText(temp);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
