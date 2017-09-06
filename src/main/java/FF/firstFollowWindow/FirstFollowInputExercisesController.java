package FF.firstFollowWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created on 08.05.2017.
 */
public class FirstFollowInputExercisesController implements Initializable {
    @FXML
    TextField productionLeftPart;
    @FXML
    TextField productionRightPart;

    public String getLeftPart(){
        if(productionLeftPart != null) {
            return productionLeftPart.getText();
        }
        return "";

    }

    public String getRightPart(){
        if (productionRightPart != null) {
            return productionRightPart.getText();
        }
        return "";
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