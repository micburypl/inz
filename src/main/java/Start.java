import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by DELL6430u on 2017-03-13.
 */
public class Start extends Application {

    public static void main(String[] argc){
        launch(argc);
    };

    public void start(Stage stage) throws Exception {
    System.out.print("Hello world");

        stage.setTitle("My JavaFX Application");
        //stage.setScene(scene);
        stage.show();
    }
}
