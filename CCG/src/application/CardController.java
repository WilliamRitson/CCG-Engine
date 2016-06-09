package application;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * The Class CardController.
 * 
 * This class handles the UI logic for a single card element
 */
public class CardController {

	/** The root element of the UI. */
	@FXML
	private StackPane root;

	/** UI element to display the card's background image. */
	@FXML
	private ImageView backgroundImage;

	/** UI element to display the card's name. */
	@FXML
	private Text name;

	/** UI element to display the card's resource cost. */
	@FXML
	private Text cost;

	/** UI element to display the card's main image. */
	@FXML
	private ImageView image;

	/** UI element to display the card's rule text. */
	@FXML
	private Text ruleText;

	/** UI element to display the card's remaining life. */
	@FXML
	private Text life;

	/** UI element to display the card's damage. */
	@FXML
	private Text damage;

	/** The object that models the card. */
	private Card model;

	/**
	 * This function is automatically called when the element is constructed
	 * form FXML. It adds an event binding for when the card is dragged.
	 */
	@FXML
	private void initialize() {
		root.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				dragPlay(event);
			}
		});
	}

	/**
	 * Drag play.
	 *
	 * @param event
	 *            the event
	 */
	@FXML
	public void dragPlay(MouseEvent event) {

		if (!model.isPlayable())
			return;

		/* drag was detected, start a drag-and-drop gesture */
		/* allow any transfer mode */
		Dragboard db = root.startDragAndDrop(TransferMode.ANY);

		/* Put a string on a dragboard */
		ClipboardContent content = new ClipboardContent();
		content.putString(model.getID());
		db.setContent(content);

		/* Put a string on a dragboard */
		db.setContent(content);

		event.consume();
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public Card getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @param model
	 *            the new model
	 */
	public void setModel(Card model) {
		this.model = model;
		bindModel();
	}
	
	/**
	 * Binds to UI to a model
	 */
	private void bindModel() {
		cost.textProperty().bind(model.getCostProperty().asString());
		name.setText(model.getName());
		ruleText.setText(model.getDisplayText());
		if (model instanceof Minion) {
			life.textProperty().bind(((Minion) model).getLifeProperty().asString());
			damage.textProperty().bind(((Minion) model).getDamageProperty().asString());
		} else {
			life.setVisible(false);
			damage.setVisible(false);
		}
	}

}
