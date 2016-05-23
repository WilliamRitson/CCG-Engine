package application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardEditorController {

	@FXML
	private MenuItem newCard;

	@FXML
	private MenuItem save;

	@FXML
	private TextField name;

	@FXML
	private TextField cost;

	@FXML
	private ChoiceBox<?> cardType;

	@FXML
	private TextField attack;

	@FXML
	private TextField life;

	@FXML
	private TextArea ruleText;

	@FXML
	private TreeView<String> cardList;
	
	private Card currentCard;

	private final Node rootIcon = new ImageView(new Image("file:@../resources/img/ace.png"));

	private final String rootDirectory = "cards";
	
	TreeItem<String> directoryTree;
	
	@FXML
	public void initialize() {
		directoryTree = buildDirectoryTree(rootDirectory);
		directoryTree.setExpanded(true);
		cardList.setRoot(directoryTree);
	}

	/**
	 * This function recursively builds a tree structure representing a
	 * directory and its subdirectories
	 * 
	 * @param currentDirectory
	 *            - The top level directory to search.
	 * @return - A tree representing all the files and folders in the directory
	 *         and its children.
	 */
	private TreeItem<String> buildDirectoryTree(String currentDirectory) {
		TreeItem<String> dir = new TreeItem<String>(currentDirectory, rootIcon);
		File path = new File(currentDirectory);
		File[] list = path.listFiles();
		for (int i = 0; i < list.length; i += 1) {
			if (list[i].isDirectory()) {
				dir.getChildren().add(buildDirectoryTree(list[i].getPath()));
			} else {
				dir.getChildren().add(new TreeItem<String>(list[i].getName()));
			}
		}
		return dir;
	}
	
	void loadCard(File cardData) {
		
	}

	@FXML
	void newCard(ActionEvent event) {
		currentCard = new Minion();
		currentCard.setName("new card");
		File saveFile = new File(rootDirectory + "/" + currentCard.getName() + ".xml");
		saveCard(currentCard, saveFile);
		directoryTree.getChildren().add(new TreeItem<String>(saveFile.getName()));
	}
	
	void saveCard(Card card, File path) {
		
	}

	@FXML
	void save(ActionEvent event) {

	}

}
