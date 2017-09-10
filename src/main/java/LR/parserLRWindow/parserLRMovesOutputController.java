package LR.parserLRWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by DELL6430u on 2017-05-02.
 */
public class parserLRMovesOutputController implements Initializable {

    @FXML
    Label stack;
    @FXML
    Label input;
    @FXML
    Label moveNo;

    public void setStack(String temp){
        stack.setText(temp);
    }
    public void setInput(String temp){
        input.setText(temp);
    }
    public void setMoveNo(String temp){
        moveNo.setText(temp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
