package application;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CardEditorTabController {

	@FXML
	private TextField cost;

	@FXML
	private ChoiceBox<String> cardType;

	@FXML
	private ChoiceBox<String> cardClass;

	@FXML
	private ChoiceBox<String> cardSubtype;

	@FXML
	private ChoiceBox<String> cardRarity;

	@FXML
	private ChoiceBox<String> cardSet;

	@FXML
	private CheckBox cardIsCollectable;

	@FXML
	private TextField attack;

	@FXML
	private TextField life;

	private File cardFile;

	private Card model;

	@FXML
	private TextArea ruleText;

	@FXML
	public void initialize() {
		cardType.setItems(FXCollections.observableArrayList("Minion", "Spell", "Weapon"));
		cardClass.setItems(FXCollections.observableArrayList("Neutral", "Mage", "Warrior", "Hunter", "Rouge", "Druid",
				"Warlock", "Paladin", "Shaman", "Priest"));
		cardSubtype.setItems(FXCollections.observableArrayList("None", "Beast", "Demon", "Dragon", 
				"Mech", "Murloc", "Pirate", "Totem"));
		cardRarity.setItems(FXCollections.observableArrayList("Common", "Rare", "Epic", "Legendary"));
		cardSet.setItems(FXCollections.observableArrayList("Basic", "Classic", "Reward", "Promo",
				"Naxxramas", "Goblins vs Gnomes", "Blackrock Mountain Mission", 
				"The Grand Tournament", "League of Explorers", "Whispers of the Old Gods"));
	}
	
	String getCardType(Card card) {
		if (card instanceof Minion) {
			return "Minion";
		} else if (card instanceof Spell) {
			return "Spell";
		} else {
			return "Weapon";
		}
	}

	void getDataFromView() {
		model.setCost(Integer.parseInt(cost.getText()));
		model.setRuleText(ruleText.getText());
		model.setHeroClass(cardClass.getValue());
		model.setSubtype(cardSubtype.getValue());
		model.setRarity(cardRarity.getValue());
		model.setSet(cardSet.getValue());
		model.setCollectable(cardIsCollectable.isSelected());

		if (model instanceof Minion) {
			((Minion) model).setLife(Integer.parseInt(life.getText()));
			((Minion) model).setDamage(Integer.parseInt(attack.getText()));
		}
	}

	void pushDataToView() {
		cardType.setValue(getCardType(model));
		cost.setText(String.valueOf(model.getCost()));
		ruleText.setText(model.getRuleText());
		cardClass.setValue(model.getHeroClass());
		cardSubtype.setValue(model.getSubtype());
		cardRarity.setValue(model.getRarity());
		cardSet.setValue(model.getSet());
		cardIsCollectable.setSelected(model.isCollectable());

		if (model instanceof Minion) {
			life.setText(String.valueOf(((Minion) model).getLife()));
			attack.setText(String.valueOf(((Minion) model).getDamage()));
		}
	}

	public void setFile(File cardFile) {
		this.cardFile = cardFile;
		model = Minion.loadFromXML(cardFile);
		pushDataToView();
	}

	/**
	 * Saves a card to a file as xml
	 * 
	 * @param card
	 *            - the card to save
	 * @param file
	 *            - the file to save it to
	 */
	public void save() {
		getDataFromView();
		if (model instanceof Minion) {
			Minion.saveToXML((Minion) model, cardFile);
		}
	}

}
