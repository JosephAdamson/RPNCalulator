import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Calculator.fxml"));
        root.getStylesheets().add(String.valueOf(getClass().getResource("CalculatorStyle.css")));
        primaryStage.setTitle("Shut-O-Matic 3000");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
