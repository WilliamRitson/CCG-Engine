package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Hashtable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The Class CardEditorController.
 */
public class CardEditorController extends ViewController {

	/** The Menu option that creates a new card. */
	@FXML
	private MenuItem newCard;

	/** The save. */
	@FXML
	private MenuItem save;

	/** The card tabs. */
	@FXML
	private TabPane cardTabs;

	/** The card list. */
	@FXML
	private TreeView<File> cardList;

	/** The set of all existing cards. */
	private HashSet<Card> allCards = new HashSet<Card>();
	
	/** The controller for each tab. */
	private Hashtable<Tab, CardEditorTabController> tabControllers = new Hashtable<Tab, CardEditorTabController>();
		
	//private final Node rootIcon = new ImageView(new Image("file:@../resources/img/folder.png"));

	/** The root directory to load content from. */
	private final File rootDirectory = new File("cards");


	/** The root tree item in the file tree. */
	private TreeItem<File> rootTreeItem;
	
	/**
	 * Selects an item from the tree view. If its a card opens a tab for it.
	 *
	 * @param mouseEvent the mouse event
	 */
	public void selectItem(MouseEvent mouseEvent) {
		TreeItem<File> item = cardList.getSelectionModel().getSelectedItem();

		File selected = item.getValue();
		if (!selected.isDirectory()) {
			openTab(selected);
		}
	}
	
	
	/**
	 * Gets the tree add path.
	 *
	 * @return the tree add path
	 */
	private TreeItem<File> getTreeAddPath() {
		TreeItem<File> selectedTreeItem = cardList.getSelectionModel().getSelectedItem();
		File selected = selectedTreeItem.getValue();
		if (selected.isDirectory()) {
			return selectedTreeItem;
		} else {
			return selectedTreeItem.getParent();
		}
	}
	
	/**
	 * Make subdirectory.
	 *
	 * @param directory the directory
	 */
	private void makeSubdirectory(File directory) {
		TreeItem<File> addPos = getTreeAddPath();
		String folderName = (new TextInputDialog()).showAndWait().get();
		String childPath = directory.getPath() + File.separator + folderName;
		File child = new File(childPath);
		child.mkdirs();
		addPos.getChildren().add(new TreeItem<File>(child));
	}
	
	/**
	 * Adds the card list menu.
	 */
	public void addCardListMenu() {
		ContextMenu treeMenu = new ContextMenu();
        MenuItem addCard = new MenuItem("Add Card");
        MenuItem addDirectory = new MenuItem("Add Directory");
        MenuItem rename = new MenuItem("Rename");
        MenuItem delete = new MenuItem("Delete");
        
        treeMenu.getItems().addAll(addCard, addDirectory, rename, delete);
        addCard.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addCardToFolder(getTreeAddPath());
			}
        });
        addDirectory.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				makeSubdirectory(getTreeAddPath().getValue());
			}
        });
        rename.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			}
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			}
        });
         
        cardList.setContextMenu(treeMenu);
	}

	/**
	 * Initialize the controller.
	 */
	@FXML
	public void initialize() {
		cardTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
		rootTreeItem = buildDirectoryTree(rootDirectory);
		rootTreeItem.setExpanded(true);
		cardList.setRoot(rootTreeItem);
		addCardListMenu();
	}

	/**
	 * This function recursively builds a tree structure representing a
	 * directory and its sub-directories.
	 *
	 * @param currentDirectory            - The top level directory to search.
	 * @return - A tree representing all the files and folders in the directory
	 *         and its children.
	 */
	private TreeItem<File> buildDirectoryTree(File currentDirectory) {
		TreeItem<File> dir = new TreeItem<File>(currentDirectory);
		File[] list = currentDirectory.listFiles();
		for (int i = 0; i < list.length; i += 1) {
			if (list[i].isDirectory()) {
				dir.getChildren().add(buildDirectoryTree(list[i]));
			} else {
				dir.getChildren().add(new TreeItem<File>(list[i]));
				allCards.add(Card.loadFromXML(list[i]));
			}
		}
		return dir;
	}

	/**
	 * Open tab.
	 *
	 * @param cardFile the card file
	 */
	void openTab(File cardFile) {
		try {
			Tab newTab = new Tab();
			URL url = getClass().getResource("CardEditorTab.fxml");
			FXMLLoader tabLoader = new FXMLLoader();
			newTab.setContent((Pane) tabLoader.load(url.openStream()));

			CardEditorTabController controller = tabLoader.<CardEditorTabController> getController();
			controller.setFile(cardFile);
			newTab.setText(cardFile.getName());
			newTab.setClosable(true);
			cardTabs.getTabs().add(newTab);
			tabControllers.put(newTab, controller);
			newTab.setOnClosed(new EventHandler<javafx.event.Event>(){
			    @Override
				public void handle(javafx.event.Event e){
			    	tabControllers.remove(newTab);
			    }
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the card to folder.
	 *
	 * @param addLoc the add loc
	 */
	void addCardToFolder(TreeItem<File> addLoc) {
		Card newCard = new Minion();
		
		TextInputDialog nameDialog = new TextInputDialog();
		nameDialog.setTitle("New Card");
		nameDialog.setHeaderText("New Card");
		nameDialog.setContentText("New Card name:");
		newCard.setName(nameDialog.showAndWait().get());
		
		File saveFile = new File(addLoc.getValue().getPath() + "/" + newCard.getName() + ".xml");
		Card.saveToXML(newCard, saveFile);
		addLoc.getChildren().add(new TreeItem<File>(saveFile));
		openTab(saveFile);
		allCards.add(newCard);
	}

	/**
	 * New card.
	 *
	 * @param event the event
	 */
	@FXML
	void newCard(ActionEvent event) {
		addCardToFolder(rootTreeItem);
	}


	/**
	 * Saves all the open cards.
	 *
	 * @param event the event
	 */
	@FXML
	void save(ActionEvent event) {
		for (Tab tab: cardTabs.getTabs()) {
			tabControllers.get(tab).save();
		}
	}

}