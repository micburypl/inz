import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by DELL6430u on 2017-03-27.
 */
public class testController implements Initializable {

    @FXML
    StackPane stackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        stackPane.getChildren().add(new Button("XXX"));

    }
}
