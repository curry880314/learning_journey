package stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/resources/main.fxml"));
        primaryStage.setTitle("Learning Journey");
        primaryStage.getIcons().add(new Image("resources/image/icon.jpg"));
        primaryStage.setScene(new Scene(root, 1050, 600));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

}
//java -cp learning_journey/jars/javafx-sdk-11.0.2/lib/*.Launcher.java
//javac -cp learning_journey/jars/javafx-sdk-11.0.2/lib/*.jar Launcher.java
//javac -cp learning_journey/jars/javafx-sdk-11.0.2/lib/*.jar Main.java
//javac -cp learning_journey/jars/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml Main.java