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

public class CardEditorController {

	@FXML
	private MenuItem newCard;

	@FXML
	private MenuItem save;

	@FXML
	private TabPane cardTabs;

	@FXML
	private TreeView<File> cardList;

	private HashSet<Card> allCards = new HashSet<Card>();
	
	private Hashtable<Tab, CardEditorTabController> tabControllers = new Hashtable<Tab, CardEditorTabController>();
		
	//private final Node rootIcon = new ImageView(new Image("file:@../resources/img/folder.png"));

	private final File rootDirectory = new File("cards");


	private TreeItem<File> rootTreeItem;
	
	public void selectItem(MouseEvent mouseEvent) {
		TreeItem<File> item = cardList.getSelectionModel().getSelectedItem();

		File selected = item.getValue();
		if (!selected.isDirectory()) {
			openTab(selected);
		}
	}
	
	
	private TreeItem<File> getTreeAddPath() {
		TreeItem<File> selectedTreeItem = cardList.getSelectionModel().getSelectedItem();
		File selected = selectedTreeItem.getValue();
		if (selected.isDirectory()) {
			return selectedTreeItem;
		} else {
			return selectedTreeItem.getParent();
		}
	}
	
	private void makeSubdirectory(File directory) {
		TreeItem<File> addPos = getTreeAddPath();
		String folderName = (new TextInputDialog()).showAndWait().get();
		String childPath = directory.getPath() + File.separator + folderName;
		File child = new File(childPath);
		child.mkdirs();
		addPos.getChildren().add(new TreeItem<File>(child));
	}
	
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
	 * directory and its sub-directories
	 * 
	 * @param currentDirectory
	 *            - The top level directory to search.
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

	@FXML
	void newCard(ActionEvent event) {
		addCardToFolder(rootTreeItem);
	}


	@FXML
	void save(ActionEvent event) {
		for (Tab tab: cardTabs.getTabs()) {
			tabControllers.get(tab).save();
		}
	}

}