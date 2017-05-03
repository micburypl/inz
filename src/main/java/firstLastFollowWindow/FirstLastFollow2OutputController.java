package firstLastFollowWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created on 03.05.2017.
 */
public class FirstLastFollow2OutputController implements Initializable {


    @FXML
    Label elementNo;
    @FXML
    Label first;


    public void setElementNo(String temp){
        elementNo.setText(temp);
    }
    public void setFirst(String temp){
        first.setText(temp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

