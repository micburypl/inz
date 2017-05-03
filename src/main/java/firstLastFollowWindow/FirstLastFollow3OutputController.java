package firstLastFollowWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created on 03.05.2017.
 */
public class FirstLastFollow3OutputController implements Initializable {


    @FXML
    Label elementNo;
    @FXML
    Label first;
    @FXML
    Label second;

    public void setElementNo(String temp){
        elementNo.setText(temp);
    }
    public void setFirst(String temp){
        first.setText(temp);
    }
    public void setSecond(String temp){
        second.setText(temp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

