package FF.firstFollowWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by DELL6430u on 2017-05-01.
 */
public class FirstFollowInputController implements Initializable {

    @FXML
    TextField productionLeftPart;
    @FXML
    TextField productionRightPart;

    public String getLeftPart(){
        return productionLeftPart.getText();
    }

    public String getRightPart(){
        return productionRightPart.getText();
    }

    public void setLeftPart(String input){
        productionLeftPart.setText(input);
    }

    public void setRightPart(String input){
        productionRightPart.setText(input);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}
