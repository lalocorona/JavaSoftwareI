package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;
//The JavaDocs folder can be found in the src folder with the Main.java

/**
 * This class launches the FXML scenes and directs to the main form.
 *  <p> FUTURE ENHANCEMENT - The inventory system could also implement file
 *  importing and exporting. Having some form of integration from
 *  Excel sheets. </p>
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        primaryStage.setTitle("Main Form");
        primaryStage.setScene(new Scene(root, 1000, 400 ));
        primaryStage.show();
    }

    /**
     * This method launches the main screen.
     * @param args
     */
    public static void main(String[] args){
        launch(args);
    }
}

