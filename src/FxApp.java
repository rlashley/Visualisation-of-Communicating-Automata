import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class FxApp extends Application {
	
	//Creates FileParser object with methods that alter the incoming Array of Strings into the format we need
	FileParser fileParser = new FileParser();

	
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("File Chooser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
        Button openButton = new Button("Click to open a file...");
        openButton.setPrefSize(200, 80);
        Label lbl = new Label();
        VBox vbox = new VBox(lbl, openButton);  
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(15));
        lbl.setText("This tool creates virtual automata based \ron the file.");
        Scene scene = new Scene(vbox, 250, 300);
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
