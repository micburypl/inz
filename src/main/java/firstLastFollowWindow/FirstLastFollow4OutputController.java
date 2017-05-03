package firstLastFollowWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created on 03.05.2017.
 */
public class FirstLastFollow4OutputController implements Initializable {


    @FXML
    Label elementNo;
    @FXML
    Label first;
    @FXML
    Label second;
    @FXML
    Label third;

    public void setElementNo(String temp){
        elementNo.setText(temp);
    }
    public void setFirst(String temp){
        first.setText(temp);
    }
    public void setSecond(String temp){
        second.setText(temp);
    }
    public void setThird(String temp){
        third.setText(temp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

