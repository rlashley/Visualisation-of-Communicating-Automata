import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
     
public class Main extends Application {
    	
        public static void main(String[] args) {
        	launch(args);
        }
        
    	//Creates FileParser object with methods that alter the incoming Array of Strings into the format we need
    	FileParser fileParser = new FileParser();
    	
        @Override
        public void start(Stage primaryStage) throws Exception {
            primaryStage.setTitle("File Chooser");
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
            Button openButton = new Button("Click to open a file...");
            openButton.setMinSize(100, 40);
            openButton.setMaxSize(300, 120);
            openButton.setPrefSize(200, 80);
            HBox hbox = new HBox(openButton);        
            Scene scene = new Scene(hbox, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(primaryStage);
                        if (file != null) {
                        	//Execute the method to convert to string array before sending to file parser                        	
                        	fileParser.convertFile(file);
                        }
                    }
                });
        }
}
    