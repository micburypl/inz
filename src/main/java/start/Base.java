package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public final class Base extends Application {
    private static Base instance;

    private Stage primatyStage;


    public static Base getInstance() {
        return (instance);
    }

    private Base(){
    }

    @Override
    public void start(final Stage primaryStage) {
        instance = this;
        try {

            // Main scene.
            {

                Scene mainScene = new Scene((Parent)new FXMLLoader(getClass().getResource("/fxml/test/mainWindow/baseWindow.fxml"), ResourceBundle.getBundle("bundle.bundle")).load(), 1300, 700);
                mainScene.getStylesheets().add(getClass().getResource("/fromPaula/application.css").toExternalForm());


                //Parent page = (Parent) FXMLLoader.load(BaseWindowController.class.getResource("main.fxml"));
                //Scene mainScene = new Scene(page);
                primaryStage.setScene(mainScene);
                primaryStage.show();
            }


        } catch (Exception ex) {
            //Constants.LOGGER.log(Level.SEVERE, ex.toString());
        }
    }

    public void initialize() {
        instance = this;
    }

}
