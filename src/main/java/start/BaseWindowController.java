package start;

import fromPaula.SideMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by DELL6430u on 2017-04-10.
 */
public class BaseWindowController implements Initializable {

    @FXML
    public StackPane solverSection;
    @FXML
    StackPane mainSection;
    @FXML
    VBox menuSection;

    String language;
    String fxml;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Scene testScene = new Scene((Parent)new FXMLLoader(getClass().getResource("/fxml/test/firstLastFollowWindow/firstLastFollowSolver.fxml"), ResourceBundle.getBundle("bundle.bundle")).load(), 800, 600);
        //Base.displayMainWindowPane("/fxml/test/firstLastFollowWindow/firstLastFollowSolver.fxml");

        fxml = "/fxml/test/firstLastFollowWindow/firstLastFollowSolver.fxml";
        FXMLLoader x1 = new FXMLLoader(getClass().getResource(fxml), ResourceBundle.getBundle("bundle.bundle"));
        try {
            solverSection.getChildren().add(x1.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //SideMenu sm = new SideMenu();
        menuSection.getChildren().add(new SideMenu());

        //mainSection.getChildren().add(vb);

    }


    //public ResourceBundle getResourceBundle(){
     //   return ResourceBundle.getBundle("/bundle/bundle.properties");
   // }

    public void switchToEnglish(ActionEvent actionEvent) throws IOException {
        Locale.setDefault(new Locale("en", "EN"));
        Start.getInstance().displayMainWindowPane();

    }

    public void switchToPolish(ActionEvent actionEvent) throws IOException {
        Locale.setDefault(new Locale("pl", "PL"));
        Start.getInstance().displayMainWindowPane();
    }


    public ResourceBundle getResourceBundle() {
        Locale local = Locale.getDefault();
        return ResourceBundle.getBundle("bundle.bundle", local);
    }

}
