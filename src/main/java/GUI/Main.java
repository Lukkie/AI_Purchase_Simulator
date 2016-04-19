package GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

/**
 * Created by Lukas on 19-Apr-16.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader loader = new FXMLLoader();
        String pathToFxml = "src/main/resources/GUI.fxml";
        URL fxmlUrl = new File(pathToFxml).toURI().toURL();
        loader.setLocation(fxmlUrl);
        Parent root = loader.load();
        primaryStage.setTitle("Purchase simulator by Gilles and Lukas");
        Scene rootScene = new Scene(root);
        primaryStage.setScene(rootScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

    }

}
