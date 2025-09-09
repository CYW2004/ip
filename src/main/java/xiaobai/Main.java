package xiaobai;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    private final XiaoBai xiaoBai = new XiaoBai("data/xiaobai.txt");

    @Override
    public void start(Stage stage) {
        assert stage != null : "Stage must not be null";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            assert fxmlLoader != null : "FXMLLoader must not be null";
            AnchorPane ap = fxmlLoader.load();
            assert ap != null : "AnchorPane must not be null after loading FXML";
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("XiaoBai");
            stage.setResizable(false);
            MainWindow controller = fxmlLoader.getController();
            assert controller != null : "MainWindow controller must not be null";
            controller.setXiaoBai(xiaoBai); // inject
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            assert false : "IOException occurred while starting application: " + e.getMessage();
        }
    }
}
