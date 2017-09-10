package start;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by DELL6430u on 2017-04-10.
 */
public class BaseWindowController implements Initializable {

    @FXML
    StackPane mainStackPane;
    @FXML
    StackPane menuStackPane;

//    private static BaseWindowController ourInstance = new BaseWindowController();
//
//    public static BaseWindowController getInstance() {
//        return ourInstance;
//    }
//
//    private BaseWindowController() {
//    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setMainView(String fxml){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/test/baseWindow.fxml"), getResourceBundle());
        mainStackPane.getChildren().clear();
        try {
            mainStackPane.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResourceBundle getResourceBundle(){
        return ResourceBundle.getBundle("/bundle/bundle.properties");
    }

//    protected void setStage(Stage primaryStage){
//
//    }

}
