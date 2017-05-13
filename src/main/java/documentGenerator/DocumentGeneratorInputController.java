package documentGenerator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created on 12.05.2017.
 */
public class DocumentGeneratorInputController  implements Initializable {

    @FXML
    TextField productionLeftPart;
    @FXML
    TextField productionRightPart;
    @FXML
    Button removeButton;

    Integer number;

    public String getLeftPart(){
        return productionLeftPart.getText();
    }

    public String getRightPart(){
        return productionRightPart.getText();
    }

    public Number getNumber(){
        return number;
    }

    public void setLeftPart(String input){
        productionLeftPart.setText(input);
    }

    public void setRightPart(String input){
        productionRightPart.setText(input);
    }

    public void setNumber(Integer input) {
        number = input;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
