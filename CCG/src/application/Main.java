package application;
	
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;


/**
 * The Class Main.
 * 
 * This is the entry point for the program
 */
public class Main extends Application {
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * 
	 * Creates a new app and loads the a view as specified by the command line argumetns
	 */
	@Override
	public void start(Stage primaryStage) {
		Map<String, String> params = getParameters().getNamed();
		String startView = params.containsKey("start") ? params.get("start") : "match";
				
		switch(startView) {
		case "match":
			openView(primaryStage, "Match.fxml", "match.css");
			break;
		case "cardeditor":
			openView(primaryStage, "CardEditor.fxml", "CardEditor.css");
			break;
		case "seteditor":
			openView(primaryStage, "SetEditor.fxml", "SetEditor.css");
			break;
		}
	}
	
	public void openView(Stage primaryStage, String fxmlFileName, String cssFileName) {
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource(fxmlFileName));
			Scene scene = new Scene(root, 1280, 800);
			scene.getStylesheets().add(getClass().getResource(cssFileName).toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The main method. This just launches the JavaFX application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
