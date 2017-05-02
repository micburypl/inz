package firstLastFollowWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by DELL6430u on 2017-04-10.
 */
public class FirstLastFollowInputController implements Initializable {
    @FXML
    TextField productionLeftPart;
    @FXML
    TextField productionRightPart;

    public String getLeftPart(){
        return productionLeftPart.getText();
    }

    public String getRightPart(){
        return productionLeftPart.getText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
