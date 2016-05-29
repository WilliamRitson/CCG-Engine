package application;
	
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * The Class Main.
 * 
 * This is the entry point for the program
 */
public class CollectableCardEngine extends Application {
	
	private Stage primaryStage;
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * 
	 * Creates a new app and loads the a view as specified by the command line arguments
	 */
	@Override
	public void start(Stage stage) {
		Map<String, String> params = getParameters().getNamed();
		String startView = params.containsKey("start") ? params.get("start") : "";
		primaryStage = stage;
				
		switch(startView) {
		case "match":
			openView(View.Match);
			break;
		case "cardeditor":
			openView(View.CardEditor);
			break;
		default:
			openView(View.MainMenu);

		}
	}
	
	public void openView(View toOpen) {
		switch(toOpen) {
			case MainMenu:
				showView("MainMenu.fxml", "MainMenu.css");
				break;
			case CardEditor:
				showView("CardEditor.fxml", "CardEditor.css");
				break;
			case Match:
				showView("Match.fxml", "match.css");
				break;
		}
		
	}
	
	private void showView(String fxmlFileName, String cssFileName) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(fxmlFileName));
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());			
			Pane root = (Pane) FXMLLoader.load(getClass().getResource(fxmlFileName));
			Scene scene = new Scene(root, 1280, 800);
			scene.getStylesheets().add(getClass().getResource(cssFileName).toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
			ViewController controller = fxmlLoader.<ViewController> getController();
			controller.setParent(this);
			
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
